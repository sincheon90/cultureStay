package com.abcde.cultureStay.util;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;

import java.io.FileInputStream;

public class TestMain {

    public static void main(String[] args) {
        String credentialsPath = "C:/";

        Translate translate = null;

        try {
            // OAuth2 인증으로 GoogleCredentials를 로드합니다.
            GoogleCredentials credentials = GoogleCredentials
                    .fromStream(new FileInputStream(credentialsPath))
                    .createScoped("https://www.googleapis.com/auth/cloud-platform");

            // GoogleCredentials를 사용하여 TranslateOptions을 초기화합니다.
            translate = TranslateOptions.newBuilder().setCredentials(credentials).build().getService();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Translate API를 사용하여 번역합니다.
        if (translate != null) {
            Translation translation = translate.translate(
                    "Hola Mundo!",
                    Translate.TranslateOption.sourceLanguage("es"),
                    Translate.TranslateOption.targetLanguage("de"),
                    Translate.TranslateOption.model("nmt")
            );

            System.out.printf("Translated Text:\nText: %s\n", translation.getTranslatedText());
        }



//        Translate translate = TranslateOptions.getDefaultInstance().getService();
//
//        Translation translation =
//                translate.translate(
//                        "Hola Mundo!",
//                        Translate.TranslateOption.sourceLanguage("es"),
//                        Translate.TranslateOption.targetLanguage("de"),
//                        // Use "base" for standard edition, "nmt" for the premium model.
//                        Translate.TranslateOption.model("nmt"));
//
//        System.out.printf("TranslatedText:\nText: %s\n", translation.getTranslatedText());


    }
}
