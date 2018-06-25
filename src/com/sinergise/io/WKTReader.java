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
            return readPoint(wktString);
        }

        else if (wktString.startsWith("LINESTRING")){
            return readLineString(wktString);
        }

        else if (wktString.startsWith("POLYGON")) {
            return readPolygon(wktString);
        }

        return null;
    }
    private Point readPoint(String wktString){
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

    private LineString readLineString(String wktString){
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

    private Polygon readPolygon(String wktString){
        Pattern regex = Pattern.compile("(\\([\\d.d\\s,]+\\))");
        Matcher m = regex.matcher(wktString);
        List<LineString> ls = new ArrayList<LineString>();

        while (m.find()) {
            ls.add(readLineString(m.group()));
        }

        LineString outer = ls.remove(0);
        LineString[] holes = new LineString[ls.size()];
        int i = 0;
        for (LineString l : ls) {
            holes[i] = l;
            i++;
        }
        Polygon pol = new Polygon(outer,holes);
        System.out.println(pol.toString());
        return pol;
    }

    public static void main(String[] args){
        WKTReader wkt = new WKTReader();
        wkt.read("POINT (3.5 4)");
        wkt.read("LINESTRING EMPTY");
        wkt.read("LINESTRING (30 10, 10 30, 40 40)");
        wkt.read("LINESTRING (30.4 10, 10 30.2, 40.5 40)");
        wkt.read("POLYGON ((35 10, 45 45, 15 40, 10 20, 35 10), (20 30, 35 35, 30 20, 20 30))");
    }
}

