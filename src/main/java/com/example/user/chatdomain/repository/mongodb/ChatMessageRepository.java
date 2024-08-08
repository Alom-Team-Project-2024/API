package com.example.user.chatdomain.repository.mongodb;

import com.example.user.chatdomain.entity.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
}
