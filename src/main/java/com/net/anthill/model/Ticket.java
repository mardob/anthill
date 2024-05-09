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
    @Setter private Date dateCreated = new Date();

    @Column(name="LAST_CHECKED")
    @Setter private Date lastChecked = null;

    @Column(name="LAST_UPDATED")
    @UpdateTimestamp
    @Setter private Date lastUpdated = null;

    @Enumerated(EnumType.ORDINAL)
    @Setter private Status status;

    @Enumerated(EnumType.ORDINAL)
    @Setter private Severity severity;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ASSIGNEE_ID")
    @Setter private UserMetaData assignedUserMetaData = null;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "REPORTER_ID")
    @Setter private UserMetaData reporter;


  //  @Getter @Setter private Pile pile;

    //TODO future:
    // assignedUser
    // reporter

    // typeOfTicket is it bug, new feature
}
