package com.da.qlnt_be.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String address;
    private String phoneNumber;
    private LocalDate dob;
    private MultipartFile avatar;
}
