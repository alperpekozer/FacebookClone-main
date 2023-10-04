package com.emre.controller;

import com.emre.repository.entity.UserRoles;
import com.emre.service.UserRolesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/userroles")
@RequiredArgsConstructor
public class UserRolesController {

    private final UserRolesService userRolesService;

    @PostMapping("/save")
    public ResponseEntity<Void> save(String userprofileid,String role){
        UserRoles userRoles= UserRoles.builder()
                .role(role)
                .userprofileid(userprofileid)
                .build();
        userRolesService.save(userRoles);
        return ResponseEntity.ok().build();
    }
}
