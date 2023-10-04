package com.emre.service;

import com.emre.dto.request.LoginRequestDto;
import com.emre.dto.request.RegisterRequestDto;
import com.emre.dto.request.UserProfileSaveRequestDto;
import com.emre.exception.AuthException;
import com.emre.exception.ErrorType;
import com.emre.manager.IUserProfileManager;
import com.emre.mapper.IAuthMapper;
import com.emre.rabbitmq.model.CreateUserModel;
import com.emre.rabbitmq.producer.CreateUserProducer;
import com.emre.repository.IAuthRepository;
import com.emre.repository.entity.Auth;
import com.emre.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService extends ServiceManager<Auth,Long> {

    private final IAuthRepository authRepository;

    private final IUserProfileManager userProfileManager;

    private final CreateUserProducer createUserProducer;

    public AuthService(IAuthRepository authRepository,
                       IUserProfileManager userProfileManager,
                       CreateUserProducer createUserProducer) {
        super(authRepository);
        this.authRepository = authRepository;
        this.userProfileManager = userProfileManager;
        this.createUserProducer = createUserProducer;
    }
    public Optional<Auth> doLogin(LoginRequestDto dto){
         return authRepository.findOptionalByUsernameAndPassword(
                 dto.getUsername(),dto.getPassword());
    }

    public void register(RegisterRequestDto dto){
        if (authRepository.existsByUsername(dto.getUsername()))
            throw new AuthException(ErrorType.ERROR_USERNAME);
        Auth auth=save(IAuthMapper.INSTANCE.toAuth(dto));
        UserProfileSaveRequestDto requestDto=UserProfileSaveRequestDto.builder()
                .username(auth.getUsername())
                .email(auth.getEmail())
                .authid(auth.getId())
                .build();

        /*Bu işlemden sonra feignclient bizim için veridğimiz prametreleri
        iletişime geçceğimiz userprofile save metoduna jsonobject olarak göderir
        ve o save metodunu calışmaısnı sağlar.*/

        //userProfileManager.save(requestDto);
        createUserProducer.converAndSendData(CreateUserModel.builder()
                        .authid(auth.getId())
                        .email(auth.getEmail())
                        .username(auth.getUsername())
                .build());

    }


}
