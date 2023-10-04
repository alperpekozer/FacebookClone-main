package com.emre.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "userprofile")
public class UserProfile {

    @Id
    String id;
    String userid;
    Long authid;
    String username;
    String email;
    String name;
    String surname;
    String phone;
    String address;
    String city;
    String avatar;
    Gender gender;










}
