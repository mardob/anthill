package com.net.anthill.controller;

import com.net.anthill.dto.NoteDto;
import com.net.anthill.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class NoteController {

    @Autowired
    private NoteService noteService;


    @RequestMapping(method= RequestMethod.GET, value="/v1/notes/")
    @ResponseBody
    public List<NoteDto> getNotes(){
        System.out.println("Fetching notes");
        List <NoteDto> notes = noteService.getNotes();
        return notes;
    }

    @GetMapping(value="/v1/tickets/{ticketId}/notes/")
    @ResponseBody
    public List<NoteDto> getTicketsNotes( @PathVariable(value="ticketId") Long ticketId){
        System.out.println("Fetching ticket's ["+ticketId+"] notes");
        List <NoteDto> notes = noteService.getNotesByTicketId(ticketId);
        return notes;
    }

    @GetMapping(value="/v1/notes/{id}")
    @ResponseBody
    public NoteDto getNote( @PathVariable(value="id") Long noteId){
        System.out.println("Fetching note with id " + noteId);
        NoteDto note = noteService.getNoteById(noteId);
        return note;
    }
/*
    @GetMapping(value="/api/v1/notes2/{id}")
    @ResponseBody
    public NoteDto getNote2( @PathVariable(value="id") Long noteId){
        System.out.println("Fetching note with id " + noteId);
        NoteDto note = noteService.getNoteById(noteId);
        note.add(linkTo(methodOn(NoteController.class).getNote2(noteId)).withSelfRel());
        return note;
    }*/

    @PostMapping(value="/v1/notes/")
    @ResponseBody
    public NoteDto createNote(@RequestBody NoteDto note){
        return noteService.createNote(note);
    }


    @PutMapping(value="/v1/notes/{id}")
    @ResponseBody
    public void updateNote(@RequestBody NoteDto note){
        noteService.updateNote(note);
    }


    @DeleteMapping(value="/v1/notes/{id}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteNote(@PathVariable(value="id") Long noteId){
        System.out.println("deleteNote in NoteController called with " + noteId);
        noteService.deleteNoteById(noteId);
    }


}