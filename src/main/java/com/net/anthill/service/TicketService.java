package com.net.anthill.service;

import com.net.anthill.constants.Status;
import com.net.anthill.dto.TicketDto;
import com.net.anthill.dto.UserMetadataDto;
import com.net.anthill.model.Ticket;
import com.net.anthill.model.UserMetadata;
import com.net.anthill.repository.TicketRepo;
import com.net.anthill.security.AuthenticationFacade;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service @Slf4j
public class TicketService {
    private TicketRepo ticketRepository;
    private AuthenticationFacade authenticationFacade;
    private UserMetadataService userMetadataService;
    private ModelMapper modelMapper = new ModelMapper();


    @Autowired
    public TicketService(TicketRepo ticketRepository, AuthenticationFacade authenticationFacade, UserMetadataService userMetadataService){
        this.ticketRepository = ticketRepository;
        this.authenticationFacade = authenticationFacade;
        this.userMetadataService = userMetadataService;
    }

    public TicketDto getTicketById(long id){
        log.trace("getTicketById started");
        Optional<Ticket> opTicket = ticketRepository.findById(id);
        TicketDto ticketDto = modelMapper.map(opTicket.get(), TicketDto.class);//TODO handle error in case ticket not found
        log.trace("getTicketById ended");
        return ticketDto;
    }


    public Page<TicketDto> getPaginatedTickets(int pageId, int pageSize,String sortBy, String sortDirection){
        log.warn("getPaginatedTickets started");
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageRequest = PageRequest.of(pageId, pageSize, sort);
        Page<Ticket> resultPage = ticketRepository.findAll(pageRequest);
        System.out.println("Page insides totalpages " + resultPage.getTotalPages()+" totalElements"+resultPage.getTotalElements());
        Page<TicketDto> resultingPage = resultPage.map(item -> modelMapper.map(item, TicketDto.class));
        log.info("getPaginatedTickets ended");
        return resultingPage;
    }


    public TicketDto createTicket(@NotNull TicketDto ticketDto){
        log.trace("createTicket started");
        //TODO In future either do this in mapper or make unique DTO for create call
       // TicketDto cleanedDto = deleteUnnecessaryFields(ticketDto);
        System.out.println("Sent ticketDto "+ ticketDto.getId() + " _ "+ ticketDto.getStatus() + " _ "+ ticketDto.getDateCreated());
        modelMapper.addMappings(mappingsUpdated());
        Ticket ticket = modelMapper.map(ticketDto, Ticket.class);
        System.out.println("Sent ticket "+ ticket.getId() + " _ "+ ticket.getStatus() + " _ "+ ticket.getDateCreated());

        populateReportingUser(ticket);
        ticketRepository.save(ticket);
        TicketDto persistedTicketDto = modelMapper.map(ticket, TicketDto.class);
        log.trace("createTicket ended");
        return persistedTicketDto;
    }

    public void updateTicket(@NotNull TicketDto ticketDto){
        log.trace("updateTicket started");
        Ticket ticket = modelMapper.map(ticketDto, Ticket.class);
        ticketRepository.save(ticket);
        log.trace("updateTicket ended");
    }

    private Optional<Ticket> getTicket(long id){
        return ticketRepository.findById(id);
    }

    public void deleteTicketById(long itemId){
        this.ticketRepository.deleteById(itemId);
    }

    private TicketDto deleteUnnecessaryFields(TicketDto ticketDto){
        log.trace("deleteUnnecessaryFields started");
        ticketDto.setId(0);
        ticketDto.setStatus(Status.NEW);
        ticketDto.setDateCreated(new Date());
        log.trace("deleteUnnecessaryFields ended");
        return ticketDto;
    }

    private void populateReportingUser(Ticket ticket){
        log.trace("populateReportingUser started");
        String username = authenticationFacade.getAuthentication().getName();
        UserMetadataDto userMetadataDto = userMetadataService.getUserMetadataByUsername(username);
        if(userMetadataDto != null) {
            System.out.println("loged in details: " + userMetadataDto.getUsername());
            ticket.setReportingUser(modelMapper.map(userMetadataDto, UserMetadata.class));
        }//TODO add error handling
        log.trace("populateReportingUser ended");
    }

    private PropertyMap<TicketDto,Ticket> mappingsUpdated(){
     return new PropertyMap<TicketDto,Ticket>(){
         @Override
         protected void configure() {
              //skip(destination.getBlessedField());
                map().setId(0);
             map().setStatus(Status.NEW);
             map().setDateCreated(new Date());
         }
     };
    }

}
