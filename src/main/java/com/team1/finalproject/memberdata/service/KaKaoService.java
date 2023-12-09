package com.team1.finalproject.memberdata.service;

import com.team1.finalproject.config.UserDetailsImpl;
import com.team1.finalproject.config.jwt.JwtTokenUtils;
import com.team1.finalproject.memberdata.dto.SignUpRequest;
import com.team1.finalproject.memberdata.entity.Member;
import com.team1.finalproject.memberdata.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class KaKaoService {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final JwtTokenUtils jwtTokenUtils;

    public String getKakaoToken(String code) throws IOException {
        // 인가코드로 토큰받기
        String host = "https://kauth.kakao.com/oauth/token";
        URL url = new URL(host);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        String token = "";
        try {
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true); // 데이터 기록 알려주기

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
            String sb = "grant_type=authorization_code" +
                    "&client_id=b6d6b8757acb893872695778f57324b7" +
                    "&redirect_uri=http://localhost:8080/kakao/getCI" +
                    "&code=" + code;

            bw.write(sb);
            bw.flush();

            /*int responseCode = urlConnection.getResponseCode();
            System.out.println("responseCode = " + responseCode);*/

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            StringBuilder result = new StringBuilder();
            while ((line = br.readLine()) != null) {
                result.append(line);
            }

            // json parsing
            JSONParser parser = new JSONParser();
            JSONObject elem = (JSONObject) parser.parse(result.toString());

            String access_token = elem.get("access_token").toString();
            String refresh_token = elem.get("refresh_token").toString();

            token = access_token;

            br.close();
            bw.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return token;
    }

    public SignUpRequest getKakaoUserInfo(String access_token) {
        String host = "https://kapi.kakao.com/v2/user/me";
        SignUpRequest signUpRequest = null;
        try {
            URL url = new URL(host);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Authorization", "Bearer " + access_token);
            urlConnection.setRequestMethod("GET");

            /*int responseCode = urlConnection.getResponseCode();
            System.out.println("responseCode = " + responseCode);*/

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            StringBuilder res = new StringBuilder();
            while ((line = br.readLine()) != null) {
                res.append(line);
            }

            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(res.toString());
            JSONObject kakao_account = (JSONObject) obj.get("kakao_account");
            JSONObject properties = (JSONObject) obj.get("properties");

            String kakaoId = obj.get("id").toString();
            String nickname = properties.get("nickname").toString();
            String email = kakao_account.get("email").toString();
            /*String age_range = kakao_account.get("age_range").toString();*/

            signUpRequest = new SignUpRequest(email, nickname, kakaoId, 0L);

            br.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return signUpRequest;
    }

    public String signUpAsKakao(SignUpRequest dto) throws IOException {
        String kakaoId = dto.getKakaoId();
        if (kakaoId==null) {
            return "Invalid request: Kakao id not Included";
        }
        String email = dto.getEmail();
        String nickname = dto.getNickName();
        if (memberRepository.existsByKakaoId(kakaoId)) {
            log.warn("Already signed up with kakao.");
            Member member = memberRepository.findByKakaoId(kakaoId).orElseThrow();
            if (member.getPreferences() == null) {
                log.info("Kakao member " + member.getId() + " does not have preferences.");
                return "Set preference";
            }
            else {
                log.info("Kakao member " + member.getId() + " has preferences.");
                return "Home";
            }

        } else if (memberRepository.existsByEmail(email)) {
            log.warn("Email is already used.");
            return "Duplicate email";
        } else {
            Member member = new Member(nickname, email, kakaoId, dto.getSocialCode());
            memberRepository.save(member);
            return jwtTokenUtils.generateJwtToken(new UserDetailsImpl(member));
        }
    }

    public String getKakaoAgreementInfo(String access_token) {
        StringBuilder result = new StringBuilder();
        String host = "https://kapi.kakao.com/v2/user/scopes";
        try {
            URL url = new URL(host);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Authorization", "Bearer " + access_token);

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line = "";
            while ((line = br.readLine()) != null) {
                result.append(line);
            }

            int responseCode = urlConnection.getResponseCode();
            System.out.println("responseCode = " + responseCode);

            // result is json format
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
