package com.net.anthill.controller;

import com.net.anthill.dto.UserLoginDto;
import com.net.anthill.service.UserLoginService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserLoginController {

 private UserLoginService userLoginService;

  @Autowired
  public UserLoginController(UserLoginService userLoginService) {
    this.userLoginService = userLoginService;
  }

  @PostMapping(value="/api/admin/users")
  @ResponseBody
  public void createUserLogin(@RequestBody UserLoginDto userLoginDto){
    userLoginService.createUserLogin(userLoginDto);
  }


  @GetMapping(value="/api/admin/users")
  @ResponseBody
  public UserLoginDto getUserLogin(@NotBlank @RequestParam String username){
    return userLoginService.getUserLoginById(username);
  }

}
