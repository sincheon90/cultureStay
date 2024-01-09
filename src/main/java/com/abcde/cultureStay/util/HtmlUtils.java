package com.abcde.cultureStay.util;

import com.abcde.cultureStay.vo.Program;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Safelist;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class HtmlUtils {

    static int maxLength = 500;
    static int maxLines = 15;

    @Value("${image.getImage.url}")
    String url;
    static String defaultImage1 = "/img/board/img.png";

    static String defaultImage2 = "/img/board/img_5.png";

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
            ArrayList<String> imageList = extractTwoImages(program.getContent());

            // 이미지 리스트 검사 및 안전한 할당
            if (!imageList.isEmpty()) {
                program.setImagePath1(imageList.get(0));
                if (imageList.size() > 1) {
                    program.setImagePath2(imageList.get(1));
                } else {
                    // 이미지가 하나만 있는 경우 또는 대체할 기본 이미지 경로
                    program.setImagePath2("defaultImagePath2");
                }
            } else {
                // 이미지가 없는 경우 기본 이미지 경로 할당
                program.setImagePath1(defaultImage1);
                program.setImagePath2(defaultImage2);
            }

            // 미리보기용 텍스트 추출
            program.setContent(extractText(program.getContent()));
        }
        return programList;
    }


}
