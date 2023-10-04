package com.emre.dto.request;

import com.emre.repository.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileUpdateRequestDto {
    @NotBlank
    @Size(min = 3)
    String token;
    String name;
    String surname;
    String phone;
    String address;
    String city;
    String avatar;
    Gender gender;

}
