package com.abcde.cultureStay.service;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Service
public class TranslateService {
    String clientId = "3Nf77awa3Sp4Tft0ZT1F"; //애플리케이션 클라이언트 아이디값"
    String clientSecret = "wLyqz9eRbq"; //애플리케이션 클라이언트 시크릿값"

    String apiURL = "https://openapi.naver.com/v1/papago/n2mt";


    //원본언어: 한국어 (ko) -> 목적언어: 영어 (en)
    public String translate(String source, String target, String s_text){
        String translatedText = "";
        String text;
        try {
            text = URLEncoder.encode(s_text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("인코딩 실패", e);
        }

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);

        String responseBody = post(apiURL, requestHeaders, text, source, target);

//        System.out.println(responseBody); //{"message":{"@type":"response","@service":"naverservice.nmt.proxy","@version":"1.0.0","result":{"srcLangType":"ko","tarLangType":"en","translatedText":"Hello. How are you doing today?","engineType":"N2MT"}}}

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(responseBody);
            JSONObject message = jsonObject.getJSONObject("message");
            JSONObject result = message.getJSONObject("result");
            translatedText = result.getString("translatedText");

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return translatedText;
    }

    private static String post(String apiUrl, Map<String, String> requestHeaders, String text, String source, String target){
        HttpURLConnection con = connect(apiUrl);
        String postParams = "source=" + source + "&target=" + target + "&text=" + text; //원본언어: 한국어 (ko) -> 목적언어: 영어 (en)
        try {
            con.setRequestMethod("POST");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            con.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postParams.getBytes());
                wr.flush();
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 응답
                return readBody(con.getInputStream());
            } else {  // 에러 응답
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }
}
