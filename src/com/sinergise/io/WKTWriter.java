package com.sinergise.io;

import com.sinergise.geometry.Geometry;
import com.sinergise.geometry.GeometryCollection;
import com.sinergise.geometry.LineString;
import com.sinergise.geometry.Point;
import com.sinergise.geometry.MultiPoint;
import com.sinergise.geometry.MultiLineString;
import com.sinergise.geometry.Polygon;

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
		String output = "";
		Class geom_class = geom.getClass();
		output += geom_class.getSimpleName().toUpperCase() + " ";

		if (geom_class == Point.class) {
			Point p = (Point) geom;
			output += "(" + p.getX() + " " + p.getY() + ")";
		}

		else if (geom_class == LineString.class) {
			LineString ls = (LineString)geom;
			output += "(";
			for (int i = 0; i < ls.getNumCoords(); i++) {
				output += ls.getX(i) + " " + ls.getY(i);
				if (i != ls.getNumCoords() - 1)
					output += ", ";
			}
			output += ")";
		}

		else if (geom_class == Polygon.class)
		{
			Polygon pol = (Polygon) geom;
			// couldnt find clear info about expected output here. So just imitating wikipedia example.
			output += "(" + write(pol.getOuter());
			for (int i = 0; i < pol.getNumHoles(); i++) {
				output += ", " + write(pol.getHole(i));
			}
			output += ")";
		}

		else if (geom_class == MultiPoint.class) {
			MultiPoint mp = (MultiPoint) geom;
			output += "(";
			for (int i=0; i<mp.size(); i++) {
				output += write(mp.get(i));
				if (i != mp.size() - 1)
					output += ", ";
			}
			output += ")";
		}

		else if (geom_class == GeometryCollection.class) {
			GeometryCollection gc = (GeometryCollection) geom;
			output += "(";
			for (int i=0; i<gc.size(); i++) {
				output += write(gc.get(i));
				if (i != gc.size() - 1)
					output += ", ";
			}
			output += ")";
		}

		return output;
	}

	public static void main(String[] args){
		System.out.println(new WKTWriter().write(new LineString(new double[]{30, 10, 10, 30, 40, 40})));
		System.out.println(new WKTWriter().write(new MultiPoint(new Point[]{new Point(4, 6), new Point(5, 10)})));
		System.out.println(new WKTWriter().write(new GeometryCollection<Geometry>(new Geometry[]{new Point(4,6), new LineString(new double[] {4,6,7,10})})));
		System.out.println(new WKTWriter().write(new Polygon(new LineString(new double[] {35, 10, 45, 45, 15, 40, 10, 20, 35, 10}), new LineString[]{new LineString(new double[]{20, 30, 35, 35, 30, 20, 20, 30})})));

	}
}
