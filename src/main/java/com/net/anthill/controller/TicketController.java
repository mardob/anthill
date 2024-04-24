package com.net.anthill.controller;

import com.net.anthill.dto.TicketDto;
import com.net.anthill.service.TicketService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
public class TicketController {

    @Autowired
    private TicketService ticketService;

    // @PatchMapping equal to @RequestMapping(method= RequestMethod.PATCH)
    // @GetMapping     same for get
    @GetMapping(value="/api/tickets/")
    @ResponseBody
    public List<TicketDto> getTickets(){
        System.out.println("Fetching tickets");
        List <TicketDto> tickets = ticketService.getTickets();
        return tickets;
    }

    @GetMapping(value="/api/tickets/{id}")
    @ResponseBody
    public TicketDto getTicket(@NotBlank @PathVariable(value="id") Long ticketId){
        System.out.println("Fetching TicketDto with id " + ticketId);
        TicketDto ticket = ticketService.getTicketById(ticketId);
        return ticket;
    }
/* TODO need to implement
    @GetMapping(value="/api/tickets/{id}/notes")
    @ResponseBody
    public List<NoteDto> getTicketsNotes(@NotBlank @PathVariable(value="id") Long ticketId){
        System.out.println("Fetching ticket with id " + ticketId);
        List<NoteDto> notes = noteService.getNotesOfTicket(ticketId);
        return notes;
    }*/

    @PostMapping(value="/api/tickets/")
    @ResponseBody
    public void createTicket(@NotBlank @RequestBody TicketDto ticket){
        ticketService.createTicket(ticket);
    }

    @PutMapping(value="/api/tickets/{id}")
    @ResponseBody
    public void updateTicket(@NotBlank @PathVariable(value="id") Long ticketId, @NotBlank @RequestBody TicketDto ticket){
        ticket.setId(ticketId);
        ticketService.updateTicket(ticket);
    }

    @DeleteMapping(value="/api/tickets/{id}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteTicket(@NotBlank @PathVariable(value="id") Long ticketId){
        ticketService.deleteTicketById(ticketId);
    }



}
