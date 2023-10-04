package com.emre.config;

import com.emre.exception.ErrorType;
import com.emre.exception.UserException;
import com.emre.utility.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenManager jwtTokenManager;
    @Autowired
    private JwtUserDetails jwtUserDetails;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        //bu metot içinde tüm isteklerin yönlendirildiği yerdir. Burada Authorization olup
        //olmadığı kontrol edilir eğer varsa token içindeki bilgi alınp kullanıcı varlığı oluşturulur
        final String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer")){
            final String token = authHeader.substring(7);
            Optional<Long> authId = jwtTokenManager.getIdFromToken(token);
            if (authId.isPresent()){
                UserDetails userDetails = jwtUserDetails.loadUserByAuthId(authId.get());
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }else {
                throw new UserException(ErrorType.ERROR_INVALID_TOKEN);
            }
        }
        filterChain.doFilter(request, response);
    }
}
