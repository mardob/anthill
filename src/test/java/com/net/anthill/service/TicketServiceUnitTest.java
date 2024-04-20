package com.net.anthill.service;

import com.net.anthill.dto.NoteDto;
import com.net.anthill.dto.TicketDto;
import com.net.anthill.model.Note;
import com.net.anthill.model.Ticket;
import com.net.anthill.model.User;
import com.net.anthill.repository.NoteRepo;
import com.net.anthill.repository.TicketRepo;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@SpringBootTest
class TicketServiceUnitTest {

	@Mock
	private TicketRepo ticketRepository;

	@InjectMocks
	TicketService ticketService;

	@BeforeEach
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	//TODO add mock for mapper when able
	@Test
	void GIVENnothingWHENgetTicketsTHENlistOfNoteDtosReturned() {
		//GIVEN
		List<Ticket> listOfTickets = buildTickets();
		Mockito.when(ticketRepository.findAll()).thenReturn(listOfTickets);

		//WHEN
		List<TicketDto> result = ticketService.getTickets();

		//THEN
		assertThat(result).hasSize(listOfTickets.size());
		verify(ticketRepository).findAll();
	}

	//TODO add mock for mapper when able
	@Test
	void GIVENnoteIdWHENgetNoteByIdTHETicketDtoReturned() {
		//GIVEN
		Ticket ticket = buildTicket();
		Mockito.when(ticketRepository.findById(ticket.getTicketId())).thenReturn(Optional.of(ticket));

		//WHEN
		TicketDto result = ticketService.getTicketById(ticket.getTicketId());

		//THEN
		verify(ticketRepository).findById(ticket.getTicketId());
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(ticket.getTicketId());
	}

	@Test
	void GIVENoteDtoWHENcreateNoteTHENnoteCreated(){
		//GIVEN
		TicketDto ticket = buildTicketDto();
		ArgumentCaptor<Ticket> saveMethodCapture = ArgumentCaptor.forClass(Ticket.class);
		Mockito.when(ticketRepository.save(any(Ticket.class))).then(AdditionalAnswers.returnsFirstArg());

		//WHEN
		ticketService.createTicket(buildTicketDto());

		//THEN
		verify(ticketRepository).save(saveMethodCapture.capture());
		assertThat(saveMethodCapture.getValue().getTicketId()).isEqualTo(ticket.getId());
	}

	@Test
	void GIVENnoteIdWHENdeleteNoteByIdTHENnoteDtoReturned() {
		//GIVEN
		Ticket ticket = buildTicket();

		//WHEN
		ticketService.deleteTicketById(ticket.getTicketId());

		//THEN
		verify(ticketRepository).deleteById(ticket.getTicketId());
	}

	//TODO add mock for mapper when able
	@Test
	void GIVENoteDtoWHENupdateNoteTHENnoteCreated(){
		//GIVEN
		ArgumentCaptor<Ticket> saveMethodCapture = ArgumentCaptor.forClass(Ticket.class);
		Mockito.when(ticketRepository.save(any(Ticket.class))).then(AdditionalAnswers.returnsFirstArg());

		//WHEN
		ticketService.updateTicket(buildTicketDto());


		//THEN
		verify(ticketRepository).save(saveMethodCapture.capture());
	}







	private TicketDto buildTicketDto(){
		return new TicketDto();
	}

	private Ticket buildTicket() {
		return buildTicket(2L);
	}

	private Ticket buildTicket(long ticketId){
		Ticket ticket = new Ticket();
		ticket.setTicketId(ticketId);
		return ticket;
	}

	private List<Ticket> buildTickets(){
		List<Ticket> list = new ArrayList<>();
		for(int i = 0; i < 5; i++){
			list.add(buildTicket(2L));
		}
		return list;
	}
}
