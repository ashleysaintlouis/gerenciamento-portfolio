//package io.github.ashleysaintlouis.gerenciamentoportfolio.security;
//
////import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
////import io.swagger.v3.oas.annotations.security.SecurityScheme;
//import io.github.ashleysaintlouis.gerenciamentoportfolio.security.utils.TokenService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.ProviderManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//
//import java.util.List;
//
//@EnableMethodSecurity
//@Configuration
//@EnableWebSecurity
////@SecurityScheme(
////        name = SecurityConfiguration.SEGURITY,
////        type = SecuritySchemeType.HTTP,
////        bearerFormat = "JWT",
////        scheme = "bearer"
////)
//public class SecurityConfiguration {
//
//    public static final String SEGURITY = "bearerAuth";
//
//    private JWTFilter jwtFilter;
//    private CustomUserDetailsService  userDetailsService;
//
//    @Autowired
//    private TokenService tokenService;
//
//    public SecurityConfiguration(CustomUserDetailsService userDetailsService, JWTFilter jwtFilter) {
//        this.userDetailsService = userDetailsService;
//        this.jwtFilter = jwtFilter;
//    }
//
//    public static final String[] AUTH_WHITE_LIST = {
//            "/v3/api-docs/**",
//            "/api-docs/**",
//            "/swagger-ui.html",
//            "/swagger-ui/**",
//            "/v2/api-docs/**",
//            "/swagger-resources/**",
//            "/usuario/register",
//            "/membro/",
//            "/usuario/login",
//            "/error", "/error/**",
//            "/actuator/health"
//    };
//
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService);
//        authProvider.setPasswordEncoder(passwordEncoder);
//
//        return new ProviderManager(authProvider);
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(AUTH_WHITE_LIST).permitAll()
//                        .anyRequest().authenticated()
//                )
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .exceptionHandling(exceptions -> exceptions
//                        .authenticationEntryPoint((request, response, authException) -> {
//                            response.setContentType("application/json");
//                            response.setStatus(401);
//                            response.getWriter().write("{\"error\":\"Usuário não autenticado ou token inválido\"}");
//                        })
//                        .accessDeniedHandler((request, response, accessDeniedException) -> {
//                            response.setContentType("application/json");
//                            response.setStatus(403);
//                            response.getWriter().write("{\"error\":\"Acesso negado: você não possui permissão\"}");
//                        })
//                )
//                .headers(headers -> headers.frameOptions(frame -> frame.disable()));
//
//        return http.build();
//    }
//}
