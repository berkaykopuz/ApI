package net.kopuz.ApI.controller;

import net.kopuz.ApI.service.AIService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AIController {
    private final AIService aiService;

    public AIController(AIService aiService) {
        this.aiService = aiService;
    }

    @GetMapping("/api/prompt")
    public ResponseEntity<?> getResponse(@RequestParam("query") String query){
        return new ResponseEntity<>(aiService.chat(query), HttpStatus.OK);
    }
    
}
