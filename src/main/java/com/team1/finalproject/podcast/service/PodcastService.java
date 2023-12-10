package com.team1.finalproject.podcast.service;

import com.team1.finalproject.podcast.dto.PodcastInfoResponse;
import com.team1.finalproject.podcast.entity.Podcast;
import com.team1.finalproject.podcast.repository.PodcastRepository;
import com.team1.finalproject.sportsdata.entity.Team;
import com.team1.finalproject.sportsdata.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PodcastService {

    private final TeamRepository teamRepository;
    private final PodcastRepository podcastRepository;

    public ResponseEntity<ByteArrayResource> getAudio(Podcast podcast) throws IOException {

        String title = podcast.getTitle();
        String text = podcast.getText();
        LocalDateTime madeAt = podcast.getMadeAt();
        String duration = podcast.getDuration();

        // 파일 경로로 음성 파일 호출.
        String filePath = podcast.getPath();
        //String filePath = "src/main/resources/Feat_MINO_Prod_GRAY.mp3";
        File file = new File(filePath);
        Path path = file.toPath();

        // 음성파일 바이트 변환
        byte[] audioData = Files.readAllBytes(path);
        ByteArrayResource resource = new ByteArrayResource(audioData);

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + title + ".mp3");
        headers.add("Podcast-Title", title);
        headers.add("Podcast-Text", text);
        headers.add("Podcast-MadeAt", String.valueOf(madeAt));
        headers.add("Podcast-Duration", duration);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(audioData.length);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(audioData.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    public List<PodcastInfoResponse> getPodcastInfoList(Long teamId) {
        List<PodcastInfoResponse> podcastInfoList = new ArrayList<>();
        Team team = teamRepository.findById(teamId).orElseThrow();
        List<Podcast> allByTeam = podcastRepository.findAllByTeam(team);
        for (Podcast podcast : allByTeam) {
            podcastInfoList.add(new PodcastInfoResponse(podcast));
        }
        return podcastInfoList;
    }

    public ResponseEntity<ByteArrayResource> getPodcastByTeamId(Long teamId) throws IOException {
        Team team = teamRepository.findById(teamId).orElseThrow();
        // 팀 기준 가장 최신의 팟캐스트
        Podcast podcast = podcastRepository.findTopByTeamOrderByMadeAtDesc(team).orElseThrow();

        return getAudio(podcast);
    }

    public ResponseEntity<ByteArrayResource> getPodcastById(Long podcastId) throws IOException {
        Podcast podcast = podcastRepository.findById(podcastId).orElseThrow();

        return getAudio(podcast);
    }
}
