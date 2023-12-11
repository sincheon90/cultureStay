package com.abcde.cultureStay.service;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TranslationService {

    @Value("${google.api.key}")
    private String apiKey;

    public String translateText(String text, String targetLanguage) throws JSONException {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://translation.googleapis.com/language/translate/v2";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject requestBody = new JSONObject();
        requestBody.put("q", text);
        requestBody.put("target", targetLanguage);
        requestBody.put("key", apiKey);

        HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);
        return extractTranslation(response.getBody());
    }

    private String extractTranslation(String response) {
        // Google 번역 API 응답에서 번역된 텍스트 추출
        // JSON 파싱 및 처리 로직 구현
        return "Extracted translation";
    }
}

