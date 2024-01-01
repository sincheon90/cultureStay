package com.abcde.cultureStay.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Safelist;

import java.util.Arrays;
import java.util.stream.Collectors;

public class HtmlUtils {

    public static void main(String[] args) {
        String html =
                "<h1>타루 커뮤니티 홈스테이</h1>\n" +
                "<p>공유하기</p>\n" +
                "<h1>타루 커뮤니티 홈스테이</h1>\n" +
                "<p>공유하기</p>\n" +
                "<p>공유하기</p>\n" +
                "<h1>타루 커뮤니티 홈스테이</h1>\n" +
                "<p>공유하기</p>\n" +
                "<p>공유하기</p>\n" +
                "<h1>타루 커뮤니티 홈스테이</h1>\n" +
                "<p>공유하기</p>\n" +
                "<td>&nbsp;</td>";
        System.out.println(extractTextWithLines(html, 5));
    }

    public static String extractTextWithLength(String _html, int maxLength) {
        // html tag 삭제, 줄바꿈 허용
        String text = Jsoup.clean(_html
                , "http://localhost"
                , Safelist.none().preserveRelativeLinks(true)
                , new Document.OutputSettings().prettyPrint(false));

        text = text.replaceAll("&nbsp;", "");

        // 지정된 길이로 텍스트 자르기
        text = text.length() > maxLength ? text.substring(0, maxLength) + "..." : text;
        System.out.println(text);
        return text;
    }

    public static String extractTextWithLines(String html, int maxLines) {
        // html tag 삭제, 줄바꿈 허용
        String text = Jsoup.clean(html
                , "http://localhost"
                , Safelist.none().preserveRelativeLinks(true)
                , new Document.OutputSettings().prettyPrint(false));

        text = text.replaceAll("&nbsp;", "");

        String[] lines = text.split("\\R");
        text = Arrays.stream(lines, 0, Math.min(lines.length, maxLines))
                .collect(Collectors.joining("\n")).trim();
        System.out.println(text);
        return text;
    }

}
