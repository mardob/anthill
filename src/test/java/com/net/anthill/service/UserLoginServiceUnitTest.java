package com.net.anthill.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import com.net.anthill.dto.UserLoginDto;
import com.net.anthill.model.UserLogin;
import com.net.anthill.repository.UserLoginRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest
public class UserLoginServiceUnitTest {

  @Mock
  private UserLoginRepo userLoginRepo;

  @Mock
  private UserMetadataService userMetadataService;

  @InjectMocks
  UserLoginService userLoginService;


  @BeforeEach
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }


  @Test
  void GIVEN_userNameAndUserExists_WHEN_getUserLoginById_THEN_UserLoginDtoReturned() {
    //GIVEN
    UserLogin userLogin = createUserLogin();
    Mockito.when(userLoginRepo.findUserLoginByUsername(userLogin.getUsername())).thenReturn(userLogin);

    //WHEN
    UserLoginDto result = userLoginService.getUserLoginById(userLogin.getUsername());

    //THEN
    assertThat(result).isNotNull();
    assertThat(result.getUsername()).isEqualTo(userLogin.getUsername());
  }


  @Test
  void GIVEN_userLoginDtoAndUserDoesntExist_WHEN_createUserLogin_THEN_UserLoginCreated() {
    //GIVEN
    String mockedPassword = "mockedPassword";
    UserLoginDto userLoginDto = createUserLoginDto(mockedPassword);

    Mockito.when(userLoginRepo.existsUserByUsername(userLoginDto.getUsername())).thenReturn(false);
    ArgumentCaptor<UserLogin> saveMethodCapture = ArgumentCaptor.forClass(UserLogin.class);
    Mockito.when(userLoginRepo.save(any(UserLogin.class))).then(AdditionalAnswers.returnsFirstArg());

    //WHEN
    userLoginService.createUserLogin(userLoginDto);

    //THEN
    Mockito.verify(userMetadataService).createMetadata(userLoginDto.getUsername());
    verify(userLoginRepo).save(saveMethodCapture.capture());
    UserLogin sendUserLogin =  saveMethodCapture.getValue();
    assertThat(sendUserLogin).isNotNull();
    assertThat(sendUserLogin.getUsername()).isEqualTo(userLoginDto.getUsername());
    assertThat(sendUserLogin.getPassword()).isNotEqualTo(mockedPassword);
    assertThat(sendUserLogin.getAuthorities()).isNullOrEmpty();
  }


  @Test
  void GIVEN_usernameAndUserExists_WHEN_loadUserByUsername_THEN_UserDetailsReturned() {
    //GIVEN
    UserLogin userLogin = createUserLogin();

    Mockito.when(userLoginRepo.findUserLoginByUsername(userLogin.getUsername())).thenReturn(userLogin);

    //WHEN
    UserDetails result = userLoginService.loadUserByUsername(userLogin.getUsername());

    //THEN
    assertThat(result).isNotNull();
    assertThat(result.getUsername()).isEqualTo(userLogin.getUsername());
    assertThat(result.getPassword()).isEqualTo(userLogin.getPassword());
    assertThat(result.getAuthorities()).isNullOrEmpty();
  }


  private UserLogin createUserLogin() {
    UserLogin userLogin = new UserLogin();
    userLogin.setUsername("Test username");
    return userLogin;
  }

  private UserLoginDto createUserLoginDto(String mockedPassword) {
    UserLoginDto userLogin = new UserLoginDto();
    userLogin.setUsername("Test username");
    userLogin.setEnabled(true);
    userLogin.setPassword(mockedPassword);
    return userLogin;
  }

}
