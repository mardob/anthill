package com.net.anthill.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Pile implements Serializable {

    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long pileId;

    //TODO causes 	Suppressed: org.hibernate.MappingException: Unable to determine SQL type name for column 'tickets' of table 'pile'
  //  @Getter @Setter
  //  private Set<Ticket> tickets;

    @Setter
    private String name;
}
