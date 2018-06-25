package com.sinergise.io;

import com.sinergise.geometry.Geometry;
import com.sinergise.geometry.GeometryCollection;
import com.sinergise.geometry.LineString;
import com.sinergise.geometry.Point;

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
		output += geom_class.getSimpleName();

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


		System.out.println(output);
		return output;
		//return write(new GeometryCollection<Geometry>(new Geometry[]{new Point(4,6), new LineString(new double[] {4,6,7,10})}));
	}

	public static void main(String[] args){
		new WKTWriter().write(new LineString(new double[]{30, 10, 10, 30, 40, 40}));
	}
}
