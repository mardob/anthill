package com.net.anthill.repository;

import com.net.anthill.model.UserLogin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@RestResource(exported = false) @Repository
public interface UserLoginRepo extends CrudRepository<UserLogin, Long> {
  public Boolean existsUserByUsername(String username);


  public UserLogin findUserLoginByUsername(String username);

}
