package com.net.anthill.initialSetup;

import com.net.anthill.dto.AuthorityDto;
import com.net.anthill.dto.UserLoginDto;
import com.net.anthill.model.Authority;
import com.net.anthill.security.SystemAuthority;
import com.net.anthill.service.AuthorityService;
import com.net.anthill.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class NewDbSetup {

  private UserLoginService userLoginService;

  private AuthorityService authorityService;
  @Autowired
  public NewDbSetup(UserLoginService userLoginService, AuthorityService authorityService) {
    this.userLoginService = userLoginService;
    this.authorityService = authorityService;
  }

  public void initiateDatabaseOnFirstDbExecution(){
    AuthorityDto authorityDto = new AuthorityDto("admin", SystemAuthority.FULL_ACCESS.name());
    authorityService.createAuthority(authorityDto);
    UserLoginDto userLoginDto = new UserLoginDto("admin", "password",true); //TODO make this configurable
    try {
      userLoginService.createUserLogin(userLoginDto);
    }catch(UsernameNotFoundException e){
      System.out.println("User exists no setup required");
    }
  }

}
