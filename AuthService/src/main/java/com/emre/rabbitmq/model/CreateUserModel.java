package com.emre.rabbitmq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserModel implements Serializable {
    /*bu model üzerinden RabbitMQ ya mesaj ileteceğiz ve bu mesajı kuyruga işlemesini
    * isteyeceğiz. burada göndeirecek olan sınıf bilgisi rabbitMQ ya base64 olarak
    * iletilecektir bu nedenle sınıfın serileştirmesi gerekmektedir. buna riayet etmek için
    * sınıfın Serializable interfacesini implement etmesi gerekir
    * ---
    * yine burada oluşturulan ınıf için, paket ismi ve sınıf içinde kullanılan tüm
    * parametlerin aynı olması gerekir. aksi takdirde nesneniz iletilen tarafta okunmayacaktır.
    * */
    Long authid;
    String username;
    String email;
}
