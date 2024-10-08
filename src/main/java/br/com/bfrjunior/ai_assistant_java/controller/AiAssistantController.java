package br.com.bfrjunior.ai_assistant_java.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bfrjunior.ai_assistant_java.dto.MessageDTO;
import br.com.bfrjunior.ai_assistant_java.factory.AiAssistantFactory;
import br.com.bfrjunior.ai_assistant_java.factory.ContentRetrieverFactory;
import br.com.bfrjunior.ai_assistant_java.factory.DocumentAssistantFactory;
import br.com.bfrjunior.ai_assistant_java.factory.EmbeddingFactory;
import dev.langchain4j.model.chat.ChatLanguageModel;

@RestController
@RequestMapping("/api/chat")
public class AiAssistantController {

    @Value("${langchain.huggingface.accessToken}")
    private String token;

    @PostMapping
    public ResponseEntity chat(@RequestBody MessageDTO messageDTO) {
        ChatLanguageModel chatModel = AiAssistantFactory.createLocalChatModel();
        var embeddingModel = EmbeddingFactory.createEmbeddingModel();
        var embeddingStore = EmbeddingFactory.createEmbeddingStore();
        var fileContentRetriever = ContentRetrieverFactory.createFileContentRetriever(
                embeddingModel,
                embeddingStore,
                "movies.txt");

        var documentAssistant = new DocumentAssistantFactory(chatModel, fileContentRetriever);
        String response = documentAssistant.chat(messageDTO.message());
        return ResponseEntity.ok().body(response);
    }
}
