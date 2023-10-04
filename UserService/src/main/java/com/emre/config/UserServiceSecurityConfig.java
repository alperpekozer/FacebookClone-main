package com.emre.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UserServiceSecurityConfig {

    @Bean
    JwtTokenFilter getJwtTokenFilter(){
        return new JwtTokenFilter();
    }

    /*Bu kısımda gelen isteklerin filtrelenmesi ve belli adreslere gelen isteklerin,
    hangilerinin public olacagını hangilerninin kısıtlanacagını belirliyoruz
    1. gelen istek HTTPSecurity
    2. kısıtlanacak adreslerin listesi --> antMatchers
    2.1 "/api/v1/admin/login --> admin giriş sayfasını tanımlamak
    2.2. /api/v1/user/**  --> kullanıcıı isteklerinin tamamını tanımlar iki yıldız."
    3. izin verme işlemi --> permitAll
    4. Tüm istekleri reddetmek ---> anyRequeest.
    5. authenticated ---> gelen istekleri önce oturum acmaya yönlendriir.
     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/v1/userprofile/getpage","/login.html"
                ,"/v3/api-docs/**","/swagger-ui/**","/swagger-ui.html"
                        ,"/api/v1/userroles/save")
                .permitAll()
                .anyRequest().authenticated();
        //http.formLogin();
        //http.formLogin().loginPage("/login.html");
        /*Burada gelen isteğin filtre mekanizmasına ugramadan önce JWT ile kontrol edilmesi ve
        yetkili bir kullanıcı ise kendisine özel bir kimlik kartı tanımlanmasını sağlıyoruz
        1. ilk yapmamız gereken şey filtrleme işlmeinden önce planlanan kodlamanın ne oldugunu
        bildiriyor ve yönlendiriyoruz.
        1.1. ilk kısım filtreleme işlemini yaparak jwt kontrolu sağlayacak olan sınıfı tanımlıyrz
        1.2. Filtreleme işleminde kontrol edilcek sınıf bileşenini veiryoruz
        * */
        http.addFilterBefore(getJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
