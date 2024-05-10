package com.net.anthill.service;

import com.net.anthill.dto.UserLoginDto;
import com.net.anthill.model.UserLogin;
import com.net.anthill.repository.AuthoritiesRepo;
import com.net.anthill.repository.UserLoginRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
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

  public UserLoginDto getUserLoginById(String username) {
    System.out.println("Looking for user with following username:" + username);
    UserLogin userLogin = userLoginRepository.findUserLoginByUsername(username);
    return modelMapper.map(userLogin, UserLoginDto.class);
  }
  @Transactional
  public void createUserLogin(UserLoginDto userLoginDto){
    if(userLoginRepository.existsUserByUsername(userLoginDto.getUsername())){
      System.out.println("User "+userLoginDto.getUsername()+" found");
      throw new UsernameNotFoundException(userLoginDto.getUsername());
    }
    System.out.println("Username " + userLoginDto.getUsername() + " not found, creating");
    userLoginDto.setPassword(ENCODER.encode(userLoginDto.getPassword()));

    UserLogin userLogin = modelMapper.map(userLoginDto, UserLogin.class);
    System.out.println("username to add " + userLogin.getUsername());
    userLoginRepository.save(userLogin);
    userMetadataService.createMetadata(userLogin.getUsername());
  }


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    System.out.println(username);
    UserLogin userLogin = userLoginRepository.findUserLoginByUsername(username);
    System.out.println(userLogin.getUsername());
    if (userLogin == null) {
      System.out.println("Never found");
      throw new UsernameNotFoundException(username);
    }
    System.out.println("returns " + userLogin.getUsername());
    System.out.println( " / " + userLogin.getPassword() );
    System.out.println(" / "+ userLogin.getAuthorities());

    return userLogin;
  }
}
