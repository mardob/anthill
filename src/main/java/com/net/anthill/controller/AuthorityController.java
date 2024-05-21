package com.net.anthill.controller;

import com.net.anthill.dto.AuthorityDto;
import com.net.anthill.service.AuthorityService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AuthorityController {

 private AuthorityService authorityService;

  @Autowired
  public AuthorityController(AuthorityService authorityService) {
    this.authorityService = authorityService;
  }

  @PostMapping(value="/v1/admin/authorities")
  @ResponseBody
  public void assignAuthority(@Valid @RequestBody AuthorityDto authorityDto){
    authorityService.createAuthority(authorityDto);
  }


  @GetMapping(value="/v1/admin/authorities")
  @ResponseBody
  public List<AuthorityDto> getAuthority(@NotBlank @RequestParam String username){
    return authorityService.fetchAuthorities(username);
  }

}
