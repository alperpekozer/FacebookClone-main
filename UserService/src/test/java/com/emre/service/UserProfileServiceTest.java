package com.emre.service;

import com.emre.dto.request.GetMyProfileRequestDto;
import com.emre.dto.request.UserProfileSaveRequestDto;
import com.emre.dto.response.GetMyProfileResponseDto;
import com.emre.exception.ErrorType;
import com.emre.repository.entity.UserProfile;
import com.emre.utility.JwtTokenManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class UserProfileServiceTest {

    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private JwtTokenManager jwtTokenManager;

    @Test
    void test_Deneme(){
        userProfileService.findAll().forEach(System.out::println);
    }

    @Test
    void saveTest(){
        userProfileService.save(UserProfileSaveRequestDto.builder()
                        .authid(4L)
                        .email("bahar@gmail.com")
                        .username("bahartekin")
                .build());
        Optional<UserProfile> user = userProfileService.findAll().stream()
                .filter(x -> x.getUsername().equals("bahartekin")).findFirst();

        Assertions.assertTrue(user.isPresent());
    }
    @ParameterizedTest
    @CsvFileSource(resources = "/userProfile.csv")
    void saveCSVTest(Long authid,String email,String username){
        UserProfile userProfile= UserProfile.builder()
                .email(email)
                .username(username)
                .authid(authid)
                .build();
        UserProfile userProfileSave=userProfileService.save(userProfile);
        Assertions.assertTrue(userProfileSave.getId() != null);
    }

    @Test
    void findByIdTest(){
        Optional<UserProfile> user=userProfileService.findByAuthId(1L);
        if (user.isPresent()) {
            System.out.println(user.toString());
        }
        Assertions.assertTrue(user.isPresent());
    }

    @Test
    void getMyProfileInvalidTokenTest(){
        Exception exception= Assertions.assertThrows(
                Exception.class, ()->
        userProfileService.getMyProfile(GetMyProfileRequestDto.builder()
                .token("45346666653453")
                .build())
        );
        Assertions.assertEquals(ErrorType.ERROR_INVALID_TOKEN.getMessage(),exception.getMessage());
    }
    @Test
    void getMyProfileInvalidUserTest(){
        Optional<String> token = jwtTokenManager.createToken(2000L);
        Exception exception= Assertions.assertThrows(
                Exception.class, ()->
                        userProfileService.getMyProfile(GetMyProfileRequestDto.builder()
                                .token(token.get())
                                .build())
        );
        Assertions.assertEquals(ErrorType.USER_NOT_FOUND.getMessage(),exception.getMessage());
    }
    @Test
    void getMyProfileTest(){
        Optional<String> token=jwtTokenManager.createToken(9L);
        GetMyProfileResponseDto responseDto=userProfileService.getMyProfile(GetMyProfileRequestDto.builder()
                        .token(token.get())
                .build());
        Assertions.assertTrue(responseDto.getUsername() != null);
    }


}
