package com.team1.finalproject.memberdata.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordResponse {
    private String success;

    private String message;

    public UpdatePasswordResponse(String success) {
        this.success = success;
        if (success.equals("Success"))
            this.message = "Password successfully updated.";
        else
            this.message = "Invalid request. Try again.";
    }
}
