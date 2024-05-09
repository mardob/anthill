package com.net.anthill.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class UserLoginDto {
  private String userName;

  private String password;

  private Boolean enabled;
}
