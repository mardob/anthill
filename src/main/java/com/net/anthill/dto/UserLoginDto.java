package com.net.anthill.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class UserLoginDto {
  private String userName;

  private String password;

  private Boolean enabled;
}
