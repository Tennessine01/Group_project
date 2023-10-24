package com.ptit.englishapp.utils;


import java.util.HashMap;
import java.util.Map;

public class LanguageUtils {
    private static final Map<String, String> LANGUAGE_CODES = new HashMap<>();

    static {
        LANGUAGE_CODES.put("afrikaans", "af");
        LANGUAGE_CODES.put("albanian", "sq");
        LANGUAGE_CODES.put("arabic", "ar");
        LANGUAGE_CODES.put("belarusian", "be");
        LANGUAGE_CODES.put("bulgarian", "bg");
        LANGUAGE_CODES.put("bengali", "bn");
        LANGUAGE_CODES.put("catalan", "ca");
        LANGUAGE_CODES.put("chinese", "zh");
        LANGUAGE_CODES.put("croatian", "hr");
        LANGUAGE_CODES.put("czech", "cs");
        LANGUAGE_CODES.put("danish", "da");
        LANGUAGE_CODES.put("dutch", "nl");
        LANGUAGE_CODES.put("english", "en");
        LANGUAGE_CODES.put("esperanto", "eo");
        LANGUAGE_CODES.put("estonian", "et");
        LANGUAGE_CODES.put("finnish", "fi");
        LANGUAGE_CODES.put("french", "fr");
        LANGUAGE_CODES.put("galician", "gl");
        LANGUAGE_CODES.put("georgian", "ka");
        LANGUAGE_CODES.put("german", "de");
        LANGUAGE_CODES.put("greek", "el");
        LANGUAGE_CODES.put("gujarati", "gu");
        LANGUAGE_CODES.put("haitian creole", "ht");
        LANGUAGE_CODES.put("hebrew", "he");
        LANGUAGE_CODES.put("hindi", "hi");
        LANGUAGE_CODES.put("hungarian", "hu");
        LANGUAGE_CODES.put("icelandic", "is");
        LANGUAGE_CODES.put("indonesian", "id");
        LANGUAGE_CODES.put("irish", "ga");
        LANGUAGE_CODES.put("italian", "it");
        LANGUAGE_CODES.put("japanese", "ja");
        LANGUAGE_CODES.put("kannada", "kn");
        LANGUAGE_CODES.put("korean", "ko");
        LANGUAGE_CODES.put("lithuanian", "lt");
        LANGUAGE_CODES.put("latvian", "lv");
        LANGUAGE_CODES.put("macedonian", "mk");
        LANGUAGE_CODES.put("marathi", "mr");
        LANGUAGE_CODES.put("malay", "ms");
        LANGUAGE_CODES.put("maltese", "mt");
        LANGUAGE_CODES.put("norwegian", "no");
        LANGUAGE_CODES.put("persian", "fa");
        LANGUAGE_CODES.put("polish", "pl");
        LANGUAGE_CODES.put("portuguese", "pt");
        LANGUAGE_CODES.put("romanian", "ro");
        LANGUAGE_CODES.put("russian", "ru");
        LANGUAGE_CODES.put("slovak", "sk");
        LANGUAGE_CODES.put("slovenian", "sl");
        LANGUAGE_CODES.put("spanish", "es");
        LANGUAGE_CODES.put("swedish", "sv");
        LANGUAGE_CODES.put("swahili", "sw");
        LANGUAGE_CODES.put("tagalog", "tl");
        LANGUAGE_CODES.put("tamil", "ta");
        LANGUAGE_CODES.put("telugu", "te");
        LANGUAGE_CODES.put("thai", "th");
        LANGUAGE_CODES.put("turkish", "tr");
        LANGUAGE_CODES.put("ukrainian", "uk");
        LANGUAGE_CODES.put("urdu", "ur");
        LANGUAGE_CODES.put("vietnamese", "vi");
        LANGUAGE_CODES.put("welsh", "cy");
    }

    public static String[] fromLanguage = {
            "English", "Afrikaans", "Albanian", "Arabic", "Belarusian", "Bulgarian", "Bengali",
            "Catalan", "Chinese", "Croatian", "Czech", "Danish", "Dutch",
            "Esperanto", "Estonian", "Finnish", "French", "Galician", "Georgian",
            "German", "Greek", "Gujarati", "Haitian Creole", "Hebrew", "Hindi",
            "Hungarian", "Icelandic", "Indonesian", "Irish", "Italian", "Japanese",
            "Kannada", "Korean", "Lithuanian", "Latvian", "Macedonian", "Marathi",
            "Malay", "Maltese", "Norwegian", "Persian", "Polish", "Portuguese",
            "Romanian", "Russian", "Slovak", "Slovenian", "Spanish", "Swedish",
            "Swahili", "Tagalog", "Tamil", "Telugu", "Thai", "Turkish", "Ukrainian",
            "Urdu", "Vietnamese", "Welsh"
    };

    public static String[] toLanguage = {
            "Vietnamese", "Afrikaans", "Albanian", "Arabic", "Belarusian", "Bulgarian", "Bengali",
            "Catalan", "Chinese", "Croatian", "Czech", "Danish", "Dutch", "English",
            "Esperanto", "Estonian", "Finnish", "French", "Galician", "Georgian",
            "German", "Greek", "Gujarati", "Haitian Creole", "Hebrew", "Hindi",
            "Hungarian", "Icelandic", "Indonesian", "Irish", "Italian", "Japanese",
            "Kannada", "Korean", "Lithuanian", "Latvian", "Macedonian", "Marathi",
            "Malay", "Maltese", "Norwegian", "Persian", "Polish", "Portuguese",
            "Romanian", "Russian", "Slovak", "Slovenian", "Spanish", "Swedish",
            "Swahili", "Tagalog", "Tamil", "Telugu", "Thai", "Turkish", "Ukrainian",
            "Urdu", "Welsh"
    };

    public static String getLanguageCode(String languageName) {
        String code = LANGUAGE_CODES.get(languageName.toLowerCase());
        if (code == null) {
            throw new IllegalArgumentException("Unknown language name: " + languageName);
        }
        return code;
    }
}
