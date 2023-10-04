package com.emre.repository;

import com.emre.repository.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAuthRepository extends JpaRepository<Auth,Long> {

    //login olma için username şifre kontrolu yapma sorgusu
    Optional<Auth> findOptionalByUsernameAndPassword(String username, String password);

    //kullanıcı veritabanıda var mı sorgusu
    Boolean existsByUsername(String username);

    //aynı yukardaki sorgunun query ile yazdık. eger akyıt varsa 1 olacak
    //turly deger olucak boolean dönüşe cevap verebilmiş olcaz
    @Query("select count(a)>0 from Auth a where a.username = ?1")
    Boolean existsByUsernameQuery(String username);


}
