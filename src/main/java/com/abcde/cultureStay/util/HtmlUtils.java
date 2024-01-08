package com.abcde.cultureStay.util;

import com.abcde.cultureStay.vo.Program;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Safelist;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class HtmlUtils {

    static int maxLength = 500;
    static int maxLines = 15;

    public static String extractText(String html) {
        String text = Jsoup.clean(html, Safelist.none());
        text = text.replaceAll("&nbsp;", "");

        // 지정된 길이로 텍스트 자르기
        text = text.length() > maxLength ? text.substring(0, maxLength) : text;
        return text;
    }

    public static ArrayList<String> extractTwoImages(String html) {
        ArrayList<String> result = new ArrayList<>();

        Document document = Jsoup.parse(html);
        Elements images = document.select("img");

        for (Element img : images) {
            result.add(img.attr("src"));
        }

        return result;
    }

    public static ArrayList<Program> extractPreview(ArrayList<Program> programList) {
        for (Program program : programList) {
            // 미리보기용 이미지 추출
            ArrayList<String> list = extractTwoImages(program.getContent());
            program.setImagePath1(list.get(0));
            program.setImagePath2(list.get(1));

            // 미리보기용 텍스트 추출
            program.setContent(extractText(program.getContent()));
        }
        return programList;
    }


}
