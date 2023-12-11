package com.team1.finalproject.memberdata.dto;

import com.team1.finalproject.memberdata.entity.Member;
import com.team1.finalproject.sportsdata.entity.Player;
import com.team1.finalproject.sportsdata.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDataResponse {

    private String state;

    private String msg;

    private String platform;

    private String email;

    private String nickname;

    private Long teamId;

    private String teamName;

    private Long playerId;

    private String playerName;

    @Builder
    public MemberDataResponse(String str) {
        this.state = str;
        this.msg = "Error occurred: Preference is not set.";
    }

    @Builder
    public MemberDataResponse(String str, Member member) {
        Team team = member.getPreferences().getTeam();
        Player player = member.getPreferences().getPlayer();
        this.state = str;
        this.msg = "successful";
        if(member.getGoogleId()!=null)
            this.platform = "google";
        else if (member.getKakaoId()!=null)
            this.platform = "kakao";
        else
            this.platform = "crews";
        this.email = member.getEmail();
        this.nickname = member.getNickName();
        this.teamId = team.getId();
        this.teamName = team.getName();
        this.playerId = player.getId();
        this.playerName = player.getName();
    }
}

