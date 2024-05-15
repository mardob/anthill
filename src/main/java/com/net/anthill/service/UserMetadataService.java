package com.net.anthill.service;

import com.net.anthill.dto.UserMetadataDto;
import com.net.anthill.model.UserMetadata;
import com.net.anthill.repository.UserMetadataRepo;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service @Slf4j
public class UserMetadataService {
  private UserMetadataRepo userMetadataRepo;
  private ModelMapper modelMapper = new ModelMapper();

  @Autowired
  public UserMetadataService(UserMetadataRepo userMetadataRepo) {
    this.userMetadataRepo = userMetadataRepo;
  }

  public void createMetadata(String username){//TODO add logic in case it would exist already
    log.trace("createMetadata started");
    UserMetadata userMetadata = new UserMetadata();
    userMetadata.setUsername(username);
    userMetadataRepo.save(userMetadata);
    log.trace("createMetadata ended");
  }

  public UserMetadataDto getUserMetadataByUsername(String username){
    log.trace("getUserMetadataByUsername started");
    UserMetadata userMetadata = userMetadataRepo.findUserMetadataByUsername(username);
    UserMetadataDto userMetadataDto = modelMapper.map(userMetadata, UserMetadataDto.class);
    log.trace("getUserMetadataByUsername ended");
    return userMetadataDto;
  }


}
