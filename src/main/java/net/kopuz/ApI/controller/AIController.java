package net.kopuz.ApI.controller;

import net.kopuz.ApI.service.AIService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AIController {
    private final AIService aiService;

    public AIController(AIService aiService) {
        this.aiService = aiService;
    }

    
}
