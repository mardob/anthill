package com.net.anthill.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="USERS")
public class User {

    @Getter @Setter @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="FIRST_NAME")
    @Getter @Setter private String firstName;

    @Column(name="LAST_NAME")
    @Getter @Setter private String lastName;

    @Column(name="USER_NAME")
    @Getter @Setter private String userName;

    @NonNull @Getter @Setter private String email;

    @Column(name="DATE_CREATED")
    @CreationTimestamp
    @NonNull @Getter @Setter private Date dateCreated = new Date();

    @Column(name="LAST_LOGGED_IN")
    @Getter @Setter private Date lastLoggedIn;

    @Column(name="LAST_UPDATED")
    @UpdateTimestamp
    @Getter @Setter private Date lastUpdated;

    @OneToMany(mappedBy = "reporter", cascade = CascadeType.ALL)
    @Getter @Setter private Set<Ticket> tickets;


    //TODO future:
    // tickets assigned

}


// https://thorben-janssen.com/persist-creation-update-timestamps-hibernate/
// https://tyk.io/rest-never-crud/
