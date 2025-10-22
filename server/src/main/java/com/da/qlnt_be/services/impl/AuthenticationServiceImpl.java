package com.da.qlnt_be.services.impl;



import com.da.qlnt_be.dto.request.AuthenticationRequest;
import com.da.qlnt_be.dto.request.IntrospectRequest;
import com.da.qlnt_be.dto.request.LogoutRequest;
import com.da.qlnt_be.dto.request.RefreshRequest;
import com.da.qlnt_be.dto.response.AuthenticationResponse;
import com.da.qlnt_be.dto.response.IntrospectResponse;
import com.da.qlnt_be.exception.AppException;
import com.da.qlnt_be.exception.ErrorCode;
import com.da.qlnt_be.model.InvalidatedToken;
import com.da.qlnt_be.model.Role;
import com.da.qlnt_be.model.User;
import com.da.qlnt_be.model.UserRole;
import com.da.qlnt_be.repository.InvalidatedTokenRepository;
import com.da.qlnt_be.repository.UserRepository;
import com.da.qlnt_be.services.AuthenticationService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServiceImpl implements AuthenticationService {

    UserRepository userRepository;
    InvalidatedTokenRepository invalidatedTokenRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;
    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    /**
     * kiểm tra token
     * @param request
     * @return
     * @throws JOSEException
     * @throws ParseException
     */
    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        boolean isValid = true;
        try{
            verifyToken(token, false);
        } catch (AppException e) {
            isValid = false;
        }
        return IntrospectResponse.builder()
                .val(isValid)
                .build();
    }


    /**
     * lấy token
     * @param request
     * @return
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (!user.getIsActive()) {
            throw new AppException(ErrorCode.USER_NOT_ACTIVE);
        }

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if(!authenticated){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        var token = generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    /**
     * đăng xuất
     * @param request
     * @throws ParseException
     * @throws JOSEException
     */
    @Override
    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        try{
            var signedToken = verifyToken(request.getToken(), true);

            String jwtId = signedToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signedToken.getJWTClaimsSet().getExpirationTime();
            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                    .id(jwtId)
                    .expiryTime(expiryTime)
                    .build();
            invalidatedTokenRepository.save(invalidatedToken);
        } catch (AppException e) {
            log.error("Token is invalid or expired", e);
        }
    }

    public AuthenticationResponse refreshToken(RefreshRequest request)
            throws ParseException, JOSEException {

        // Kiểm tra hiệu lực của token hiện tại
        var signedToken = verifyToken(request.getToken(), true);
        String jwtId = signedToken.getJWTClaimsSet().getJWTID();
        Date exptiry = signedToken.getJWTClaimsSet().getExpirationTime();
        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jwtId)
                .expiryTime(exptiry)
                .build();
        invalidatedTokenRepository.save(invalidatedToken);

        String username = signedToken.getJWTClaimsSet().getSubject();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        String newToken = generateToken(user);
        return AuthenticationResponse.builder()
                .token(newToken)
                .authenticated(true)
                .build();

    }

    private String generateToken(User user){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("da-qlnt")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))

                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();

        } catch (JOSEException e) {
            log.error("cannot create Token", e);
            throw new RuntimeException(e);
        }

    }

    private List<String> buildRoles(User user) {
        if (user.getUserRoles() == null) return List.of();
        return user.getUserRoles().stream()
                .map(ur -> ur.getRole().getName())
                .collect(Collectors.toList());
    }

    private Map<String, List<String>> buildScope(User user) {
        if (user.getUserRoles() == null || user.getUserRoles().isEmpty()) {
            return Map.of("roles", List.of(), "permissions", List.of());
        }

        Set<String> roles = new HashSet<>();
        Set<String> permissions = new HashSet<>();

        user.getUserRoles().forEach(userRole -> {
            Role role = userRole.getRole();
            if (role != null) {
                roles.add("ROLE_" + role.getName());
                if (role.getRolePermissions() != null) {
                    role.getRolePermissions().forEach(p -> permissions.add(p.getPermission().getCode()));
                }
            }
        });

        return Map.of(
                "roles", new ArrayList<>(roles),
                "permissions", new ArrayList<>(permissions)
        );
    }
    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        var verified = signedJWT.verify(verifier);
        Date expiryTime = (isRefresh)
                ? new Date(signedJWT.getJWTClaimsSet().getIssueTime()
                    .toInstant().plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS).toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();
        if(!(verified && expiryTime.after(new Date()))){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        if(invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        return signedJWT;
    }
}

