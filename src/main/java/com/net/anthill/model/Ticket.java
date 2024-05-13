package com.net.anthill.model;

import com.net.anthill.constants.Severity;
import com.net.anthill.constants.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.validation.constraints.NotBlank;


import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter @Id
    private long ticketId;

    @Setter @NotBlank(message = "Items Name may not be blank")
    private String name;

    @Setter @NotBlank(message = "Items Description may not be blank")
    private String description;

    @Column(name="DATE_CREATED")
    @CreationTimestamp
    @NonNull
    @Setter private Date dateCreated;

    @Column(name="LAST_CHECKED")
    @Setter private Date lastChecked;

    @Column(name="LAST_UPDATED")
    @UpdateTimestamp
    @Setter private Date lastUpdated;

    @Enumerated(EnumType.ORDINAL)
    @Setter private Status status;

    @Enumerated(EnumType.ORDINAL)
    @Setter private Severity severity;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Setter private UserMetadata assignedUser;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @Setter private UserMetadata reportingUser;




  //  @Getter @Setter private Pile pile;

    //TODO future:
    // assignedUser
    // reporter

    // typeOfTicket is it bug, new feature
}
