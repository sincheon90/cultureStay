package com.abcde.cultureStay.util;

import com.abcde.cultureStay.service.TranslateService;

import java.io.FileInputStream;
import java.io.IOException;

public class TestMain {

    public static void main(String[] args) {

        TranslateService service = new TranslateService();

        String text = "헤이 거기 안녕!!";

        String result = service.translate("ko", "en", text);
        System.out.println(result);

    }
}
