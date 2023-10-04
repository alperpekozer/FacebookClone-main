package com.emre.service;

import com.emre.dto.request.GetMyProfileRequestDto;
import com.emre.dto.response.GetMyProfileResponseDto;
import com.emre.repository.IUserProfileRepository;
import com.emre.repository.entity.UserProfile;
import com.emre.utility.JwtTokenManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserProfileUnitTest {

    @InjectMocks
    UserProfileService userProfileService;

    @Mock
    IUserProfileRepository userProfileRepository;

    @Mock
    JwtTokenManager jwtTokenManager;

    @Test
    void getMyProfileTest(){
        when(jwtTokenManager.getIdFromToken(any())).thenReturn(Optional.of(9L));
        when(userProfileRepository.findOptionalByAuthid(any())).thenReturn(Optional.of(
                UserProfile.builder()
                        .phone("0555 555 55 55")
                        .avatar("")
                        .name("Emre")
                        .surname("Sahbudak")
                        .username("emre")
                        .build()
        ));
        GetMyProfileResponseDto responseDtodto=userProfileService.getMyProfile(GetMyProfileRequestDto.builder()
                        .token("4534534534534")
                .build());
        Assertions.assertTrue(responseDtodto != null);

    }
}
