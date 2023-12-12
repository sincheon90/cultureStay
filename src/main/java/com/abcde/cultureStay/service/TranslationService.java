package com.abcde.cultureStay.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
@XSlf4j
public class TranslationService {

    @Value("${google.credentials.path}")
    private String credentialsPath;

    public HashMap<String, Object> getTranslatedText() {

        Map<String, Object> map = new HashMap<>();
        String text= "";
        String sourceLanguage = "";
        String targetLanguage = "";
        // json 파일 경로
        String jsonPath = credentialsPath;
        // json 파일에서 GoogleCredentials 객체 생성
        try (InputStream serviceAccountStream = new URL(jsonPath).openStream()) {
            GoogleCredentials credentials = ServiceAccountCredentials.fromStream(serviceAccountStream);
            // Translate 서비스 생성
            Translate translate = TranslateOptions.newBuilder().setCredentials(credentials).build().getService();
            // 번역 api 코드 추가
            Translation translation = translate.translate(text, Translate.TranslateOption.sourceLanguage(sourceLanguage),
                    Translate.TranslateOption.targetLanguage(targetLanguage));
            String translatedText =  translation.getTranslatedText();
            translatedText = translatedText.replaceAll("&#39;", "\'");
            map.put("translatedText", translatedText);
        } catch (IOException e) {
            // ...
        }
        return map.size () > 0 ? (HashMap<String, Object>) map : null;
    }
}

