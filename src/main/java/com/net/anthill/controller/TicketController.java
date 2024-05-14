package com.net.anthill.controller;

import com.net.anthill.constants.ApiConstants;
import com.net.anthill.dto.TicketDto;
import com.net.anthill.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TicketController {

    @Autowired
    private TicketService ticketService;

    // @PatchMapping equal to @RequestMapping(method= RequestMethod.PATCH)
    // @GetMapping     same for get


    @GetMapping(value="/api/tickets/")
    @ResponseBody
    public List<TicketDto> getTickets(
        @RequestParam(required = false, defaultValue = ApiConstants.DEFAULT_PAGE_SIZE, value="pageSize") int pageSize,
        @RequestParam(required = false, defaultValue = ApiConstants.DEFAULT_PAGE_NUMBER, value="page") int pageId,
        @RequestParam(required = false, defaultValue = ApiConstants.DEFAULT_SORT_BY, value="sortBy") String sortBy,
        @RequestParam(required = false, defaultValue = ApiConstants.DEFAULT_SORT_DIRECTION, value="sortDirection") String sortDirection){

        System.out.println("Fetching paged tickets with page " + pageId + " and pageSize " + pageSize );
        List <TicketDto> tickets = ticketService.getPaginatedTickets(pageId, pageSize, sortBy, sortDirection);
        return tickets;
    }

    @GetMapping(value="/api/tickets/{id}")
    @ResponseBody
    public TicketDto getTicket(@PathVariable(value="id") Long ticketId){
        System.out.println("Fetching TicketDto with id " + ticketId);
        TicketDto ticket = ticketService.getTicketById(ticketId);
        return ticket;
    }


    @PostMapping(value="/api/tickets/")
    @ResponseBody
    public void createTicket(@RequestBody TicketDto ticket){
        ticketService.createTicket(ticket);
    }

    @PutMapping(value="/api/tickets/{id}")
    @ResponseBody
    public void updateTicket(@PathVariable(value="id") Long ticketId, @RequestBody TicketDto ticket){
        ticket.setId(ticketId);
        ticketService.updateTicket(ticket);
    }

    @DeleteMapping(value="/api/tickets/{id}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteTicket(@PathVariable(value="id") Long ticketId){
        ticketService.deleteTicketById(ticketId);
    }



}
