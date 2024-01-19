package com.example.wth.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class PreferenceDto {
    @NonNull
    private String campaignId;
    @NonNull
    private Boolean isExternal;
}
