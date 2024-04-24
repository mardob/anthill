package com.net.anthill.model;

import com.net.anthill.cosntants.State;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.validation.constraints.NotBlank;


import java.io.Serializable;
import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    @Setter private State state = State.NEW;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ASSIGNEE_ID")
    @Setter private User assignedUser = null;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "REPORTER_ID")
    @Setter private User reporter;


  //  @Getter @Setter private Pile pile;

    //TODO future:
    // assignedUser
    // reporter

    //done
    // state
    // @Getter @Setter List<String> notes;


    // typeOfTicket is it bug, new feature
}
