package com.team1.finalproject.memberdata.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDeletionResponse {
    private String success;

    private String message;

    public MemberDeletionResponse(String success) {
        this.success = success;
        if (success.equals("Success"))
            this.message = "User successfully deleted from service. Thank you.";
        else
            this.message = "Invalid request. Try again.";
    }
}
