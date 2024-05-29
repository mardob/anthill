package com.net.anthill.initialSetup;

import com.net.anthill.dto.AuthorityDto;
import com.net.anthill.dto.UserLoginDto;
import com.net.anthill.security.SystemAuthority;
import com.net.anthill.service.AuthorityService;
import com.net.anthill.service.UserLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component @Slf4j
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
    if(!userLoginService.doesUserExist(userLoginDto.getUsername())){
      userLoginService.createUserLogin(userLoginDto);
    } else {
      log.debug(String.format("User [%s] exists no setup required", userLoginDto.getUsername()));
    }
  }

}
