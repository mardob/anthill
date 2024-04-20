package com.net.anthill.repository;

import com.net.anthill.model.Note;
import com.net.anthill.model.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@Repository
@RestResource(exported = false)
public interface TicketRepo extends CrudRepository<Ticket, Long> {

}
