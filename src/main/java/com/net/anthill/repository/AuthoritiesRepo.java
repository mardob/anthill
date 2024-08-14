package com.net.anthill.repository;

import com.net.anthill.model.Authority;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface AuthoritiesRepo extends CrudRepository<Authority, Long> {
  Boolean existsAuthorityByUsernameAndAuthority(String username, String authority);

  List<Authority> findAuthorityByUsername(String username);
}
