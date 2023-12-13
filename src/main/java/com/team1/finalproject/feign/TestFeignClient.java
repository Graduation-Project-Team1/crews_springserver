package com.team1.finalproject.feign;

import com.team1.finalproject.feign.wrapper.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="testFeignClient", url = "${feign.test.url}", configuration = TestFeignClientConfiguration.class)
public interface TestFeignClient {

    @GetMapping(value = "/news/{teamId}", produces = "application/json", consumes = "application/json")
    NewsResponseWrapper getNews(@PathVariable("teamId") Long teamId);

    @GetMapping(value = "/news/positive/{teamId}", produces = "application/json", consumes = "application/json")
    NewsResponseWrapper getPositiveNews(@PathVariable("teamId") Long teamId);

    @GetMapping(value = "/news/negative/{teamId}", produces = "application/json", consumes = "application/json")
    NewsResponseWrapper getNegativeNews(@PathVariable("teamId") Long teamId);

    @GetMapping(value = "/sns/{teamId}", produces = "application/json", consumes = "application/json")
    SnsResponseWrapper getSNS(@PathVariable("teamId") Long teamId);

    @GetMapping(value = "/community/{teamId}", produces = "application/json", consumes = "application/json")
    CommunityResponseWrapper getCommunity(@PathVariable("teamId") Long teamId);

    @GetMapping(value = "/community/positive/{teamId}", produces = "application/json", consumes = "application/json")
    CommunityResponseWrapper getCommunityPositive(@PathVariable("teamId") Long teamId);

    @GetMapping(value = "/community/negative/{teamId}", produces = "application/json", consumes = "application/json")
    CommunityResponseWrapper getCommunityNegative(@PathVariable("teamId") Long teamId);

    @GetMapping(value = "/keywords/{teamId}", produces = "application/json", consumes = "application/json")
    KeywordResponseWrapper getKeyword(@PathVariable("teamId") Long teamId);

    @GetMapping(value = "/opinion/{teamId}", produces = "application/json", consumes = "application/json")
    OpinionResponseWrapper getOpinion(@PathVariable("teamId") Long teamId);
}
