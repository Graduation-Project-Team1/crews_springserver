package com.team1.finalproject.memberdata.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.team1.finalproject.config.UserDetailsImpl;
import com.team1.finalproject.config.jwt.JwtTokenUtils;
import com.team1.finalproject.memberdata.dto.LogInResponse;
import com.team1.finalproject.memberdata.dto.SignUpRequest;
import com.team1.finalproject.memberdata.entity.Member;
import com.team1.finalproject.memberdata.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {

    private final JwtTokenUtils jwtTokenUtils;

    private final MemberRepository memberRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;
    @Value("${token-uri}")
    private String tokenUri;
    @Value("${resource-uri}")
    private String resourceUri;
    public LogInResponse googleLogin(String code, String registrationId) {

        String accessToken = getGoogleAccessToken(code, registrationId);
        SignUpRequest dto = getGoogleUserResources(accessToken, registrationId);

        String id = dto.getGoogleId();
        String email = dto.getEmail();

        if(memberRepository.existsByGoogleId(id)){
            log.warn("Already signed up with google.");
            Member member = memberRepository.findByGoogleId(id).orElseThrow();
            if (member.getPreferences() == null) {
                log.info("Google member " + member.getId() + " does not have preferences.");
                return new LogInResponse(false, "preference");
            }
            else {
                log.info("Google member " + member.getId() + " has preferences.");
                String jwtToken = jwtTokenUtils.generateJwtToken(new UserDetailsImpl(member));
                return new LogInResponse(true, "complete", jwtToken);
            }
        } else if (memberRepository.existsByEmail(email)) {
            log.warn("Email is already used.");
            return new LogInResponse(false, "email");
        } else {
            Member member = new Member(dto);
            memberRepository.save(member);
            return new LogInResponse(false, "preference");
        }
    }

    private String getGoogleAccessToken(String authorizationCode, String registrationId) {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", authorizationCode);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity entity = new HttpEntity(params, headers);

        ResponseEntity<JsonNode> responseNode = restTemplate.exchange(tokenUri, HttpMethod.POST, entity, JsonNode.class);
        JsonNode accessTokenNode = responseNode.getBody();
        assert accessTokenNode != null;
        return accessTokenNode.get("access_token").asText();
    }

    private SignUpRequest getGoogleUserResources(String accessToken, String registrationId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity entity = new HttpEntity(headers);
        JsonNode jsonNode = restTemplate.exchange(resourceUri, HttpMethod.GET, entity, JsonNode.class).getBody();

        String id = jsonNode.get("id").asText();
        String email = jsonNode.get("email").asText();
        String nickname = jsonNode.get("name").asText();

        return new SignUpRequest(email, nickname, id, 1L);
    }
}