//package com.da.qlnt_be.config.socket;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.config.ChannelRegistration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.web.socket.config.annotation.*;
//
//@Configuration
//@EnableWebSocketMessageBroker
//@RequiredArgsConstructor
//public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
//
//    private final WebSocketChannelInterceptor webSocketChannelInterceptor;
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry config) {
//        // Gửi về client: /topic/** hoặc /queue/**
//        config.enableSimpleBroker("/topic", "/queue");
//        // Gửi từ client lên server: /app/**
//        config.setApplicationDestinationPrefixes("/app");
//        // Prefix cho destination riêng - không được thêm API_BASE_URL vào đây
//        config.setUserDestinationPrefix("/user");
//    }
//
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        // Đây là endpoint để kết nối WebSocket
//        registry.addEndpoint("/ws")
//                .setAllowedOriginPatterns("*")
//                .withSockJS(); // Hỗ trợ fallback nếu client không hỗ trợ WebSocket gốc
//    }
//
//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        // Đăng ký interceptor xác thực để xử lý token
//        registration.interceptors(webSocketChannelInterceptor);
//    }
//}
