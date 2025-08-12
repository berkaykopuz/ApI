package net.kopuz.ApI.service;

import net.kopuz.ApI.exception.BadQueryRequestException;
import net.kopuz.ApI.tool.DateTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@Service
public class AIService {
    private final ChatClient chatClient;
    private final DateTool dateTool;

    public AIService(ChatClient.Builder builder, DateTool dateTool) {
        this.chatClient = builder.build();
        this.dateTool = dateTool;
    }

    public String chat(String prompt){

        if(prompt.isEmpty()){
            throw new BadQueryRequestException("Client entry must be fulfilled!");
        }

        return chatClient.prompt(prompt).tools(dateTool).call().content();
    }
}
