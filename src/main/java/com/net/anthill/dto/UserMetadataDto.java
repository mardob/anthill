package com.net.anthill.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserMetadataDto {
  private long id;

  private String username;

  private Date dateCreated;

  private Date lastLoggedIn;

  private Date lastUpdated;
}
