package com.net.anthill.repository;

import com.net.anthill.model.Note;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface NoteRepo extends CrudRepository<Note, Long> {

    @Query("FROM Note n where n.ticket.id = :ticketId")
    List<Note> findNotesRelatedToTicket(@Param("ticketId") Long ticketId);

}
