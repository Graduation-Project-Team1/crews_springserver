package com.team1.finalproject.memberdata.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.team1.finalproject.config.UserDetailsImpl;
import com.team1.finalproject.config.jwt.JwtTokenUtils;
import com.team1.finalproject.memberdata.dto.SignUpRequest;
import com.team1.finalproject.memberdata.entity.Member;
import com.team1.finalproject.memberdata.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Value("${client-id}")
    private String clientId;
    @Value("${client-secret}")
    private String clientSecret;
    @Value("${redirect-uri}")
    private String redirectUri;
    @Value("${token-uri}")
    private String tokenUri;
    @Value("${resource-uri}")
    private String resourceUri;
    public String googleLogin(String code, String registrationId) {
        String accessToken = getGoogleAccessToken(code, registrationId);
        JsonNode userResourceNode = getGoogleUserResources(accessToken, registrationId);

        String id = userResourceNode.get("id").asText();
        String email = userResourceNode.get("email").asText();
        String nickname = userResourceNode.get("name").asText();

        if(memberRepository.existsByGoogleId(id)){
            log.warn("Already signed up with google.");
            Member member = memberRepository.findByGoogleId(id).orElseThrow();
            if (member.getPreferences() == null) {
                log.info("Google member " + member.getId() + " does not have preferences.");
                return "Set preference";
            }
            else {
                log.info("Google member " + member.getId() + " has preferences.");
                return "Home";
            }
        } else if (memberRepository.existsByEmail(email)) {
            log.warn("Email is already used.");
            return "Duplicate email";
        } else {
            return signUpAsGoogle(new SignUpRequest(email, nickname, id, 1L));
        }
    }

    public String signUpAsGoogle(SignUpRequest dto) {
        Member member = new Member(dto.getEmail(), dto.getNickName(), dto.getGoogleId(), dto.getSocialCode());
        memberRepository.save(member);
        return jwtTokenUtils.generateJwtToken(new UserDetailsImpl(member));
    }

    private String getGoogleAccessToken(String authorizationCode, String registrationId) {

        System.out.println("registrationId = " + registrationId);
        System.out.println("clientId = " + clientId);
        System.out.println("clientSecret = " + clientSecret);
        System.out.println("redirectUri = " + redirectUri);
        System.out.println("tokenUri = " + tokenUri);

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

    private JsonNode getGoogleUserResources(String accessToken, String registrationId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity entity = new HttpEntity(headers);
        return restTemplate.exchange(resourceUri, HttpMethod.GET, entity, JsonNode.class).getBody();
    }
}