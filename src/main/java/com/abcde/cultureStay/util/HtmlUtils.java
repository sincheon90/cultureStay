package com.abcde.cultureStay.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Safelist;

import java.util.Arrays;
import java.util.stream.Collectors;

public class HtmlUtils {

    static int maxLength = 500;
    static int maxLines = 15;

    public static void main(String[] args) {
        String html =
                "<h1>타루 커뮤니티 홈스테이</h1>\n" +
                        "\n" +
                        " \n" +
                        "<td>&nbsp;</td>"+
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

        System.out.println(extractText(html));
    }

    public static String extractTextWithLines(String html) {
        // html tag 삭제, 줄바꿈 허용
        String text = Jsoup.clean(html
                , "http://localhost"
                , Safelist.none().preserveRelativeLinks(true)
                , new Document.OutputSettings().prettyPrint(false));

        text = text.replaceAll("&nbsp;", "");

        String[] lines = text.split("\\R");

        // Filter out empty or whitespace-only lines
        String[] filteredLines = Arrays.stream(lines)
                .filter(line -> !line.trim().isEmpty())
                .toArray(String[]::new);

//        System.out.println(Arrays.toString(filteredLines));

        text = Arrays.stream(filteredLines, 0, Math.min(lines.length, maxLines))
                .collect(Collectors.joining("\n")).trim();
//        System.out.println(text.replace("\n","\\n"));
        return text.replace("\n","\\n");
    }


    public static String extractTextWithLength(String _html) {
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

    public static String extractHtmlSummary(String htmlContent) {
        Document document = Jsoup.parse(htmlContent);
        Element firstParagraph = document.select("p").first(); // 첫 번째 단락만 추출
        return firstParagraph != null ? firstParagraph.outerHtml() : "";
    }

    public static String extractText(String html) {
        String text = Jsoup.clean(html, Safelist.none());
        text = text.replaceAll("&nbsp;", "");

        // 지정된 길이로 텍스트 자르기
        text = text.length() > maxLength ? text.substring(0, maxLength) : text;
        return text;
    }

}