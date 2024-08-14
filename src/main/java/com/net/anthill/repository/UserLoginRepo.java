package com.net.anthill.repository;

import com.net.anthill.model.UserLogin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface UserLoginRepo extends CrudRepository<UserLogin, Long> {
  Boolean existsUserByUsername(String username);


  UserLogin findUserLoginByUsername(String username);

}
