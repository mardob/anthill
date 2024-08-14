package com.net.anthill.repository;

import com.net.anthill.model.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(exported = false)
public interface TicketRepo extends CrudRepository<Ticket, Long>, PagingAndSortingRepository<Ticket, Long> {

}
