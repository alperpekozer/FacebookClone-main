package com.emre.config;

import com.emre.exception.ErrorType;
import com.emre.exception.UserException;
import com.emre.repository.IUserProfileRepository;
import com.emre.repository.IUserRoleRepository;
import com.emre.repository.entity.UserProfile;
import com.emre.repository.entity.UserRoles;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtUserDetails implements UserDetailsService {

    private final IUserProfileRepository userProfileRepository;
    private final IUserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public UserDetails loadUserByAuthId(Long authId){
        Optional<UserProfile> userProfile= userProfileRepository.findOptionalByAuthid(authId);

        if (userProfile.isEmpty())
            throw new UserException(ErrorType.ERROR_INVALID_TOKEN);
        List<GrantedAuthority> authorities= new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        //böyle yukardaki gibi elle değil veirtabanınan cekmek laızm bunları.
        List<UserRoles> rolesList=userRoleRepository.findAllByUserprofileid(userProfile.get().getId());
        rolesList.forEach(roles->{
            authorities.add(new SimpleGrantedAuthority(roles.getRole()));
        });
        UserDetails user = User.builder()
                .accountExpired(false)
                .accountLocked(false)
                .username(userProfile.get().getUsername())
                .password("")
                .authorities(authorities)
                .build();
        return user;
    }
}
