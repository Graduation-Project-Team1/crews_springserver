package com.team1.finalproject.memberdata.dto;

import com.team1.finalproject.sportsdata.entity.Player;
import com.team1.finalproject.sportsdata.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.token.Token;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetUserPreferencesRequestDto {
    //private Nation nation;
    private String nickname;

    private Team team;

    private Player player;
}
