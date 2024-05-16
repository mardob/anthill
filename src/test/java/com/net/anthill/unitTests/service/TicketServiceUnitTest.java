package com.net.anthill.unitTests.service;

import com.net.anthill.constants.ApiConstants;
import com.net.anthill.dto.TicketDto;
import com.net.anthill.dto.UserMetadataDto;
import com.net.anthill.model.Ticket;
import com.net.anthill.repository.TicketRepo;
import com.net.anthill.security.AuthenticationFacade;
import com.net.anthill.service.TicketService;
import com.net.anthill.service.UserMetadataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
class TicketServiceUnitTest {

	@Mock
	private TicketRepo ticketRepository;

	@Mock
	private AuthenticationFacade authenticationFacade;

	@Mock
	private UserMetadataService userMetadataService;
	@InjectMocks
	private TicketService ticketService;

	@BeforeEach
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	//TODO add mock for mapper when able

	@Test
	void GIVEN_nothing_WHEN_getPaginatedTickets_THEN_listOfTicketDtosReturned() {
		//GIVEN
		List<Ticket> listOfTickets = buildTickets();
		Sort sort = Sort.by(ApiConstants.DEFAULT_SORT_BY).ascending();
		Pageable paegable = PageRequest.of(0,5, sort);
		Page page = new PageImpl(listOfTickets, paegable, listOfTickets.size());

		ArgumentCaptor<Pageable> pageableCapture = ArgumentCaptor.forClass(Pageable.class);
		Mockito.when(ticketRepository.findAll(any(Pageable.class))).thenReturn(page);

		//WHEN
		Page<TicketDto> result = ticketService.getPaginatedTickets(paegable.getPageNumber(), paegable.getPageSize(), ApiConstants.DEFAULT_SORT_BY, ApiConstants.DEFAULT_SORT_DIRECTION);

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
		Mockito.when(ticketRepository.findById(ticket.getId())).thenReturn(Optional.of(ticket));

		//WHEN
		TicketDto result = ticketService.getTicketById(ticket.getId());

		//THEN
		verify(ticketRepository).findById(ticket.getId());
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(ticket.getId());
	}

	@Test
	void GIVEN_TicketDtoAndUserDetails_WHEN_createTicket_THEN_ticketCreated(){
		//GIVEN
		String username = "TestUsername";
		TicketDto ticket = buildTicketDto();
		Authentication authentication = buildAuthentication(username);
		UserMetadataDto userMetadataDto = buildUserMetadataDto(username);

		ArgumentCaptor<Ticket> saveMethodCapture = ArgumentCaptor.forClass(Ticket.class);
		Mockito.when(ticketRepository.save(any(Ticket.class))).then(AdditionalAnswers.returnsFirstArg());
		Mockito.when(authenticationFacade.getAuthentication()).thenReturn(authentication);
		Mockito.when(userMetadataService.getUserMetadataByUsername(username)).thenReturn(userMetadataDto);

		//WHEN
		TicketDto resultingTicketDto = ticketService.createTicket(ticket);

		//THEN
		verify(ticketRepository).save(saveMethodCapture.capture());
		Ticket persistedItem = saveMethodCapture.getValue();
		assertThat(persistedItem).isNotNull();
		assertThat(persistedItem.getId()).isEqualTo(ticket.getId());
		assertThat(persistedItem.getReportingUser()).isNotNull();
		assertThat(persistedItem.getReportingUser().getUsername()).isEqualTo(username);
		assertThat(resultingTicketDto).isNotNull();
	}

	@Test
	void GIVEN_ticketId_WHEN_deleteTicketById_THEN_ticketDtoReturned() {
		//GIVEN
		Ticket ticket = buildTicket();

		//WHEN
		ticketService.deleteTicketById(ticket.getId());

		//THEN
		verify(ticketRepository).deleteById(ticket.getId());
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
		ticket.setId(ticketId);
		return ticket;
	}

	private List<Ticket> buildTickets(){
		List<Ticket> list = new ArrayList<>();
		for(int i = 0; i < 5; i++){
			list.add(buildTicket(2L));
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
