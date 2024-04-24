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
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TicketService {
    Logger log = LoggerFactory.getLogger(TicketService.class);
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private TicketRepo ticketRepository;
    private Date dateOfExecution;
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

    public List<TicketDto> getTickets(){
        List<TicketDto> tickets = new ArrayList<TicketDto>();
        ticketRepository.findAll().forEach(item -> tickets.add(modelMapper.map(item, TicketDto.class)));
        return tickets;
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




/*
    public List<Item> getAllPaginated(){
        List<Item> data = new ArrayList<Item>();
        itemRepository.findAll().forEach(item -> data.add(item));
        return data;
    }*/

/*
    private Date createDateFromDateString(String dateString){
        Date date = null;
        if(null!=dateString) {
            try {
                date = DATE_FORMAT.parse(dateString);
            }catch(ParseException pe){
                date = new Date();
            }
        }else{
            date = new Date();
        }
        return date;
    }*/

/*
    public List<Item> findByName(String name){
        System.out.println(name);
        List l =  this.itemRepository.findByNameIgnoreCase(name);
        System.out.println(l.size());
        return l;
    }*/

/*
    public List<Item> createNewItems(List<Item> items){
        this.itemRepository.createNewItems(items);
        return items;
    }*/

    /* TODO next impl
    public Item updateCategory(Long itemId, List<Long> categoryId){
        Optional<Item> itemOption = this.itemRepository.findById(itemId);
        List<Category> categories = this.categoryRepository.findByCategoryIdIn(categoryId);
            Item item = itemOption.get();
            item.setCategories(categories);
            return item;
    }*/

/*
    public void updateItems(List<Item> items){
        this.itemRepository.saveAll(items);
    }*/

    public void deleteTicketById(long itemId){
        this.ticketRepository.deleteById(itemId);
    }


/*
    public List<Item> checkForItemSpoilage(){
        log.debug("inside checkForItemSpoilage");
        //TODO get list of all items
        List<Item> items = new ArrayList<Item>();//mockupListOfItems
        itemRepository.findAll().forEach(item -> items.add(item));
        log.debug("fetched following number of items " + items.size());

        //TODO Iterate over it and fill your own list of those that are fitting rules
        this.dateOfExecution = new Date();
        //items.parallelStream().forEach(item -> checkItem(item));
        for(Item item: items) {
            checkAndUpdateItem(item);
        }
        log.debug("Item checking is finished");
        return items;
    }
*/
    //Method to generate notifications based on business rules
 /*   private void checkAndUpdateItem(Item item){
        log.debug("checkItem got following item with following id: " + item.getItemId());
        if(item.getDateOfExpiration() != null) {
            long diffInMs = Math.abs(dateOfExecution.getTime() - item.getDateOfExpiration().getTime());
            long diff = TimeUnit.DAYS.convert(diffInMs, TimeUnit.MILLISECONDS);
            if(diff < 0){
                item.setSpoilage(Spoilage.SPOILED);
            } else
            if(diff <= 2){
                item.setSpoilage(Spoilage.LAST_CALL);
            } else
            if(diff <= 7){
                item.setSpoilage(Spoilage.CLOSE);
            }
        }
    }*/

}
