package com.net.anthill.service;

import com.net.anthill.dto.NoteDto;
import com.net.anthill.model.Note;
import com.net.anthill.model.Ticket;
import com.net.anthill.model.UserMetadata;
import com.net.anthill.repository.NoteRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
class NoteServiceUnitTest {


	@InjectMocks
	private NoteService NoteService;
	@Mock
	NoteRepo noteRepository;
	@Mock
	TicketService ticketService;

	@BeforeEach
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void contextLoads() {
		assertThat(NoteService).isNotNull();
	}

	//TODO add mock for mapper when able
	@Test
	void GIVEN_nothing_WHEN_getNotes_THEN_listOfNoteDtosReturned() {
		//GIVEN
		List<Note> listOfNotes = buildNotes();
		Mockito.when(noteRepository.findAll()).thenReturn(listOfNotes);

		//WHEN
		List<NoteDto> result = NoteService.getNotes();

		//THEN
		assertThat(result).hasSize(listOfNotes.size());
		verify(noteRepository).findAll();
	}

	//TODO add mock for mapper when able
	@Test
	void GIVEN_noteId_WHEN_getNoteById_THEN_noteDtoReturned() {
		//GIVEN
		Note note = buildNote();
		Mockito.when(noteRepository.findById(note.getNoteId())).thenReturn(Optional.of(note));

		//WHEN
		NoteDto result = NoteService.getNoteById(note.getNoteId());

		//THEN
		verify(noteRepository).findById(note.getNoteId());
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(note.getNoteId());
	}


	//TODO add mock for mapper when able
	@Test
	void GIVEN_ticketId_WHEN_getNotesByTicketId_THEN_noteDtoReturned() {
		//GIVEN
		long ticketId = 2L;
		List<Note> notes = buildNotes();
		Mockito.when(noteRepository.findNotesRelatedToTicket(ticketId)).thenReturn(notes);

		//WHEN
		List<NoteDto> result = NoteService.getNotesByTicketId(ticketId);

		//THEN
		assertThat(result).hasSize(notes.size());
		verify(noteRepository).findNotesRelatedToTicket(ticketId);
	}

	@Test
	void GIVEN_noteDto_WHEN_createNote_THEN_noteCreated(){
		//GIVEN
		ArgumentCaptor<Note> saveMethodCapture = ArgumentCaptor.forClass(Note.class);
		//ArgumentCaptor<Note> addNoteToTicketCapture = ArgumentCaptor.forClass(Note.class);
		Mockito.when(noteRepository.save(any(Note.class))).then(AdditionalAnswers.returnsFirstArg());
		//doNothing().when(ticketService).addNoteToTicket(any(Note.class));

		//WHEN
		NoteService.createNote(buildNoteDto());


		//THEN
		verify(noteRepository).save(saveMethodCapture.capture());
		//verify(ticketService).addNoteToTicket(addNoteToTicketCapture.capture());
		//assertThat(addNoteToTicketCapture.getValue()).isEqualTo(saveMethodCapture.getValue());
	}

	@Test
	void GIVEN_noteId_WHEN_deleteNoteById_THEN_noteDtoReturned() {
		//GIVEN
		Note note = buildNote();

		//WHEN
		NoteService.deleteNoteById(note.getNoteId());

		//THEN
		verify(noteRepository).deleteById(note.getNoteId());
	}

	//TODO add mock for mapper when able
	@Test
	void GIVEN_noteDto_WHEN_updateNote_THEN_noteCreated(){
		//GIVEN
		ArgumentCaptor<Note> saveMethodCapture = ArgumentCaptor.forClass(Note.class);
		Mockito.when(noteRepository.save(any(Note.class))).then(AdditionalAnswers.returnsFirstArg());

		//WHEN
		NoteService.updateNote(buildNoteDto());


		//THEN
		verify(noteRepository).save(saveMethodCapture.capture());
	}







	private NoteDto buildNoteDto(){
		return new NoteDto(100L, "Test Testerson", "This is test noteDto", new Date(),1L);
	}

	private Note buildNote() {
		return buildNote(2L);
	}

	private Note buildNote(long ticketId){
		Ticket ticket = new Ticket();
		ticket.setTicketId(ticketId);
		return new Note(1L, "Test Testerson", "This is test note", new Date(), new Date(), ticket, new UserMetadata());
	}

	private List<Note> buildNotes(){
		List<Note> list = new ArrayList<>();
		for(int i = 0; i < 5; i++){
			list.add(buildNote(2L));
		}
		return list;
	}
}
