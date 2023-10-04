package com.emre.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum ErrorType {

    ERROR_PASSWORD(2004,"Girmiş olduğunuz şifreler uyuşmamaktadır.",HttpStatus.NOT_FOUND),
    USER_NOT_FOUND(2006,"Kullanıcı bulunamamıştır.",HttpStatus.NOT_FOUND),

    ERROR_USERNAME(2005,"Bu kullanıcı adı daha önce kayıt edilmiştir. " +
            "Lütfen farklı bir kullanıcı adı giriniz.",HttpStatus.BAD_REQUEST),
    //ERROR_NOT_FOUND_USERNAME(2006,"Böyle bir kullanıcı bulunmamaktadır.", HttpStatus.INTERNAL_SERVER_ERROR),

    ERROR_INVALID_TOKEN(3000,"Geçersiz Token",HttpStatus.UNAUTHORIZED),

    BAD_REQUEST(4000,"Geçersiz istek yada parametre",HttpStatus.BAD_REQUEST),

    ERROR_ACCESS_DENIED(3001,"Yetkisiz erişim.Lütfen geçerli bir kullanıcı ile tekrar deneyin.",HttpStatus.BAD_REQUEST),

    ERROR(9000,"Beklenmeyen bir hata oluştu. Lütfen daha sonra deneyiniz",
            HttpStatus.INTERNAL_SERVER_ERROR);

    int code;

    String message;

    HttpStatus httpStatus;
}
