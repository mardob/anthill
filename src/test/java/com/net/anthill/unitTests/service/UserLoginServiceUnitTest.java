package com.net.anthill.unitTests.service;

import com.net.anthill.dto.UserLoginDto;
import com.net.anthill.exception.ExternalErrorMessages;
import com.net.anthill.model.UserLogin;
import com.net.anthill.repository.UserLoginRepo;
import com.net.anthill.service.UserLoginService;
import com.net.anthill.service.UserMetadataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class UserLoginServiceUnitTest {

  private static final String MOCK_USERNAME = "notUsed@test.com";
  private static final String MOCK_PASSWORD = "mockedPassword";

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
  void GIVEN_userNameAndUserExists_WHEN_getUserLoginById_THEN_UserFound() {
    //GIVEN
    UserLogin userLogin = createUserLogin();
    Mockito.when(userLoginRepo.findUserLoginByUsername(userLogin.getUsername())).thenReturn(userLogin);

    //WHEN
    boolean result = userLoginService.doesUserExist(userLogin.getUsername());

    //THEN
    assertThat(result).isTrue();
  }

  @Test
  void GIVEN_userNameAndUserDoesntExists_WHEN_getUserLoginById_THEN_UserNotFound() {
    //GIVEN

    //WHEN
    boolean result = userLoginService.doesUserExist(MOCK_USERNAME);

    //THEN
    assertThat(result).isFalse();
  }


  @Test
  void GIVEN_userLoginDtoAndUserDoesntExist_WHEN_createUserLogin_THEN_UserLoginCreated() {
    //GIVEN
    UserLoginDto userLoginDto = createUserLoginDto(MOCK_PASSWORD);

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
    assertThat(sendUserLogin.getPassword()).isNotEqualTo(MOCK_PASSWORD);
    assertThat(sendUserLogin.getAuthorities()).isNullOrEmpty();
  }

  @Test
  void GIVEN_userLoginDtoAndUserExist_WHEN_createUserLogin_THEN_ExceptionThrown() {
    //GIVEN
    UserLoginDto userLoginDto = createUserLoginDto(MOCK_PASSWORD);

    Mockito.when(userLoginRepo.existsUserByUsername(userLoginDto.getUsername())).thenReturn(true);
    String expectedErrorMessage = String.format(ExternalErrorMessages.USERNAME_ALREADY_EXISTS, userLoginDto.getUsername());

    //WHEN
    assertThrowsExactly(RuntimeException.class, () -> userLoginService.createUserLogin(userLoginDto), expectedErrorMessage);


    //THEN
    verify(userLoginRepo, never()).save(any());
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

  @Test()
  void GIVEN_usernameAndUserDoesntExists_WHEN_loadUserByUsername_THEN_ExceptionThrown() {
    //GIVEN

    String expectedErrorMessage = String.format(ExternalErrorMessages.USERNAME_DOESNT_EXISTS, MOCK_USERNAME);

    //WHEN + THEN
    assertThrowsExactly(RuntimeException.class, () ->  userLoginService.loadUserByUsername(MOCK_USERNAME), expectedErrorMessage);

  }

  private UserLogin createUserLogin() {
    UserLogin userLogin = new UserLogin();
    userLogin.setUsername(MOCK_USERNAME);
    return userLogin;
  }

  private UserLoginDto createUserLoginDto(String mockedPassword) {
    UserLoginDto userLogin = new UserLoginDto();
    userLogin.setUsername(MOCK_USERNAME);
    userLogin.setEnabled(true);
    userLogin.setPassword(mockedPassword);
    return userLogin;
  }

}
