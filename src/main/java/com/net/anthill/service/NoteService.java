package com.net.anthill.service;

import com.net.anthill.dto.NoteDto;
import com.net.anthill.dto.UserMetadataDto;
import com.net.anthill.model.Note;
import com.net.anthill.model.UserMetadata;
import com.net.anthill.repository.NoteRepo;
import com.net.anthill.security.AuthenticationFacade;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service @Slf4j
public class NoteService {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private NoteRepo noteRepository;

    private AuthenticationFacade authenticationFacade;
    private UserMetadataService userMetadataService;
    ModelMapper modelMapper = new ModelMapper(); // Can't be mocked as it is not injected

    @Autowired
    public NoteService(NoteRepo noteRepository, AuthenticationFacade authenticationFacade, UserMetadataService userMetadataService){
        this.noteRepository = noteRepository;
        this.authenticationFacade = authenticationFacade;
        this.userMetadataService = userMetadataService;
    }

    public List<NoteDto> getNotes(){
        log.trace("getNotes started");
        List<NoteDto> data = new ArrayList<NoteDto>();
        noteRepository.findAll().forEach(item -> data.add(modelMapper.map(item, NoteDto.class)));
        log.trace("getNotes ended");
        return data;
    }

    public NoteDto getNoteById(long id){
        log.trace("getNoteById started");
        Optional<Note> ticket = noteRepository.findById(id);
        NoteDto noteDto = modelMapper.map(ticket.get(), NoteDto.class);
        log.trace("getNoteById ended");
        return noteDto;
    }

    public List<NoteDto> getNotesByTicketId(long ticketId){
        log.trace("getNoteById started");
        List<NoteDto> notes = new ArrayList<NoteDto>();
        noteRepository.findNotesRelatedToTicket(ticketId).forEach(item -> notes.add(modelMapper.map(item, NoteDto.class)));
        log.trace("getNoteById ended");
        return notes;
    }

    public void createNote(NoteDto noteDto){
        log.trace("createNote started");
        NoteDto cleanedUpDto = deleteExtraFields(noteDto); //TODO In future either do this in mapper or make unique DTO for create call
        System.out.println("deleteNoteById in NoteService called with " + cleanedUpDto.toString());
        Note note = modelMapper.map(cleanedUpDto, Note.class);
        populateNotesCreator(note);
        log.trace("createNote ended");
        noteRepository.save(note);
    }

    public void deleteNoteById(long noteId){
        log.trace("deleteNoteById started");
        System.out.println("deleteNoteById in NoteService called with " + noteId);
        this.noteRepository.deleteById(noteId);
        log.trace("deleteNoteById ended");
    }


    public void updateNote(@NotNull NoteDto noteDto){
        log.trace("updateNote started");
        System.out.println("updateNote in NoteService called with " + noteDto.toString());
        Note note = modelMapper.map(noteDto, Note.class);
        noteRepository.save(note);
        log.trace("updateNote ended");
    }

    private NoteDto deleteExtraFields(NoteDto noteDto){
        log.trace("deleteExtraFields started");
        noteDto.setId(0);
        noteDto.setDateCreated(new Date());
        log.trace("deleteExtraFields ended");
        return noteDto;
    }

    private void populateNotesCreator(Note note){
        log.trace("populateNotesCreator started");
        String username = authenticationFacade.getAuthentication().getName();
        UserMetadataDto userMetadataDto = userMetadataService.getUserMetadataByUsername(username);
        if(userMetadataDto != null) {
            System.out.println("loged in details: " + userMetadataDto.getUsername());
            note.setCreator(modelMapper.map(userMetadataDto, UserMetadata.class));
        }//TODO add error handling
        log.trace("populateNotesCreator ended");
    }
}
