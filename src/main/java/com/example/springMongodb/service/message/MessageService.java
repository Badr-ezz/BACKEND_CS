package com.example.springMongodb.service.message;

import com.example.springMongodb.model.Message;
import java.util.List;

public interface MessageService {
    Message saveMessage(Message message);
    List<Message> saveMessages(List<Message> messages);
    Message getMessageById(String id);
    List<Message> getMessagesByTeam(String teamId, int limit);
    List<Message> getMessagesBySender(String senderId, int limit);
    void deleteMessage(String id);
}
