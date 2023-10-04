package com.emre.service;

import com.emre.repository.IUserRoleRepository;
import com.emre.repository.entity.UserRoles;
import com.emre.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service

public class UserRolesService extends ServiceManager<UserRoles,String> {

    private final IUserRoleRepository userRoleRepository;

    public UserRolesService(IUserRoleRepository userRoleRepository) {
        super(userRoleRepository);
        this.userRoleRepository = userRoleRepository;
    }

}
