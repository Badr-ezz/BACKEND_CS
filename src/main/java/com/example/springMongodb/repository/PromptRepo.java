package com.example.springMongodb.repository;

import com.example.springMongodb.model.Prompt;
import com.example.springMongodb.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PromptRepo extends MongoRepository<Prompt, String> {
    public List<Prompt> findByPrompt(String prompt);
    public List<Prompt> findByTag(String tag);
    public List<Prompt> findByTagAndPrompt(String tag, String prompt);
    public List<Prompt> findByCreator(Users creator);
}
