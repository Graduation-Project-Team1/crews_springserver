package com.team1.finalproject.memberdata.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreferencesResponse {

    private String success;

    private String message;

    public PreferencesResponse(String success) {
        this.success = success;
        if (success.equals("Success"))
            this.message = "Preferences successfully updated.";
        else
            this.message = "Invalid request. Try again.";
    }
}
