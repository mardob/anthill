package com.net.anthill.service;

import com.net.anthill.dto.UserLoginDto;
import com.net.anthill.exception.ExternalErrorMessages;
import com.net.anthill.model.UserLogin;
import com.net.anthill.repository.UserLoginRepo;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service @Slf4j
public class UserLoginService implements UserDetailsService {

  private static PasswordEncoder ENCODER = PasswordEncoderFactories.createDelegatingPasswordEncoder();
  private UserLoginRepo userLoginRepository;

  private UserMetadataService userMetadataService;
  private ModelMapper modelMapper = new ModelMapper();


  @Autowired
  public UserLoginService(UserLoginRepo userLoginRepository, UserMetadataService userMetadataService){
    this.userLoginRepository = userLoginRepository;
    this.userMetadataService = userMetadataService;
  }

  public boolean doesUserExist(String username) {
    log.trace("doesUserExist started");
    System.out.println("Looking for user with following username:" + username);
    UserLogin userLogin = userLoginRepository.findUserLoginByUsername(username);
    log.trace("doesUserExist ended");
    return userLogin != null;
  }
  @Transactional
  public void createUserLogin(UserLoginDto userLoginDto){
    log.trace("createUserLogin started");
    if(userLoginRepository.existsUserByUsername(userLoginDto.getUsername())){
      String errorMessage = String.format(ExternalErrorMessages.USERNAME_ALREADY_EXISTS, userLoginDto.getUsername());
      log.warn(errorMessage);
      throw new RuntimeException(errorMessage);
    }
    log.debug("Username " + userLoginDto.getUsername() + " not found, creating");
    userLoginDto.setPassword(ENCODER.encode(userLoginDto.getPassword()));

    UserLogin userLogin = modelMapper.map(userLoginDto, UserLogin.class);
    log.debug("username to persist " + userLogin.getUsername());
    userLoginRepository.save(userLogin);
    userMetadataService.createMetadata(userLogin.getUsername());
    log.trace("createUserLogin ended");
  }


  @Override
  public UserDetails loadUserByUsername(String username) {
    log.trace("loadUserByUsername started");
    System.out.println(username);
    UserLogin userLogin = userLoginRepository.findUserLoginByUsername(username);
    if (userLogin == null) {
      String errorMessage = String.format(ExternalErrorMessages.USERNAME_DOESNT_EXISTS, username);
      log.warn(errorMessage);
      throw new RuntimeException(errorMessage);
    }
    log.trace("loadUserByUsername ended");
    return userLogin;
  }
}
