package com.ezra.programandojuntos.dto.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import com.ezra.programandojuntos.enums.TypeFile;

public class Report  {
    private final String name;
    private final TypeFile type;
    private final Map<String, String> parameters;

    public String getName() {
		return name;
	}

	public TypeFile getType() {
		return type;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}
	
	private Report(Builder builder) {
        this.name = builder.name;
        this.type = builder.type;
        this.parameters = Collections.unmodifiableMap(builder.parameters);//no permite adicionar mas elementos a parameters
    }

//    public boolean isKeepNullParams() {
//        return keepNullParams;
//    }

//    public static Builder name(String name) {
//        return new Builder().name(name);
//    }



	public static class Builder {

        private String name;
        private TypeFile type;
        //private Type type = Type.EXCELOPENXML;
        private Map<String, String> parameters = new LinkedHashMap<>();
        //private boolean keepNullParams = true;


        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder type(TypeFile type) {
            this.type = type;
            return this;
        }
//
//        public Builder parameter(String name, String value) {
//            this.parameters.computeIfAbsent(name, k -> new ArrayList<>()).add(value);
//            return this;
//        }

//        public Builder parameter(String name, List<Object> values) {
//            this.parameters.computeIfAbsent(name, k -> new ArrayList<>()).addAll(values);
//            return this;
//        }

        public Builder parameter(Map<String, String> parameters) {
            this.parameters = parameters;
            return this;
        }

        public Report build() {
            return new Report(this);
        }
    }

}
