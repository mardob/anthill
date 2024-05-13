package com.net.anthill.model;

import com.net.anthill.constants.Severity;
import com.net.anthill.constants.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @Setter private UserMetadata assignedUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @Setter private UserMetadata reportingUser;




  //  @Getter @Setter private Pile pile;

    //TODO future:
    // assignedUser
    // reporter

    // typeOfTicket is it bug, new feature
}
