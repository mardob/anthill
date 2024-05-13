package com.net.anthill.controller;

import com.net.anthill.dto.AuthorityDto;
import com.net.anthill.dto.UserLoginDto;
import com.net.anthill.service.AuthorityService;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AuthorityController {

 private AuthorityService authorityService;

  @Autowired
  public AuthorityController(AuthorityService authorityService) {
    this.authorityService = authorityService;
  }

  @PostMapping(value="/api/admin/authorities")
  @ResponseBody
  public void assignAuthority(@RequestBody AuthorityDto authorityDto){
    authorityService.createAuthority(authorityDto);
  }


  @GetMapping(value="/api/admin/authorities")
  @ResponseBody
  public List<AuthorityDto> getAuthority(@NotBlank @RequestParam String username){
    return authorityService.fetchAuthorities(username);
  }

}
