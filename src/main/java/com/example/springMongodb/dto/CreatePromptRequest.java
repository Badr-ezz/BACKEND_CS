package com.example.springMongodb.dto;

import jakarta.validation.constraints.NotEmpty;

public record CreatePromptRequest(
        @NotEmpty String userId,
        @NotEmpty String prompt,
        @NotEmpty String tag
) {}