//package com.da.qlnt_be.config.socket;
//
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.simp.stomp.StompCommand;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.messaging.support.ChannelInterceptor;
//import org.springframework.messaging.support.MessageHeaderAccessor;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//@Component
//@RequiredArgsConstructor
//public class WebSocketChannelInterceptor implements ChannelInterceptor {
//
//    private static final Logger logger = LoggerFactory.getLogger(WebSocketChannelInterceptor.class);
//    private final JwtDecoder jwtDecoder;
//
//    @Override
//    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//
//        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
//            // Lấy token từ header
//            List<String> authorization = accessor.getNativeHeader("Authorization");
//            logger.info("WebSocket Auth Headers: {}", authorization);
//
//            if (authorization != null && !authorization.isEmpty()) {
//                String bearerToken = authorization.get(0);
//                if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//                    String token = bearerToken.substring(7);
//
//                    try {
//                        // Giải mã JWT token
//                        Jwt jwt = jwtDecoder.decode(token);
//                        String username = jwt.getClaimAsString("sub");
//                        logger.info("WebSocket connection established for user: {}", username);
//
//                        // Tạo authentication với username từ JWT
//                        Authentication authentication = createAuthentication(jwt);
//                        accessor.setUser(authentication);
//
//                        // Lưu vào SecurityContext để sử dụng trong các phương thức
//                        SecurityContextHolder.getContext().setAuthentication(authentication);
//                    } catch (Exception e) {
//                        logger.error("WebSocket JWT authentication error", e);
//                    }
//                }
//            }
//        }
//
//        return message;
//    }
//
//    private Authentication createAuthentication(Jwt jwt) {
//        String username = jwt.getClaimAsString("sub");
//        Map<String, Object> claims = jwt.getClaims();
//
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//
//        return new JwtAuthenticationToken(jwt, authorities, username);
//    }
//}