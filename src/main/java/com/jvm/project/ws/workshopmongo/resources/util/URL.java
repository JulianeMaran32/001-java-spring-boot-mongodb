package com.jvm.project.ws.workshopmongo.resources.util;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class URL {
	
	public static String decodeParam(String text) throws UnsupportedEncodingException {
		return URL.decode(text, "UTF-8");
	}
	
	private static String decode(String text, String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Date convertDate(String textDate, Date defaultValue) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		try {
			return sdf.parse(textDate);
		} catch (ParseException e) {
			return defaultValue;
		}		
	}

}
