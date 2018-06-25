package com.sinergise.io;

import com.sinergise.geometry.*;

import java.util.ArrayList;
import java.util.List;
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
            Point p = null;

            if (m.find()) {
                double x = Double.parseDouble(m.group(1));
                double y = Double.parseDouble(m.group(2));
                p = new Point(x, y);
            } else {
                p = new Point();
            }
            System.out.println(p.toString());
            return p;
        }

        else if (wktString.startsWith("LINESTRING")){
            Pattern regex = Pattern.compile("([\\d.d]+)\\s([\\d.d]+)");
            Matcher m = regex.matcher(wktString);
            List<Double> points = new ArrayList<Double>();

            while (m.find()) {
                points.add(Double.parseDouble(m.group(1)));
                points.add(Double.parseDouble(m.group(2)));
            }

            double[] coords = new double[points.size()];
            int i = 0;
            for (Double p : points) {
                coords[i] = p;
                i++;
            }
            System.out.println(new LineString(coords).toString());
            return new LineString(coords);
        }

        return null;
    }

    public static void main(String[] args){
        WKTReader wkt = new WKTReader();
        wkt.read("POINT (3.5 4)");
        wkt.read("LINESTRING EMPTY");
        wkt.read("LINESTRING (30 10, 10 30, 40 40)");
        wkt.read("LINESTRING (30.4 10, 10 30.2, 40.5 40)");
    }
}

