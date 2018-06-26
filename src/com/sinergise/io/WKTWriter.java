package com.sinergise.io;

import com.sinergise.geometry.*;

import java.util.Locale;

public class WKTWriter {

    /**
     * Transforms the input Geometry object into WKT-formatted String. e.g.
     * <pre><code>
     * new WKTWriter().write(new LineString(new double[]{30, 10, 10, 30, 40, 40}));
     * //returns "LINESTRING (30 10, 10 30, 40 40)"
     * </code></pre>
     */


    public String write(Geometry geom) {
        //TODO: Implement this
        //TODO: Use instanceof
        //TODO: Add fashion
        //TODO: Add comments
        String wktString = writeFormatted(geom, false); //using isSubElement to get a proper wktString
        return wktString;
    }

    private String writePoint(Geometry geom) {
        Point p = (Point) geom;
        return "(" + decimalformat(p.getX()) + " " + decimalformat(p.getY()) + ")";
    }

    private String writeLineString(Geometry geom){
        LineString ls = (LineString)geom;
        String wktString = "(";
        for (int i = 0; i < ls.getNumCoords(); i++) {
            wktString += decimalformat(ls.getX(i)) + " " + decimalformat(ls.getY(i));
            if (i != ls.getNumCoords() - 1)
                wktString += ", ";
        }
        wktString += ")";
        return wktString;
    }

    private String writePolygon(Geometry geom) {
        Polygon pol = (Polygon) geom;
        // couldnt find clear info about expected wktString here. So just imitating wikipedia example.
        String wktString = "(" + writeFormatted(pol.getOuter(),true);
        for (int i = 0; i < pol.getNumHoles(); i++) {
            wktString += ", " + writeFormatted(pol.getHole(i), true);
        }
        wktString += ")";
        return wktString;
    }

    private String writeMultiPoint(Geometry geom) {
        MultiPoint mp = (MultiPoint) geom;
        String wktString = "(";
        for (int i=0; i<mp.size(); i++) {
            wktString += writeFormatted(mp.get(i), true);
            if (i != mp.size() - 1)
                wktString += ", ";
        }
        wktString += ")";
        return wktString;
    }

    private String writeMultiLineString(Geometry geom){

        MultiLineString mls = (MultiLineString)geom;
        String wktString = "(";
        for (int i = 0; i<mls.size(); i++) {
            wktString +=  writeFormatted(mls.get(i), true);
            if (i != mls.size() - 1)
                wktString += ", ";
        }
        wktString += ")";
        return wktString;
    }

    private String writeMultiPolygon(Geometry geom){

        MultiPolygon mpol = (MultiPolygon) geom;
        String wktString = "(";
        for (int i = 0; i<mpol.size(); i++) {
            wktString +=  writeFormatted(mpol.get(i),true);
            if (i != mpol.size() - 1)
                wktString += ", ";
        }
        wktString += ")";
        return wktString;
    }

    private String writeGeometryCollection(Geometry geom){
        GeometryCollection gc = (GeometryCollection) geom;
        String wktString = "(";
        for (int i=0; i<gc.size(); i++) {
            wktString += writeFormatted(gc.get(i), false);
            if (i != gc.size() - 1)
                wktString += ", ";
        }
        wktString += ")";
        return wktString;
    }

    private String decimalformat(double d){ //formatting doubles to get a proper wktString
        if (d == (long) d)
            return String.format("%d",(long)d);
        else
            return String.format("%s",d);
    }

    public String writeFormatted(Geometry geom, boolean isSubElement){
        String wktString = "";
        Class geom_class = geom.getClass();
        if (!isSubElement){
            wktString += geom.getClass().getSimpleName().toUpperCase(Locale.ENGLISH) + " "; //to get exactly "LINESTRING"

        }
        if (geom.isEmpty()){
            wktString += "EMPTY";
        }
        else if (geom instanceof Point) {
            wktString += writePoint(geom);
        }

        else if (geom instanceof LineString) {
            wktString += writeLineString(geom);
        }

        else if (geom instanceof Polygon)
        {
            wktString += writePolygon(geom);
        }

        else if (geom instanceof MultiPoint) {

            wktString += writeMultiPoint(geom);
        }

        else if (geom instanceof MultiLineString){
            wktString += writeMultiLineString(geom);
        }

        else if (geom instanceof MultiPolygon){
            wktString += writeMultiPolygon(geom);
        }

        else if (geom instanceof GeometryCollection) {

            wktString += writeGeometryCollection(geom);
        }
        return wktString;
    }


    public static void main(String[] args){
        WKTWriter wkt = new WKTWriter();
        System.out.println(wkt.write(new Point(3,4)));
        System.out.println(wkt.write(new LineString(new double[]{30, 10, 10, 30, 40, 40})));
        System.out.println(wkt.write(new LineString()));
        System.out.println(wkt.write(new MultiPoint(new Point[]{new Point(4, 6), new Point(5, 10)})));
        System.out.println(wkt.write(new GeometryCollection<Geometry>(new Geometry[]{new Point(4,6), new LineString(new double[] {4,6,7,10})})));
        System.out.println(wkt.write(new Polygon(new LineString(new double[] {35, 10, 45, 45, 15, 40, 10, 20, 35, 10}), new LineString[]{new LineString(new double[]{20, 30, 35, 35, 30, 20, 20, 30})})));
        System.out.println(wkt.write(new MultiLineString(new LineString[]{new LineString(new double[]{30, 10, 10, 30, 40, 40}), new LineString(new double[] {35, 10, 45, 45, 15, 40, 10, 20, 35, 10}), new LineString(new double[]{20, 30, 35, 35, 30, 20, 20, 30}) })));
        System.out.println(wkt.write(new MultiPolygon(new Polygon[]{(new Polygon(new LineString(new double[]{40, 40, 20, 45, 45, 30, 40, 40}), new LineString[]{})),(new Polygon(new LineString(new double[]{20, 35, 10, 30, 10, 10, 30, 5, 45, 20, 20, 35}), new LineString[]{new LineString(new double[]{30, 20, 20, 15, 20, 25, 30, 20})})) })));
    }
}
