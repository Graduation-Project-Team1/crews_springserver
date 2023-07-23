package com.team1.finalproject.memberdata.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.token.Token;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewUserDataRequestDto {
    private Token token;
}
