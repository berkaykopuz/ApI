package net.kopuz.ApI.controller;

import net.kopuz.ApI.dto.QueryRequest;
import net.kopuz.ApI.service.AIService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class AIController {
    private final AIService aiService;

    public AIController(AIService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/api/prompt")
    public ResponseEntity<?> getResponse(@RequestBody QueryRequest request){

        if(request.getConversationId() == null || request.getConversationId().isEmpty()){
            request.setConversationId(UUID.randomUUID().toString());
        }

        return new ResponseEntity<>(aiService.chat(request.getQuery(), request.getConversationId()), HttpStatus.OK);
    }
    
}
