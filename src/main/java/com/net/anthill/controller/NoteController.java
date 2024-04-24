package com.net.anthill.controller;

import com.net.anthill.dto.NoteDto;
import com.net.anthill.service.NoteService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@RestController
public class NoteController {

    @Autowired
    private NoteService noteService;


    @RequestMapping(method= RequestMethod.GET, value="/api/notes/")
    @ResponseBody
    public List<NoteDto> getNotes(){
        System.out.println("Fetching notes");
        List <NoteDto> notes = noteService.getNotes();
        return notes;
    }

    @GetMapping(value="/api/tickets/{ticketId}/notes/")
    @ResponseBody
    public List<NoteDto> getTicketsNotes(@NotBlank @PathVariable(value="ticketId") Long ticketId){
        System.out.println("Fetching ticket's ["+ticketId+"] notes");
        List <NoteDto> notes = noteService.getNotesByTicketId(ticketId);
        return notes;
    }

    @GetMapping(value="/api/notes/{id}")
    @ResponseBody
    public NoteDto getNote(@NotBlank @PathVariable(value="id") Long noteId){
        System.out.println("Fetching note with id " + noteId);
        NoteDto note = noteService.getNoteById(noteId);
        return note;
    }

    @GetMapping(value="/api/notes2/{id}")
    @ResponseBody
    public NoteDto getNote2(@NotBlank @PathVariable(value="id") Long noteId){
        System.out.println("Fetching note with id " + noteId);
        NoteDto note = noteService.getNoteById(noteId);
        note.add(linkTo(methodOn(NoteController.class).getNote2(noteId)).withSelfRel());
        return note;
    }

    @PostMapping(value="/api/notes/")
    @ResponseBody
    public void createNote(@NotBlank @RequestBody NoteDto note){
        noteService.createNote(note);
    }


    @PutMapping(value="/api/notes/{id}")
    @ResponseBody
    public void updateNote(@NotBlank @RequestBody NoteDto note){
        noteService.updateNote(note);
    }


    @DeleteMapping(value="/api/notes/{id}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteNote(@NotBlank @PathVariable(value="id") Long noteId){
        System.out.println("deleteNote in NoteController called with " + noteId);
        noteService.deleteNoteById(noteId);
    }


}