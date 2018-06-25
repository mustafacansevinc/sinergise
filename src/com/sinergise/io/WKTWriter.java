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
        String output = writeFormatted(geom, false); //using isSubElement to get a proper output
        return output;
    }

    private String decimalformat(double d){ //formatting doubles to get a proper output
        if (d == (long) d)
            return String.format("%d",(long)d);
        else
            return String.format("%s",d);
    }

    public String writeFormatted(Geometry geom, boolean isSubElement){
        String output = "";
        Class geom_class = geom.getClass();
        if (!isSubElement){
            output += geom_class.getSimpleName().toUpperCase(Locale.ENGLISH) + " ";

        }
        if (geom.isEmpty()){
            output += "EMPTY";
        }
        else if (geom_class == Point.class) {
            Point p = (Point) geom;
            output += "(" + decimalformat(p.getX()) + " " + decimalformat(p.getY()) + ")";
        }

        else if (geom_class == LineString.class) {
            LineString ls = (LineString)geom;
            output += "(";
            for (int i = 0; i < ls.getNumCoords(); i++) {
                output += decimalformat(ls.getX(i)) + " " + decimalformat(ls.getY(i));
                if (i != ls.getNumCoords() - 1)
                    output += ", ";
            }
            output += ")";
        }

        else if (geom_class == Polygon.class)
        {
            Polygon pol = (Polygon) geom;
            // couldnt find clear info about expected output here. So just imitating wikipedia example.
            output += "(" + writeFormatted(pol.getOuter(),true);
            for (int i = 0; i < pol.getNumHoles(); i++) {
                output += ", " + writeFormatted(pol.getHole(i), true);
            }
            output += ")";
        }

        else if (geom_class == MultiPoint.class) {
            MultiPoint mp = (MultiPoint) geom;
            output += "(";
            for (int i=0; i<mp.size(); i++) {
                output += writeFormatted(mp.get(i), true);
                if (i != mp.size() - 1)
                    output += ", ";
            }
            output += ")";
        }

        else if (geom_class == MultiLineString.class){
            MultiLineString mls = (MultiLineString)geom;
            output += "(";
            for (int i = 0; i<mls.size(); i++) {
                output +=  writeFormatted(mls.get(i), true);
                if (i != mls.size() - 1)
                    output += ", ";
            }
            output += ")";
        }

        else if (geom_class == MultiPolygon.class){
            MultiPolygon mpol = (MultiPolygon) geom;
            output += "(";
            for (int i = 0; i<mpol.size(); i++) {
                output +=  writeFormatted(mpol.get(i),true);
                if (i != mpol.size() - 1)
                    output += ", ";
            }
            output += ")";
        }

        else if (geom_class == GeometryCollection.class) {
            GeometryCollection gc = (GeometryCollection) geom;
            output += "(";
            for (int i=0; i<gc.size(); i++) {
                output += writeFormatted(gc.get(i), false);
                if (i != gc.size() - 1)
                    output += ", ";
            }
            output += ")";
        }
        return output;
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
