package com.net.anthill.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Note  {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter @Id
    private long noteId;

    @Setter private String name;

    @Setter private String description;

    @Column(name="LAST_UPDATED")
    @UpdateTimestamp
    @Setter private Date lastUpdated;

    @Column(name="DATE_CREATED")
    @CreationTimestamp
    @NonNull
    @Setter private Date dateCreated = new Date();

  //  @OneToOne(cascade = CascadeType.ALL)
  //  @JoinColumn(name = "TICKET_ID")
    @ManyToOne
    @JoinColumn(name="ticketId", nullable=false)
    @NonNull
    @Setter private Ticket ticket;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CREATOR_ID")
    @Setter private User creator;


    //TODO future:
    // creator(user)
    // ticket it is noted in
}
