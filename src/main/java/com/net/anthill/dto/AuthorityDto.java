package com.net.anthill.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class AuthorityDto {
  @NotBlank(message = "Username is mandatory") private String username;

  @NotBlank(message = "Authority is mandatory") private String authority;
}
