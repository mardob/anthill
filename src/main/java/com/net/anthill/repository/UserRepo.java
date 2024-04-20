package com.net.anthill.repository;

import com.net.anthill.model.Note;
import com.net.anthill.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@RestResource(exported = false)
@Repository
public interface UserRepo extends CrudRepository<User, Long> {

}
