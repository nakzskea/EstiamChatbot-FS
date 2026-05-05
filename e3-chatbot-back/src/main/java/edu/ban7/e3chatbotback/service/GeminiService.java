package edu.ban7.e3chatbotback.service;

import edu.ban7.e3chatbotback.dao.ProductDao;
import edu.ban7.e3chatbotback.dao.RecipeDao;
import edu.ban7.e3chatbotback.model.Product;
import edu.ban7.e3chatbotback.model.Recipe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    private final RestClient restClient;
    private final String apiKey;
    private final String apiUrl;
    private final RecipeDao recipeDao;
    private final ProductDao productDao; // 💡 On ajoute le DAO des produits

    public GeminiService(RestClient.Builder restClientBuilder,
                         @Value("${gemini.api.key}") String apiKey,
                         @Value("${gemini.api.url}") String apiUrl,
                         RecipeDao recipeDao,
                         ProductDao productDao) { // 💡 Injection dans le constructeur
        this.restClient = restClientBuilder.build();
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
        this.recipeDao = recipeDao;
        this.productDao = productDao;
    }

    public String chatWithGemini(String userPrompt) {
        List<Recipe> recipes = recipeDao.findAll();
        List<Product> products = productDao.findAll();

        StringBuilder contextPrompt = new StringBuilder();
        contextPrompt.append("Tu es l'assistant virtuel culinaire du site Estiam Chatbot. ");
        contextPrompt.append("Tu dois répondre de manière sympathique, concise et experte. ");

        contextPrompt.append("\nVoici la liste des ingrédients (produits) disponibles dans notre base de données :\n");
        for (Product p : products) {
            contextPrompt.append("- ").append(p.getName()).append("\n");
        }

        contextPrompt.append("\nVoici les recettes actuellement disponibles sur notre site :\n");
        for (Recipe r : recipes) {
            contextPrompt.append("- ").append(r.getName()).append("\n");
        }

        contextPrompt.append("\nEn te basant UNIQUEMENT sur ces recettes et ces ingrédients, réponds à la question de l'utilisateur : ");
        contextPrompt.append(userPrompt);

        Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", contextPrompt.toString())
                        ))
                )
        );

        try {
            Map response = restClient.post()
                    .uri(apiUrl + "?key=" + apiKey)
                    .body(requestBody)
                    .retrieve()
                    .body(Map.class);

            List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
            Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
            List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");

            return (String) parts.get(0).get("text");

        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur";
        }
    }
}