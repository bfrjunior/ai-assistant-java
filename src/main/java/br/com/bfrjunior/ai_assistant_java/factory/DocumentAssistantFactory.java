package br.com.bfrjunior.ai_assistant_java.factory;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.service.AiServices;
import br.com.bfrjunior.ai_assistant_java.utils.DocumentService;

public class DocumentAssistantFactory {
    private DocumentService documentAiService;

    public DocumentAssistantFactory(ChatLanguageModel chatModel, ContentRetriever contentRetriever) {
        documentAiService = buildDocumentAiService(chatModel, contentRetriever);
    }

    public String chat(String message) {
        return documentAiService.chat(message);
    }

    private DocumentService buildDocumentAiService(ChatLanguageModel chatModel, ContentRetriever contentRetriever) {
        return AiServices.builder(DocumentService.class)
                .chatLanguageModel(chatModel)
                .contentRetriever(contentRetriever)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
                .build();
    }
}