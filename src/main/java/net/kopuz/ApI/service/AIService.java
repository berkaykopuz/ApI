package net.kopuz.ApI.service;

import net.kopuz.ApI.exception.BadQueryRequestException;
import net.kopuz.ApI.tool.DateTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

@Service
public class AIService {
    private final DateTool dateTool;
    private final OpenAiChatModel chatModel;

    public AIService(DateTool dateTool, OpenAiChatModel chatModel) {
        this.dateTool = dateTool;
        this.chatModel = chatModel;
    }

    public String chat(String query){

        if(query.isEmpty()){
            throw new BadQueryRequestException("Client entry must be fulfilled!");
        }

        return ChatClient.create(chatModel)
                .prompt(query)
                .tools(dateTool)
                .call()
                .content();
    }
}
