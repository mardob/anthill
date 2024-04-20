package com.net.anthill.repository;

import com.net.anthill.model.Note;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@RestResource(exported = false)
@Repository
public interface NoteRepo extends CrudRepository<Note, Long> {

    @Query("FROM Note n where n.ticket.id = :ticketId")
    List<Note> findNotesRelatedToTicket(@Param("ticketId") Long ticketId);

}
