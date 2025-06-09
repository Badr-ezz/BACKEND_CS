package com.example.springMongodb.service.message;

import com.example.springMongodb.model.Message;
import com.example.springMongodb.repository.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepo messageRepository;

    @Autowired
    public MessageServiceImpl(MessageRepo messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public List<Message> saveMessages(List<Message> messages) {
        return messageRepository.saveAll(messages);
    }

    @Override
    public Message getMessageById(String id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found with id: " + id));
    }

    @Override
    public List<Message> getMessagesByTeam(String teamId, int limit) {
        // Get the most recent messages first, then reverse for chronological order
        PageRequest pageRequest = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<Message> messages = messageRepository.findByTeamId(teamId, pageRequest);

        // Reverse to get chronological order (oldest first)
        java.util.Collections.reverse(messages);
        return messages;
    }

    @Override
    public List<Message> getMessagesBySender(String senderId, int limit) {
        PageRequest pageRequest = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        return messageRepository.findBySenderId(senderId, pageRequest);
    }

    @Override
    public void deleteMessage(String id) {
        messageRepository.deleteById(id);
    }
}
