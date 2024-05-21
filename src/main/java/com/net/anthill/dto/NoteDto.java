package com.net.anthill.dto;


import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Name is mandatory") @Setter private String name;

    @NotBlank(message = "Description is mandatory") @Setter private String description;

    @Setter private Date dateCreated = new Date();

    @Setter private Long ticketId;

    @Setter private UserMetadataDto creator;

}
