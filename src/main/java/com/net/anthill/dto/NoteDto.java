package com.net.anthill.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NoteDto extends RepresentationModel<NoteDto> {

    @Setter private long id;

    @Setter private String name;

    @Setter private String description;

    @Setter private Date dateCreated = new Date();

    @Setter private Long ticketId;

    @Setter private UserMetadataDto creator;

}
