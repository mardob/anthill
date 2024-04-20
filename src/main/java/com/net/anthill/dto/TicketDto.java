package com.net.anthill.dto;

import com.net.anthill.model.Note;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;
import java.util.Set;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TicketDto extends RepresentationModel<TicketDto> {

    @Setter private long id;

    @Setter private String name;

    @Setter private String description;

    @Setter private Date dateCreated = new Date();

//    @Getter @Setter private Set<Note> notes;


}
