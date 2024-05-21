package com.net.anthill.dto;

import com.net.anthill.constants.Severity;
import com.net.anthill.constants.Status;
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
public class TicketDto extends RepresentationModel<TicketDto> {

    @Setter private long id;

    @NotBlank(message = "Name is mandatory") @Setter private String name;

    @NotBlank(message = "Description is mandatory") @Setter private String description;

    @Setter private Date dateCreated = new Date();

    @Setter private Status status;

    @Setter private Severity severity;

    @Setter private UserMetadataDto assignedUser;

    @Setter private UserMetadataDto reportingUser;
}
