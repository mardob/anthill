package com.net.anthill.service;

import com.net.anthill.dto.UserMetadataDto;
import com.net.anthill.model.UserMetadata;
import com.net.anthill.repository.UserMetadataRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMetadataService {
  private UserMetadataRepo userMetadataRepo;
  private ModelMapper modelMapper = new ModelMapper();

  @Autowired
  public UserMetadataService(UserMetadataRepo userMetadataRepo) {
    this.userMetadataRepo = userMetadataRepo;
  }

  public void createMetadata(String username){//TODO add logic in case it would exist already
    UserMetadata userMetadata = new UserMetadata();
    userMetadata.setUsername(username);
    userMetadataRepo.save(userMetadata);
  }

  public UserMetadataDto getUserMetadataByUsername(String username){
    UserMetadata userMetadata = userMetadataRepo.findUserMetadataByUsername(username);
    return modelMapper.map(userMetadata, UserMetadataDto.class);
  }


}
