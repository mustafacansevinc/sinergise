package com.sinergise.io;

import com.sinergise.geometry.*;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WKTReader {

	/**
	 * Transforms the input WKT-formatted String into Geometry object
	 */
	public Geometry read(String wktString) {
		//TODO: Implement this
		if (wktString.startsWith("POINT")){
			Pattern regex = Pattern.compile("([\\d.d]+)\\s([\\d.d]+)");
			Matcher m = regex.matcher(wktString);
			double x = Double.parseDouble(m.group(1));
			double y = Double.parseDouble(m.group(2));
			Point p = new Point(x, y);


			System.out.println(p.toString());
			return p;
		}

		return null;
	}

	public static void main(String[] args){
		WKTReader wkt = new WKTReader();
		wkt.read("POINT (3.5 4)");
	}
}

