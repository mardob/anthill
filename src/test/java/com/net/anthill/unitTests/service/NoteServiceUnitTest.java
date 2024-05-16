package com.net.anthill.unitTests.service;

import com.net.anthill.dto.NoteDto;
import com.net.anthill.dto.UserMetadataDto;
import com.net.anthill.model.Note;
import com.net.anthill.model.Ticket;
import com.net.anthill.model.UserMetadata;
import com.net.anthill.repository.NoteRepo;
import com.net.anthill.security.AuthenticationFacade;
import com.net.anthill.service.NoteService;
import com.net.anthill.service.TicketService;
import com.net.anthill.service.UserMetadataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
class NoteServiceUnitTest {


	@InjectMocks
	private com.net.anthill.service.NoteService NoteService;
	@Mock
	private NoteRepo noteRepository;


	@Mock
	private AuthenticationFacade authenticationFacade;

	@Mock
	private UserMetadataService userMetadataService;
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
		Mockito.when(noteRepository.findById(note.getId())).thenReturn(Optional.of(note));

		//WHEN
		NoteDto result = NoteService.getNoteById(note.getId());

		//THEN
		verify(noteRepository).findById(note.getId());
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(note.getId());
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
		String username = "TestUsername";
		NoteDto noteDto = buildNoteDto();
		Authentication authentication = buildAuthentication(username);
		UserMetadataDto userMetadataDto = buildUserMetadataDto(username);
		ArgumentCaptor<Note> saveMethodCapture = ArgumentCaptor.forClass(Note.class);
		//ArgumentCaptor<Note> addNoteToTicketCapture = ArgumentCaptor.forClass(Note.class);
		Mockito.when(noteRepository.save(any(Note.class))).then(AdditionalAnswers.returnsFirstArg());

		Mockito.when(authenticationFacade.getAuthentication()).thenReturn(authentication);
		Mockito.when(userMetadataService.getUserMetadataByUsername(username)).thenReturn(userMetadataDto);

		//doNothing().when(ticketService).addNoteToTicket(any(Note.class));

		//WHEN
		NoteService.createNote(noteDto);


		//THEN
		verify(noteRepository).save(saveMethodCapture.capture());
		Note persistedItem = saveMethodCapture.getValue();
		assertThat(persistedItem).isNotNull();
		assertThat(persistedItem.getId()).isEqualTo(noteDto.getId());
		assertThat(persistedItem.getCreator()).isNotNull();
		assertThat(persistedItem.getCreator().getUsername()).isEqualTo(username);
		//verify(ticketService).addNoteToTicket(addNoteToTicketCapture.capture());
		//assertThat(addNoteToTicketCapture.getValue()).isEqualTo(saveMethodCapture.getValue());
	}

	@Test
	void GIVEN_noteId_WHEN_deleteNoteById_THEN_noteDtoReturned() {
		//GIVEN
		Note note = buildNote();

		//WHEN
		NoteService.deleteNoteById(note.getId());

		//THEN
		verify(noteRepository).deleteById(note.getId());
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
		NoteDto noteDto = new NoteDto();
		noteDto.setId(100L);
		noteDto.setName("Note test name");
		noteDto.setDescription("Note test description");
		noteDto.setDateCreated(new Date());
		noteDto.setTicketId(1L);
		return noteDto;
	}

	private Note buildNote() {
		return buildNote(2L);
	}

	private Note buildNote(long ticketId){
		Ticket ticket = new Ticket();
		ticket.setId(ticketId);
		return new Note(1L, "Test Testerson", "This is test note", new Date(), new Date(), ticket, new UserMetadata());
	}

	private List<Note> buildNotes(){
		List<Note> list = new ArrayList<>();
		for(int i = 0; i < 5; i++){
			list.add(buildNote(2L));
		}
		return list;
	}

	private Authentication buildAuthentication(String userName){
		Authentication authentication = Mockito.mock(Authentication.class);
		Mockito.when(authentication.getName()).thenReturn(userName);
		return authentication;
	}

	private UserMetadataDto buildUserMetadataDto(String username){
		UserMetadataDto userMetadataDto = new UserMetadataDto();
		userMetadataDto.setUsername(username);
		userMetadataDto.setDateCreated(new Date());
		return userMetadataDto;
	}
}
