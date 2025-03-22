package com.ezra.programandojuntos.enums;


import java.util.HashMap;
import java.util.Map;


public enum TypeFile {
    PPTX("PPTX", "pptx"),
    ATOM("ATOM", "atom"),
    MHTML("MHTML", "mhtml"),
    HTML5("HTML5", "html"),
    HTML4("HTML4.0", "html"),
    IMAGE("IMAGE", "jpg"),
    EXCEL("EXCEL", "xls"),
    EXCELOPENXML("EXCELOPENXML", "xlsx"),
    WORD("WORD", "doc"),
    WORDOPENXML("WORDOPENXML", "docx"),
    CSV("CSV", "csv"),
    PDF("PDF", "pdf");
	
    private final String value;
    private final String extension;
    private static final Map<String, TypeFile> _map = new HashMap<>();


    private TypeFile(String value, String extension) {
        this.value = value;
        this.extension = extension;
    }

    static {
        for (TypeFile app : TypeFile.values()) { //values() recoree el enum
            _map.put(app.value, app);
        }
    }

    public String getValue() {
        return value;
    }

    public String getExtension() {
        return extension;
    }

}
