package com.net.anthill.service;

import com.net.anthill.dto.TicketDto;
import com.net.anthill.model.Note;
import com.net.anthill.model.Ticket;
import com.net.anthill.repository.TicketRepo;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TicketService {
    Logger log = LoggerFactory.getLogger(TicketService.class);
    private TicketRepo ticketRepository;
    ModelMapper modelMapper = new ModelMapper();


    @Autowired
    public TicketService(TicketRepo ticketRepository){
        this.ticketRepository = ticketRepository;
    }

    public TicketDto getTicketById(long id){
        Optional<Ticket> opTicket = ticketRepository.findById(id);
        TicketDto ticketDto = modelMapper.map(opTicket.get(), TicketDto.class);//TODO handle error in case ticket not found
        return ticketDto;
    }


    public List<TicketDto> getPaginatedTickets(int pageId, int pageSize){
        Pageable pageRequest = PageRequest.of(pageId, pageSize);
        Page<Ticket> resultPage = ticketRepository.findAll(pageRequest);
        System.out.println("Page insides totalpages " + resultPage.getTotalPages()+" totalElements"+resultPage.getTotalElements());
        return resultPage.map(item -> modelMapper.map(item, TicketDto.class)).toList();
    }

    public void createTicket(@NotNull TicketDto ticketDto){
        Ticket ticket = modelMapper.map(ticketDto, Ticket.class);
        ticketRepository.save(ticket);
    }

    public void updateTicket(@NotNull TicketDto ticketDto){
        Ticket ticket = modelMapper.map(ticketDto, Ticket.class);
        ticketRepository.save(ticket);
    }

    private Optional<Ticket> getTicket(long id){
        return ticketRepository.findById(id);
    }

    public void deleteTicketById(long itemId){
        this.ticketRepository.deleteById(itemId);
    }



}
