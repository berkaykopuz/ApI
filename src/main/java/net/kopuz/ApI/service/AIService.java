package net.kopuz.ApI.service;

import net.kopuz.ApI.exception.BadQueryRequestException;
import net.kopuz.ApI.tool.DateTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AIService {
    private final DateTool dateTool;
    private final OpenAiChatModel chatModel;
    private final ChatMemory chatMemory;

    public AIService(DateTool dateTool, OpenAiChatModel chatModel, ChatMemory chatMemory) {
        this.dateTool = dateTool;
        this.chatModel = chatModel;
        this.chatMemory = chatMemory;
    }

    public String chat(String query, final String conversationId){

        if(query.isEmpty()){
            throw new BadQueryRequestException("Client entry must be fulfilled!");
        }

        return ChatClient.create(chatModel)
                .prompt(query)
                .tools(dateTool)
                .advisors(advisor -> advisor.param(ChatMemory.CONVERSATION_ID, conversationId))
                .call()
                .content();
    }
}
