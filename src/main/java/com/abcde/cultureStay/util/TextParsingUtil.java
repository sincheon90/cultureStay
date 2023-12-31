package com.abcde.cultureStay.util;

import org.jsoup.Jsoup;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TextParsingUtil {

    public static void main(String[] args) {
        String html = "\"<h1>타루 커뮤니티 홈스테이</h1>\n" +
                "\n" +
                "<p>공유하기</p>\n" +
                "\n" +
                "\t\t\t<td>&nbsp;</td>\n" +
                "\"";
        System.out.println(extractTextFromHtml(html, 5));
    }
    public static String extractTextFromHtml(String html, int maxLines) {
        String text = Jsoup.parse(html).text();
        String[] lines = text.split("\\R");
        return Arrays.stream(lines, 0, Math.min(lines.length, maxLines))
                .collect(Collectors.joining("\n")).trim();
    }

    public static String extractTextFromHtml(String html) {
        String text = Jsoup.parse(html).text(); // Jsoup 라이브러리를 사용하여 HTML에서 텍스트 추출
        return text; // 처음 8줄만 선택 및 공백 제거
    }

}
