package com.da.qlnt_be.services;

import com.da.qlnt_be.dto.request.AuthenticationRequest;
import com.da.qlnt_be.dto.request.IntrospectRequest;
import com.da.qlnt_be.dto.request.LogoutRequest;
import com.da.qlnt_be.dto.request.RefreshRequest;
import com.da.qlnt_be.dto.response.AuthenticationResponse;
import com.da.qlnt_be.dto.response.IntrospectResponse;
import com.da.qlnt_be.model.User;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
    void logout(LogoutRequest request) throws ParseException, JOSEException;
    AuthenticationResponse refreshToken(RefreshRequest request)
            throws ParseException, JOSEException;
}
