package com.net.anthill.initialSetup;

import com.net.anthill.config.SystemConfigurations;
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

  private SystemConfigurations systemConfigurations;

  @Autowired
  public NewDbSetup(UserLoginService userLoginService, AuthorityService authorityService, SystemConfigurations systemConfigurations) {
    this.userLoginService = userLoginService;
    this.authorityService = authorityService;
    this.systemConfigurations = systemConfigurations;
  }

  public void initiateDatabaseOnFirstDbExecution(){
    log.trace("initiateDatabaseOnFirstDbExecution started");
    UserLoginDto userLoginDto = createUserDto();
    if(!userLoginService.doesUserExist(userLoginDto.getUsername())){
      log.info(String.format("User [%s] doesn't exist, setup required", userLoginDto.getUsername()));
      userLoginService.createUserLogin(userLoginDto);
      createAuthorityForUser(userLoginDto.getUsername());
    } else {
      log.info(String.format("User [%s] exists, no setup required", userLoginDto.getUsername()));
    }

    log.trace("initiateDatabaseOnFirstDbExecution ended");
  }

  private void createAuthorityForUser(String adminUsername){
    AuthorityDto authorityDto = new AuthorityDto(adminUsername, SystemAuthority.FULL_ACCESS.name());
    authorityService.createAuthority(authorityDto);
  }

  private UserLoginDto createUserDto(){
    String adminPassword = systemConfigurations.getAdminPassword();
    String adminUsername = systemConfigurations.getAdminUsername();
    if(adminUsername == null){
      adminUsername = "admin";
    }
    if(adminPassword == null){
      adminPassword = "password";
    }
    return new UserLoginDto(adminUsername, adminPassword,true);
  }

}
