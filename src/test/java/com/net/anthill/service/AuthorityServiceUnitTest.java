package com.net.anthill.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import com.net.anthill.dto.AuthorityDto;
import com.net.anthill.model.Authority;
import com.net.anthill.model.UserLogin;
import com.net.anthill.repository.AuthoritiesRepo;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AuthorityServiceUnitTest {

  @Mock
  private AuthoritiesRepo authoritiesRepo;

  @InjectMocks
  AuthorityService authorityService;


  @BeforeEach
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }


  @Test
  void GIVEN_userNameAndAuthorityExists_WHEN_fetchAuthorities_THEN_listOfAuthorityDtoReturned() {
    //GIVEN
    Authority authority = createAuthority();
    Mockito.when(authoritiesRepo.findAuthorityByUsername(authority.getUsername())).thenReturn(
        List.of(authority));

    //WHEN
    List<AuthorityDto> result = authorityService.fetchAuthorities(authority.getUsername());

    //THEN
    assertThat(result).isNotEmpty();
    assertThat(result.get(0).getUsername()).isEqualTo(authority.getUsername());
    assertThat(result.get(0).getAuthority()).isEqualTo(authority.getAuthority());
  }


  @Test
  void GIVEN_authorityDtoAndAuthorityDoesntExist_WHEN_createAuthority_THEN_AuthorityCreated() {
    //GIVEN
    AuthorityDto authorityDto = createAuthorityDto();

    Mockito.when(authoritiesRepo.existsAuthorityByUsernameAndAuthority(authorityDto.getUsername(), authorityDto.getAuthority()))
        .thenReturn(false);
    ArgumentCaptor<Authority> saveMethodCapture = ArgumentCaptor.forClass(Authority.class);
    Mockito.when(authoritiesRepo.save(any(Authority.class))).then(AdditionalAnswers.returnsFirstArg());


    //WHEN
    authorityService.createAuthority(authorityDto);

    //THEN
    verify(authoritiesRepo).save(saveMethodCapture.capture());
    Authority authority =  saveMethodCapture.getValue();
    assertThat(authority).isNotNull();
    assertThat(authority.getUsername()).isEqualTo(authorityDto.getUsername());
    assertThat(authority.getAuthority()).isEqualTo(authorityDto.getAuthority());
  }




  private Authority createAuthority() {
    Authority authority = new Authority();
    authority.setUsername("Test username");
    authority.setAuthority("Test Authority");
    return authority;
  }

  private AuthorityDto createAuthorityDto() {
    AuthorityDto authorityDto = new AuthorityDto();
    authorityDto.setUsername("Test username");
    authorityDto.setAuthority("Test authority");
    return authorityDto;
  }

}
