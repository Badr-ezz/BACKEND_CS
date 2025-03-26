package com.example.springMongodb.controller;

import com.example.springMongodb.dto.CreatePromptRequest;
import com.example.springMongodb.model.Prompt;
import com.example.springMongodb.model.Users;
import com.example.springMongodb.service.PromptService;
import com.example.springMongodb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prompts")
@CrossOrigin
@RequiredArgsConstructor
public class PromptController {

    private final PromptService promptService;
    private final UserService userService;

    @GetMapping("/count")
    public ResponseEntity<Long> countPrompts() {
        return ResponseEntity.ok(promptService.countPrompts());
    }

    @GetMapping
    public ResponseEntity<List<Prompt>> getAllPrompts() {
        return ResponseEntity.ok(promptService.getAllPrompts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prompt> getPromptById(@PathVariable String id) {
        return ResponseEntity.ok(promptService.getPromptById(id));
    }

    @PostMapping
    public ResponseEntity<?> createPrompt(@RequestBody CreatePromptRequest request) {
        try {
            // Find the user (you'll need to implement UserService)
            Users creator = userService.getUserById(request.userId());
            if (creator == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            // Create and save the prompt
            Prompt newPrompt = new Prompt(creator, request.prompt(), request.tag());
            Prompt savedPrompt = promptService.addPrompt(newPrompt);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedPrompt);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create prompt");
        }
    }

    @PutMapping
    public ResponseEntity<Prompt> updatePrompt(@RequestBody Prompt prompt) {
        return ResponseEntity.ok(promptService.updatePrompt(prompt));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrompt(@PathVariable String id) {
        promptService.deletePrompt(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Prompt>> searchPrompts(
            @RequestParam(required = false) String prompt,
            @RequestParam(required = false) String tag) {

        if (prompt != null && tag != null) {
            return ResponseEntity.ok(promptService.findByTagAndPrompt(tag, prompt));
        } else if (prompt != null) {
            return ResponseEntity.ok(promptService.findByPrompt(prompt));
        } else if (tag != null) {
            return ResponseEntity.ok(promptService.findByTag(tag));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/creator/{creatorId}")
    public ResponseEntity<List<Prompt>> getPromptsByCreator(@PathVariable String creatorId) {
        // Assuming you have a way to get User by ID (you might need to inject UserService)
        Users creator = new Users();
        creator.setId(creatorId);
        return ResponseEntity.ok(promptService.findByCreator(creator));
    }
}