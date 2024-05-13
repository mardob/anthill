package com.net.anthill.service;

import com.net.anthill.dto.AuthorityDto;
import com.net.anthill.model.Authority;
import com.net.anthill.repository.AuthoritiesRepo;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService {
  private AuthoritiesRepo authoritiesRepo;
  private ModelMapper modelMapper;

  @Autowired
  public AuthorityService(AuthoritiesRepo authoritiesRepo) {
    this.authoritiesRepo = authoritiesRepo;
    this.modelMapper = new ModelMapper();
  }

  public void createAuthority(AuthorityDto authorityDto) {
    if(authoritiesRepo.existsAuthorityByUsernameAndAuthority(authorityDto.getUsername(), authorityDto.getAuthority())){
      System.out.println("User has this authority " + authorityDto.getAuthority());
      return;
    }

    authoritiesRepo.save(modelMapper.map(authorityDto, Authority.class));
    System.out.println("Authority "+ authorityDto.getAuthority()+" added to user "+ authorityDto.getUsername());
  }


  public List<AuthorityDto> fetchAuthorities(String username) {
    List<Authority> authorities = authoritiesRepo.findAuthorityByUsername(username);
    return authorities.stream().map(item -> modelMapper.map(item, AuthorityDto.class)).toList();
  }
  }
