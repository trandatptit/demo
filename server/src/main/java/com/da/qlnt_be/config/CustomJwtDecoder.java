package com.da.qlnt_be.config;

import com.da.qlnt_be.dto.request.IntrospectRequest;
import com.da.qlnt_be.exception.AppException;
import com.da.qlnt_be.exception.ErrorCode;
import com.da.qlnt_be.services.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;

@Component
public class CustomJwtDecoder implements JwtDecoder {

    @Value("${jwt.signerKey}")
    private String signerKey;

    @Autowired
    private AuthenticationService authenticationService;
    private NimbusJwtDecoder nimbusJwtDecoder = null;

    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            // Gọi introspect để kiểm tra token còn hạn
            var response = authenticationService.introspect(
                    IntrospectRequest.builder()
                            .token(token)
                            .build()
            );

            if (!response.isVal()) {
                // Ném lỗi HTTP 401
                throw new BadJwtException("Token is invalid or expired");
            }

            // Lazy init NimbusJwtDecoder
            if (nimbusJwtDecoder == null) {
                SecretKeySpec signerKeySpec = new SecretKeySpec(signerKey.getBytes(), "HmacSHA512");
                nimbusJwtDecoder = NimbusJwtDecoder
                        .withSecretKey(signerKeySpec)
                        .macAlgorithm(MacAlgorithm.HS512)
                        .build();
            }

            return nimbusJwtDecoder.decode(token);

        } catch (BadJwtException e) {
            throw e;
        } catch (JOSEException | ParseException e) {
            throw new BadJwtException("Invalid JWT format: " + e.getMessage());
        } catch (Exception e) {
            throw new JwtException("Unexpected error during token validation");
        }
    }
}
