package com.abcde.cultureStay.restController;

import com.abcde.cultureStay.service.TranslationService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/translate")
public class TranslationController {

    @Autowired
    private TranslationService translationService;

    @GetMapping
    public String translate(@RequestParam String text, @RequestParam String targetLanguage) {

        System.out.println("[/api/translate]");
        try {
            return translationService.translateText(text, targetLanguage);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
