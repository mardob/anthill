package com.net.anthill.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Table(name="USER_METADATA")
public class UserMetaData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="USER_NAME")
    private String userName;

    @NonNull private String email;

    @Column(name="DATE_CREATED")
    @CreationTimestamp
    @NonNull private Date dateCreated = new Date();

    @Column(name="LAST_LOGGED_IN")
    private Date lastLoggedIn;

    @Column(name="LAST_UPDATED")
    @UpdateTimestamp
    private Date lastUpdated;

  /*  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name="USER_ROLES", joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "id"))
    private List<Role> roles;*/
    // @OneToMany(mappedBy = "reporter", cascade = CascadeType.ALL)
  //  @Getter @Setter private Set<Ticket> tickets;

}
