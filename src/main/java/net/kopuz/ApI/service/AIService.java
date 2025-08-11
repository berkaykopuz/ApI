package net.kopuz.ApI.service;

import net.kopuz.ApI.exception.BadQueryRequestException;
import org.apache.coyote.BadRequestException;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AIService {
    private final ChatClient chatClient;

    public AIService(ChatClient.Builder builder) {
        chatClient = builder.build();
    }

    public String chat(String prompt){

        if(prompt.isEmpty()){
            throw new BadQueryRequestException("Client entry must be fulfilled!");
        }

        return chatClient.prompt(prompt).call().content();
    }
}
