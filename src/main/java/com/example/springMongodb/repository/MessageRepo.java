package com.example.springMongodb.repository;

import com.example.springMongodb.model.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepo extends MongoRepository<Message, String> {
    List<Message> findByTeamId(String teamId, Pageable pageable);
    List<Message> findByTeamIdOrderByCreatedAtDesc(String teamId, Pageable pageable);
    List<Message> findBySenderId(String senderId, Pageable pageable);
}

