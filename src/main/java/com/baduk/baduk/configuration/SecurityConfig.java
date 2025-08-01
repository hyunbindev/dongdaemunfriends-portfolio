package com.baduk.baduk.configuration;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.NullSecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.baduk.baduk.configuration.filter.JwtAuthenticationFilter;
import com.baduk.baduk.configuration.oauth.CustomOAuth2FailureHandler;
import com.baduk.baduk.configuration.oauth.CustomOAuth2UserService;
import com.baduk.baduk.configuration.oauth.OAuth2SuccessHandler;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
	private final CustomOAuth2UserService customOAuth2userService;
	private final CustomOAuth2FailureHandler customOAuth2FailureHandler;
	private final OAuth2SuccessHandler oAuth2SuccessHandler;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	@Value("${spring.auth.redirect}")
	private String baseURL;
	
	@PostConstruct
	private void init() {
		int slashIndex = baseURL.indexOf("/", 8);
		if(slashIndex != -1) {
			this.baseURL = this.baseURL.substring(0, slashIndex);
			return;
		}
	}
	
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            /*.authorizeHttpRequests(auth -> auth
            		.anyRequest().permitAll() // 모든 요청 허용
            )*/
            .csrf(CsrfConfigurer::disable)
            .httpBasic(HttpBasicConfigurer::disable)
            .formLogin(FormLoginConfigurer::disable)
            .sessionManagement((session) -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 비활성화
             )
            .oauth2Login(customConfigurer -> customConfigurer
            		.loginPage("/login")
            		.successHandler(oAuth2SuccessHandler)
            		.failureHandler(customOAuth2FailureHandler)
            		.authorizationEndpoint(authorization -> authorization.baseUri("/api/oauth2/authorization"))
            		.redirectionEndpoint(redir -> redir.baseUri("/api/login/oauth2/code/**"))
            		.userInfoEndpoint(endpointConfig ->endpointConfig.userService(customOAuth2userService)))
        	.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/login/**").permitAll() // 허용
                .anyRequest().authenticated() // 나머지는 인증 필요
            )
        	//이건 h2 콘솔 땜문
        	.headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin())
            )
        	.securityContext(security -> security
        			.securityContextRepository(new NullSecurityContextRepository()))
        	.cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))
        	.addFilterAfter(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    CorsConfigurationSource corsConfigurationSource() {
    	return request ->{
    		CorsConfiguration config = new CorsConfiguration();
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT", "PATCH"));
            config.setAllowedOriginPatterns(Collections.singletonList(baseURL));
            config.setAllowCredentials(true);
            config.setExposedHeaders(Collections.singletonList("X-Redirect"));
            return config;
    	};
    }
}
