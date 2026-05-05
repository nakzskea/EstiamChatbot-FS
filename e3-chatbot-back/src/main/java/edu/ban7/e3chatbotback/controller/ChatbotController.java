package edu.ban7.e3chatbotback.controller;

import edu.ban7.e3chatbotback.service.GeminiService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "http://localhost:4200") // Autorise Angular à appeler cette route
public class ChatbotController {

    private final GeminiService geminiService;

    public ChatbotController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping
    public Map<String, String> askQuestion(@RequestBody Map<String, String> request) {
        String userPrompt = request.get("prompt");
        String aiResponse = geminiService.chatWithGemini(userPrompt);

        // On renvoie un objet JSON propre : { "response": "texte..." }
        return Map.of("response", aiResponse);
    }
}