package com.net.anthill.repository;

import com.net.anthill.model.UserMetadata;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMetadataRepo extends CrudRepository<UserMetadata, Long> {
  public UserMetadata findUserMetadataByUsername(String username);


}
