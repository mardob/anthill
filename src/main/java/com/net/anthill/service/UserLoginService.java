package com.net.anthill.service;

import com.net.anthill.dto.UserLoginDto;
import com.net.anthill.model.Authority;
import com.net.anthill.model.UserLogin;
import com.net.anthill.repository.AuthoritiesRepo;
import com.net.anthill.repository.UserLoginRepo;
import jakarta.transaction.Transactional;
import java.util.List;
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
  private AuthoritiesRepo authoritiesRepo;
  private ModelMapper modelMapper = new ModelMapper();


  @Autowired
  public UserLoginService(UserLoginRepo userLoginRepository, AuthoritiesRepo authoritiesRepo){
    this.userLoginRepository = userLoginRepository;
    this.authoritiesRepo = authoritiesRepo;
  }

  public UserLoginDto getUserLoginById(String username) {
    System.out.println("Looking for user with following username:" + username);
    UserLogin userLogin = userLoginRepository.findUserLoginByUserName(username);
    return modelMapper.map(userLogin, UserLoginDto.class);
  }
  @Transactional
  public void createUserLogin(UserLoginDto userLoginDto){
    if(userLoginRepository.existsUserByUserName(userLoginDto.getUserName())){
      System.out.println("User "+userLoginDto.getUserName()+" found");
      throw new UsernameNotFoundException(userLoginDto.getUserName());
    }
    System.out.println("User not found, creating");
    userLoginDto.setPassword(ENCODER.encode(userLoginDto.getPassword()));

    UserLogin userLogin = modelMapper.map(userLoginDto, UserLogin.class);
    userLoginRepository.save(userLogin);

  }


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    System.out.println(username);
    UserLogin userLogin = userLoginRepository.findUserLoginByUserName(username);
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
