package com.ezra.programandojuntos.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum SortDirection {
	ASC("asc"),

	DESC("desc");

	private String value;

	SortDirection(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
	
	 @JsonCreator
	  public static SortDirection fromValue(String value) {
	    for (SortDirection b : SortDirection.values()) {
	      if (b.value.equals(value)) {
	        return b;
	      }
	    }
	    throw new IllegalArgumentException("Unexpected value '" + value + "'");
	  }
}
