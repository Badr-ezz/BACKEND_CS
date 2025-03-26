package com.example.springMongodb.service;

import com.example.springMongodb.model.Prompt;
import com.example.springMongodb.model.Users;

import java.util.List;

public interface PromptService {
    public long countPrompts();
    public List<Prompt> getAllPrompts();
    public Prompt getPromptById(String id);
    public Prompt addPrompt(Prompt prompt);
    public Prompt updatePrompt(Prompt prompt);
    public void deletePrompt(String id);
    public List<Prompt> findByPrompt(String prompt);
    public List<Prompt> findByTag(String tag);
    public List<Prompt> findByTagAndPrompt(String tag, String prompt);
    public List<Prompt> findByCreator(Users creator);
}
