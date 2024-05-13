package com.net.anthill.service;

import com.net.anthill.dto.NoteDto;
import com.net.anthill.dto.UserMetadataDto;
import com.net.anthill.model.Note;
import com.net.anthill.model.UserMetadata;
import com.net.anthill.repository.NoteRepo;
import com.net.anthill.security.AuthenticationFacade;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class NoteService {

    private TicketService ticketService;

//    Logger log = LoggerFactory.getLogger(NoteService.class);
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private NoteRepo noteRepository;

    private AuthenticationFacade authenticationFacade;
    private UserMetadataService userMetadataService;
    private Date dateOfExecution;
    ModelMapper modelMapper = new ModelMapper(); // Can't be mocked as it is not injected

    @Autowired
    public NoteService(TicketService ticketService, NoteRepo noteRepository, AuthenticationFacade authenticationFacade, UserMetadataService userMetadataService){
        this.noteRepository = noteRepository;
        this.ticketService = ticketService;
        this.authenticationFacade = authenticationFacade;
        this.userMetadataService = userMetadataService;
    }

    public List<NoteDto> getNotes(){
        List<NoteDto> data = new ArrayList<NoteDto>();
        noteRepository.findAll().forEach(item -> data.add(modelMapper.map(item, NoteDto.class)));
        return data;
    }

    public NoteDto getNoteById(long id){
        Optional<Note> ticket = noteRepository.findById(id);
        return modelMapper.map(ticket.get(), NoteDto.class);
    }

    public List<NoteDto> getNotesByTicketId(long ticketId){
        List<NoteDto> notes = new ArrayList<NoteDto>();
        noteRepository.findNotesRelatedToTicket(ticketId).forEach(item -> notes.add(modelMapper.map(item, NoteDto.class)));
        return notes;
    }

    public void createNote(NoteDto noteDto){
        NoteDto cleanedUpDto = deleteExtraFields(noteDto); //TODO In future either do this in mapper or make unique DTO for create call
        System.out.println("deleteNoteById in NoteService called with " + cleanedUpDto.toString());
        Note note = modelMapper.map(cleanedUpDto, Note.class);
        populateNotesCreator(note);
        noteRepository.save(note);
    }

    public void deleteNoteById(long noteId){
        System.out.println("deleteNoteById in NoteService called with " + noteId);
        this.noteRepository.deleteById(noteId);
    }


    public void updateNote(@NotNull NoteDto noteDto){
        System.out.println("updateNote in NoteService called with " + noteDto.toString());
        Note note = modelMapper.map(noteDto, Note.class);
        noteRepository.save(note);
    }

    private NoteDto deleteExtraFields(NoteDto noteDto){
        noteDto.setId(0);
        noteDto.setDateCreated(new Date());
        return noteDto;
    }

    private void populateNotesCreator(Note note){
        String username = authenticationFacade.getAuthentication().getName();
        UserMetadataDto userMetadataDto = userMetadataService.getUserMetadataByUsername(username);
        if(userMetadataDto != null) {
            System.out.println("loged in details: " + userMetadataDto.getUsername());
            note.setCreator(modelMapper.map(userMetadataDto, UserMetadata.class));
        }//TODO add error handling

    }
}
