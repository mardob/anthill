package com.net.anthill.unitTests.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import com.net.anthill.dto.UserLoginDto;
import com.net.anthill.dto.UserMetadataDto;
import com.net.anthill.model.UserLogin;
import com.net.anthill.model.UserMetadata;
import com.net.anthill.repository.UserMetadataRepo;
import com.net.anthill.service.UserMetadataService;
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
public class UserMetadataServiceUnitTest {

  @Mock
  private UserMetadataRepo userMetadataRepo;

  @InjectMocks
  private UserMetadataService userMetadataService;


  @BeforeEach
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }


  @Test
  void GIVEN_userNumberAndUserMetadataExists_WHEN_getUserMetadataByUsername_THEN_UserMetadataDtoReturned() {
    //GIVEN
    UserMetadata userMetadata = createUserMetadata();
    Mockito.when(userMetadataRepo.findUserMetadataByUsername(userMetadata.getUsername())).thenReturn(userMetadata);

    //WHEN
    UserMetadataDto result = userMetadataService.getUserMetadataByUsername(userMetadata.getUsername());

    //THEN
    assertThat(result).isNotNull();
    assertThat(result.getUsername()).isEqualTo(userMetadata.getUsername());
  }


  @Test
  void GIVEN_usernameAndUserMetadataDoesntExist_WHEN_createMetadata_THEN_UserMetadataCreated() {
    //GIVEN
    final String testUsername = "testUsername";
    ArgumentCaptor<UserMetadata> saveMethodCapture = ArgumentCaptor.forClass(UserMetadata.class);
    Mockito.when(userMetadataRepo.save(any(UserMetadata.class))).then(AdditionalAnswers.returnsFirstArg());

    //WHEN
    userMetadataService.createMetadata(testUsername);

    //THEN
    verify(userMetadataRepo).save(saveMethodCapture.capture());
    UserMetadata sendUserLogin =  saveMethodCapture.getValue();
    assertThat(sendUserLogin).isNotNull();
    assertThat(sendUserLogin.getUsername()).isEqualTo(testUsername);
  }



  private UserMetadata createUserMetadata() {
    UserMetadata userMetadata = new UserMetadata();
    userMetadata.setUsername("Test username");
    return userMetadata;
  }

  private UserMetadataDto createUserLoginDto() {
    UserMetadataDto userMetadataDto = new UserMetadataDto();
    userMetadataDto.setUsername("Test username");
    return userMetadataDto;
  }

}
