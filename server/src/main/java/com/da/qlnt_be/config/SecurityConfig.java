package com.da.qlnt_be.config;


import com.da.qlnt_be.exception.AppException;
import com.da.qlnt_be.exception.ErrorCode;
import com.da.qlnt_be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Bag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import javax.crypto.spec.SecretKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity // Bật Spring Security cho toàn ứng dụng
@EnableMethodSecurity // Cho phép dùng @PreAuthorize, @Secured trên method

public class SecurityConfig {



    @Value("${jwt.signerKey}")
    private String signerKey;

    @Autowired
    private CorsProperties corsProperties;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private CustomJwtDecoder customJwtDecoder;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final String[] PUBLIC_ENDPOINTS = {"/users", "/users/create",
            "/auth/token", "/auth/login", "/auth/introspect", "/auth/logout", "/auth/refresh"};


    // userDetailsService bean
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> userRepository
                .findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable) // Tắt CSRF để WebSocket hoạt động
                .cors(
                        httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer
                                .configurationSource(corsConfigurationSource())
                )
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("**").permitAll()
                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .anyRequest().authenticated()

                )
                .exceptionHandling(
                        exceptionHandling -> exceptionHandling
                                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.decoder(customJwtDecoder)
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )
                .build();
    }

    //Bean giải mã JWT bằng key bí mật (HS512)
//    @Bean
//    JwtDecoder jwtDecoder(){
//        SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(),"HS512");
//        return NimbusJwtDecoder
//                .withSecretKey(secretKeySpec)
//                .macAlgorithm(MacAlgorithm.HS512)
//                .build();
//    };

    //Bean mã hóa mật khẩu
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }


    //Bean chuyển JWT thành danh sách quyền trong Spring Security
    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            List<GrantedAuthority> authorities = new ArrayList<>();

            // Lấy object "scope"
            Object scopeObj = jwt.getClaims().get("scope");
            if (scopeObj instanceof Map<?, ?> scope) {

                // Đọc roles
                Object rolesClaim = scope.get("roles");
                if (rolesClaim instanceof List<?> roles) {
                    roles.forEach(role ->
                            authorities.add(new SimpleGrantedAuthority(role.toString()))
                    );
                }

                // Đọc permissions
                Object permsClaim = scope.get("permissions");
                if (permsClaim instanceof List<?> permissions) {
                    permissions.forEach(perm ->
                            authorities.add(new SimpleGrantedAuthority(perm.toString()))
                    );
                }
            }

            return authorities;
        });

        return converter;
    }


    //Cấu hình CORS để FE truy cập được API từ domain khác
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList(corsProperties.getAllowedOrigins().split(",")));
        config.setAllowedMethods(List.of(corsProperties.getAllowedMethods().split(",")));
        config.setAllowedHeaders(List.of(corsProperties.getAllowedHeaders().split(",")));
        config.setExposedHeaders(List.of(corsProperties.getExposedHeaders().split(",")));
        config.setAllowCredentials(corsProperties.getAllowCredentials());
        config.setMaxAge(corsProperties.getMaxAge());

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }


    //Cấu hình provider dùng để xác thực username/password
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }


    //AuthenticationManager dùng trong AuthService để login
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {
        return config.getAuthenticationManager();
    }

}


