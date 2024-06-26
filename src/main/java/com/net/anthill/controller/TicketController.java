package com.net.anthill.controller;

import com.net.anthill.constants.ApiConstants;
import com.net.anthill.dto.TicketDto;
import com.net.anthill.service.TicketService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class TicketController {

    @Autowired
    private TicketService ticketService;


    @GetMapping(value="/v1/tickets/")
    @ResponseBody
    public Page<TicketDto> getTickets(
        @RequestParam(required = false, defaultValue = ApiConstants.DEFAULT_PAGE_SIZE, value="pageSize") int pageSize,
        @RequestParam(required = false, defaultValue = ApiConstants.DEFAULT_PAGE_NUMBER, value="page") int pageId,
        @RequestParam(required = false, defaultValue = ApiConstants.DEFAULT_SORT_BY, value="sortBy") String sortBy,
        @RequestParam(required = false, defaultValue = ApiConstants.DEFAULT_SORT_DIRECTION, value="sortDirection") String sortDirection){

        System.out.println("Fetching paged tickets with page " + pageId + " and pageSize " + pageSize );
        //List <TicketDto> tickets =
        return ticketService.getPaginatedTickets(pageId, pageSize, sortBy, sortDirection);
        //return tickets;
    }

    @GetMapping(value="/v1/tickets/{id}")
    @ResponseBody
    public TicketDto getTicket(@NotNull @PathVariable(value="id") Long ticketId){
        System.out.println("Fetching TicketDto with id " + ticketId);
        TicketDto ticket = ticketService.getTicketById(ticketId);
        return ticket;
    }


    @PostMapping(value="/v1/tickets/")
    @ResponseBody
    public TicketDto createTicket(@Valid @RequestBody TicketDto ticket){
        return ticketService.createTicket(ticket);
    }

    @PutMapping(value="/v1/tickets/{id}")
    @ResponseBody
    public void updateTicket(@NotNull @PathVariable(value="id") Long ticketId, @Valid @RequestBody TicketDto ticket){
        ticket.setId(ticketId);
        ticketService.updateTicket(ticket);
    }

    @DeleteMapping(value="/v1/tickets/{id}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteTicket(@NotNull @PathVariable(value="id") Long ticketId){
        ticketService.deleteTicketById(ticketId);
    }



}
