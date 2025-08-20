package net.kopuz.ApI.service;

import net.kopuz.ApI.exception.BadQueryRequestException;
import net.kopuz.ApI.tool.DateTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

import static org.springframework.ai.chat.client.ChatClient.builder;

@Service
public class AIService {
    private final DateTool dateTool;
    private final OpenAiChatModel chatModel;
    private final ChatClient chatClient;
    private final ChatMemory chatMemory;


    public AIService(DateTool dateTool, OpenAiChatModel chatModel, ChatMemory chatMemory) {
        this.dateTool = dateTool;
        this.chatModel = chatModel;
        this.chatMemory = MessageWindowChatMemory.builder().maxMessages(20).build();
        this.chatClient = builder(chatModel).defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build()).build();
    }

    public String chat(String query, final String conversationId){

        if(query.isEmpty()){
            throw new BadQueryRequestException("Client entry must be fulfilled!");
        }

        String response = chatClient
                .prompt()
                .user(query)
                //.tools(dateTool)
                .advisors(a ->
                        a.param(ChatMemory.CONVERSATION_ID, conversationId)
                )
                .call()
                .content();

        response += "\n\n Conversation Id: " + conversationId;

        return response;
    }
}
