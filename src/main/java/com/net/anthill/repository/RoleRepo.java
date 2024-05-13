package com.net.anthill.repository;

import com.net.anthill.model.UserMetadata;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@RestResource(exported = false)
@Repository
public interface RoleRepo extends CrudRepository<UserMetadata,Long> {


}
