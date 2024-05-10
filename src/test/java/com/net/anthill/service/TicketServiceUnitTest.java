package com.net.anthill.service;

import com.net.anthill.dto.TicketDto;
import com.net.anthill.model.Ticket;
import com.net.anthill.repository.TicketRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
	void GIVEN_nothing_WHEN_getPaginatedTickets_THEN_listOfTicketDtosReturned() {
		//GIVEN
		List<Ticket> listOfTickets = buildTickets();
		Pageable paegable = PageRequest.of(0,5);
		Page page = new PageImpl(listOfTickets, paegable, listOfTickets.size());

		ArgumentCaptor<Pageable> pageableCapture = ArgumentCaptor.forClass(Pageable.class);
		Mockito.when(ticketRepository.findAll(any(Pageable.class))).thenReturn(page);

		//WHEN
		List<TicketDto> result = ticketService.getPaginatedTickets(paegable.getPageNumber(), paegable.getPageSize());

		//THEN
		assertThat(result).hasSize(listOfTickets.size());
		verify(ticketRepository).findAll(pageableCapture.capture());
		assertThat(pageableCapture.getValue()).isEqualTo(paegable);
		verify(ticketRepository).findAll(paegable);
	}

	@Test
	void GIVEN_noteId_WHEN_getTicketByIdr_THEN_TicketDtoReturned() {
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
	void GIVEN_TicketDto_WHEN_createTicket_THEN_ticketCreated(){
		//GIVEN
		TicketDto ticket = buildTicketDto();
		ArgumentCaptor<Ticket> saveMethodCapture = ArgumentCaptor.forClass(Ticket.class);
		Mockito.when(ticketRepository.save(any(Ticket.class))).then(AdditionalAnswers.returnsFirstArg());

		//WHEN
		ticketService.createTicket(ticket);

		//THEN
		verify(ticketRepository).save(saveMethodCapture.capture());
		assertThat(saveMethodCapture.getValue().getTicketId()).isEqualTo(ticket.getId());
	}

	@Test
	void GIVEN_ticketId_WHEN_deleteTicketById_THEN_ticketDtoReturned() {
		//GIVEN
		Ticket ticket = buildTicket();

		//WHEN
		ticketService.deleteTicketById(ticket.getTicketId());

		//THEN
		verify(ticketRepository).deleteById(ticket.getTicketId());
	}

	//TODO add mock for mapper when able
	@Test
	void GIVEN_TicketDto_WHEN_updateTicket_THEN_ticketCreated(){
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
