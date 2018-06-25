package com.sinergise.io;

import com.sinergise.geometry.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WKTReader {

    /**
     * Transforms the input WKT-formatted String into Geometry object
     */
    public Geometry read(String wktString) {
        //TODO: Implement this
        //TODO: Check RegEx of readers.
        //TODO: Check EMPTY objects
        //TODO: Add fashion
        //TODO: Add comments
        if (wktString.startsWith("POINT")){
            return readPoint(wktString);
        }

        else if (wktString.startsWith("LINESTRING")){
            return readLineString(wktString);
        }

        else if (wktString.startsWith("POLYGON")) {
            return readPolygon(wktString);
        }

        else if (wktString.startsWith("MULTIPOINT")) {
            return readMultiPoint(wktString);
        }

        else if (wktString.startsWith("MULTILINESTRING")) {
            return readMultiLineString(wktString);
        }

        else if (wktString.startsWith("MULTIPOLYGON")) {
            return readMultiPolygon(wktString);
        }

        else if (wktString.startsWith("GEOMETRYCOLLECTION")) {
            return readGeometryCollection(wktString);
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
        System.out.println(new WKTWriter().write(p));
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
        LineString ls = new LineString(coords);
        System.out.println(new WKTWriter().write(ls));
        return ls;
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
        System.out.println(new WKTWriter().write(pol));
        return pol;
    }

    private MultiPoint readMultiPoint(String wktString){
        Pattern regex = Pattern.compile("([\\d.d]+)\\s([\\d.d]+)");
        Matcher m = regex.matcher(wktString);
        List<Point> p = new ArrayList<Point>();

        while (m.find()) {
            p.add(readPoint(m.group()));
        }

        Point points[] = new Point[p.size()];
        int i = 0;
        for (Point point : p) {
            points[i] = point;
            i++;
        }
        MultiPoint mp = new MultiPoint(points);
        System.out.println(new WKTWriter().write(mp));
        return mp;
    }

    private MultiLineString readMultiLineString(String wktString){
        Pattern regex = Pattern.compile("(\\([\\d.d\\s,]+\\))");    //check regex
        Matcher m = regex.matcher(wktString);
        List<LineString> ls = new ArrayList<LineString>();

        while (m.find()) {
            ls.add(readLineString(m.group()));
        }

        LineString lineStrings[] = new LineString[ls.size()];
        int i = 0;
        for (LineString linestring : ls) {
            lineStrings[i] = linestring;
            i++;
        }
        MultiLineString mls = new MultiLineString(lineStrings);
        System.out.println(new WKTWriter().write(mls));
        return mls;
    }

    private MultiPolygon readMultiPolygon(String wktString){
        Pattern regex = Pattern.compile("(\\([\\d.d\\s,]+\\))"); //check regex
        Matcher m = regex.matcher(wktString);
        List<Polygon> pol = new ArrayList<Polygon>();

        while (m.find()) {
            pol.add(readPolygon(m.group()));
        }

        Polygon polys[] = new Polygon[pol.size()];
        int i = 0;
        for (Polygon polygon : pol) {
            polys[i] = polygon;
            i++;
        }
        MultiPolygon mpol = new MultiPolygon(polys);
        System.out.println(new WKTWriter().write(mpol));
        return mpol;
    }

    private GeometryCollection<Geometry> readGeometryCollection(String wktString){
        Pattern regex = Pattern.compile("(\\w+\\s+(\\([\\d.d\\s,]+\\)))"); //check regex
        Matcher m = regex.matcher(wktString);
        List <Geometry> gc = new ArrayList<Geometry>();

        while (m.find()) {
            gc.add(read(m.group()));
        }

        Geometry elements[] = new Geometry[gc.size()];
        int i = 0;
        for (Geometry geometry : gc) {
            elements[i] = geometry;
            i++;
        }
        GeometryCollection gcol = new GeometryCollection(elements);
        System.out.println(new WKTWriter().write(gcol));
        return gcol;
    }

    public static void main(String[] args){
        WKTReader wkt = new WKTReader();
        wkt.read("POINT (3.5 4)");
        wkt.read("LINESTRING EMPTY");
        wkt.read("LINESTRING (30 10, 10 30, 40 40)");
        wkt.read("LINESTRING (30.4 10, 10 30.2, 40.5 40)");
        wkt.read("POLYGON ((35 10, 45 45, 15 40, 10 20, 35 10), (20 30, 35 35, 30 20, 20 30))");
        wkt.read("MULTIPOINT ((4 6), (5 10))");
        wkt.readMultiPolygon("MULTIPOLYGON (((40 40, 20 45, 45 30, 40 40)), ((20 35, 10 30, 10 10, 30 5, 45 20, 20 35), (30 20, 20 15, 20 25, 30 20)))");
        wkt.readGeometryCollection("GEOMETRYCOLLECTION (POINT (4 6), LINESTRING (4 6, 7 10))");
    }
}

