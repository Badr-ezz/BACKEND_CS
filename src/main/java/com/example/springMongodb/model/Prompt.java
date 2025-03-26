package com.example.springMongodb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "prompts")
public class Prompt {
    @Id
    private String id;

    @DBRef
    private Users creator;

    @NotEmpty(message = "Prompt is required")
    private String prompt;

    @NotEmpty(message = "Tag is required")
    private String tag;


    public Prompt(Users creator, String prompt, String tag) {
        this.creator = creator;
        this.prompt = prompt;
        this.tag = tag;
    }


}