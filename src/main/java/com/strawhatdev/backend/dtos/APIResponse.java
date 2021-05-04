package com.strawhatdev.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class APIResponse {
    private boolean isSuccessful;
    private String message;
    private LocalDate timeStamp;

    public APIResponse(boolean isSuccessful, String message) {
        this.isSuccessful = isSuccessful;
        this.message = message;
        this.timeStamp = LocalDate.now();
    }
}
