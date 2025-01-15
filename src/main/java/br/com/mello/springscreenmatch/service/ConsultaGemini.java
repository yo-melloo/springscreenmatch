package br.com.mello.springscreenmatch.service;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;

public class ConsultaGemini {

    public static String obterTraducao(String texto) {

        ChatLanguageModel gemini = GoogleAiGeminiChatModel.builder()
                .apiKey(System.getenv("GEMNI_KEY"))
                .modelName("gemini-1.5-flash")
                .build();

        String res = gemini.generate("Traduza para português o texto: " + texto);
        return res;
    }
}