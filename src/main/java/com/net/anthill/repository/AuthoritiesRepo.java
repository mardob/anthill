package com.net.anthill.repository;

import com.net.anthill.model.Authority;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@RestResource(exported = false) @Repository
public interface AuthoritiesRepo extends CrudRepository<Authority, Long> {
  public Boolean existsAuthorityByUsernameAndAuthority(String username, String authority);

  public List<Authority> findAuthorityByUsername(String username);
}
