package com.net.anthill.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name="USERS_AUTHORITY")
public class Authority implements GrantedAuthority {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @Column(name = "username")
  private String username;

  private String authority;

  public Authority(String username, String authority) {
    this.username = username;
    this.authority = authority;
  }
}
