package com.net.anthill.controller;

import com.net.anthill.dto.UserLoginDto;
import com.net.anthill.dto.UserMetadataDto;
import com.net.anthill.service.UserLoginService;
import com.net.anthill.service.UserMetadataService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserLoginController {

 private UserLoginService userLoginService;
 private UserMetadataService userMetadataService;

  @Autowired
  public UserLoginController(UserLoginService userLoginService, UserMetadataService userMetadataService) {
    this.userLoginService = userLoginService;
    this.userMetadataService = userMetadataService;
  }

  @PostMapping(value="/v1/admin/users")
  @ResponseBody
  public void createUserLogin(@Valid @RequestBody UserLoginDto userLoginDto){
    userLoginService.createUserLogin(userLoginDto);
  }


  @GetMapping(value="/v1/admin/users")
  @ResponseBody
  public UserMetadataDto getUserMetadataDto(@NotBlank @RequestParam String username){
    return userMetadataService.getUserMetadataByUsername(username);
  }

}
