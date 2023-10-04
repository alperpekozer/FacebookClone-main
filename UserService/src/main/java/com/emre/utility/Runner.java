package com.emre.utility;

import com.emre.manager.IElasticServiceManager;
import com.emre.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class Runner {

    private final UserProfileService userProfileService;
    private final IElasticServiceManager elasticServiceManager;

    //uygulama ayağa kalkarken ilk işlem olarak calışmasını sağlıcak.
    //@PostConstruct
    public void init(){
        userProfileService.findAll().forEach(x->{
            elasticServiceManager.save(x);
        });
    }
}
