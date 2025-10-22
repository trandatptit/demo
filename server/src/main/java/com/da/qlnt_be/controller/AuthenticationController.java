package com.da.qlnt_be.controller;

import com.da.qlnt_be.dto.request.AuthenticationRequest;
import com.da.qlnt_be.dto.request.IntrospectRequest;
import com.da.qlnt_be.dto.request.LogoutRequest;
import com.da.qlnt_be.dto.request.RefreshRequest;
import com.da.qlnt_be.dto.response.ApiResponse;
import com.da.qlnt_be.dto.response.AuthenticationResponse;
import com.da.qlnt_be.dto.response.IntrospectResponse;
import com.da.qlnt_be.services.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AuthenticationService authenticationService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        var result = authenticationService.authenticate(request);
         return ApiResponse.<AuthenticationResponse>builder()
                 .result(result)
                 .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request)
            throws ParseException, JOSEException
    {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder()
                .result(null)
                .build();
    }

    @PostMapping("/refresh")
    ApiResponse<AuthenticationResponse> refresh(@RequestBody RefreshRequest token) throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(token);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

}
