package com.example.springMongodb.service;

import com.example.springMongodb.model.Prompt;
import com.example.springMongodb.model.Users;
import com.example.springMongodb.repository.PromptRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromptServiceImpl implements PromptService {

    private final PromptRepo promptRepo ;

    @Override
    public long countPrompts() {
        return promptRepo.count();
    }

    @Override
    public List<Prompt> getAllPrompts() {
        return promptRepo.findAll();
    }

    @Override
    public Prompt getPromptById(String id) {
        return promptRepo.findById(id).get();
    }

    @Override
    public Prompt addPrompt(Prompt prompt) {
        return promptRepo.insert(prompt);
    }

    @Override
    public Prompt updatePrompt(Prompt prompt) {
        return promptRepo.save(prompt);
    }

    @Override
    public void deletePrompt(String id) {
        Prompt prompt = promptRepo.findById(id).get();
        if (prompt != null) {
            promptRepo.delete(prompt);
        }
    }

    @Override
    public List<Prompt> findByPrompt(String prompt) {
        return promptRepo.findByPrompt(prompt);
    }

    @Override
    public List<Prompt> findByTag(String tag) {
        return promptRepo.findByTag(tag);
    }

    @Override
    public List<Prompt> findByTagAndPrompt(String tag, String prompt) {
        return promptRepo.findByTagAndPrompt(tag, prompt);
    }

    @Override
    public List<Prompt> findByCreator(Users creator) {
        return promptRepo.findByCreator(creator);
    }
}
