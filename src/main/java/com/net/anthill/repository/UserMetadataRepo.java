package com.net.anthill.repository;

import com.net.anthill.model.UserMetadata;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserMetadataRepo extends CrudRepository<UserMetadata, Long> {
  UserMetadata findUserMetadataByUsername(String username);


}
