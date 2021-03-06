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
        //TODO: Add comments
        return writeFormatted(geom, false); //using isSubElement to get a proper wktString
    }

    public String writeFormatted(Geometry geom, boolean isSubElement){
        String wktString = "";
        if (!isSubElement){ //dont write classnames if subElement
            wktString = geom.getClass().getSimpleName().toUpperCase(Locale.ENGLISH) + " "; //to get exactly "LINESTRING"
        }
        if (geom.isEmpty()){
            wktString = geom.getClass().getSimpleName().toUpperCase(Locale.ENGLISH) + " EMPTY";
        }
        else if (geom instanceof Point) {
            wktString += writePoint((Point) geom);
        }

        else if (geom instanceof LineString) {
            wktString += writeLineString((LineString) geom);
        }

        else if (geom instanceof Polygon) {
            wktString += writePolygon((Polygon) geom);
        }

        else if (geom instanceof MultiPoint) {
            wktString += writeMultiPoint((MultiPoint) geom);
        }

        else if (geom instanceof MultiLineString) {
            wktString += writeMultiLineString((MultiLineString) geom);
        }

        else if (geom instanceof MultiPolygon) {
            wktString += writeMultiPolygon((MultiPolygon) geom);
        }

        else if (geom instanceof GeometryCollection) {
            wktString += writeGeometryCollection((GeometryCollection) geom);
        }

        return wktString;
    }

    private String writePoint(Point p) {
        return "(" + decimalformat(p.getX()) + " " + decimalformat(p.getY()) + ")";
    }

    private String writeLineString(LineString ls) {
        String wktString = "(";
        for (int i = 0; i < ls.getNumCoords(); i++) {
            wktString += decimalformat(ls.getX(i)) + " " + decimalformat(ls.getY(i));
            if (i != ls.getNumCoords() - 1)
                wktString += ", ";
        }
        wktString += ")";
        return wktString;
    }

    private String writePolygon(Polygon pol) {
        // couldnt find clear info about expected wktString here. So just imitating wikipedia example.
        String wktString = "(" + writeFormatted(pol.getOuter(),true);
        for (int i = 0; i < pol.getNumHoles(); i++) {
            wktString += ", " + writeFormatted(pol.getHole(i), true);
        }
        wktString += ")";
        return wktString;
    }

    private String writeMultiPoint(MultiPoint mp) {
        String wktString = "(";
        for (int i=0; i<mp.size(); i++) {
            wktString += writeFormatted(mp.get(i), true);
            if (i != mp.size() - 1)
                wktString += ", ";
        }
        wktString += ")";
        return wktString;
    }

    private String writeMultiLineString(MultiLineString mls) {
        String wktString = "(";
        for (int i = 0; i<mls.size(); i++) {
            wktString +=  writeFormatted(mls.get(i), true);
            if (i != mls.size() - 1)
                wktString += ", ";
        }
        wktString += ")";
        return wktString;
    }

    private String writeMultiPolygon(MultiPolygon mpol) {
        String wktString = "(";
        for (int i = 0; i<mpol.size(); i++) {
            wktString +=  writeFormatted(mpol.get(i),true);
            if (i != mpol.size() - 1)
                wktString += ", ";
        }
        wktString += ")";
        return wktString;
    }

    private String writeGeometryCollection(GeometryCollection gc) {
        String wktString = "(";
        for (int i=0; i<gc.size(); i++) {
            wktString += writeFormatted(gc.get(i), false);
            if (i != gc.size() - 1)
                wktString += ", ";
        }
        wktString += ")";
        return wktString;
    }

    private String decimalformat(double d) {    //formatting doubles to get a proper wktString
        if (d == (long) d)
            return String.format("%d",(long)d);
        else
            return String.format("%s",d);
    }
}
