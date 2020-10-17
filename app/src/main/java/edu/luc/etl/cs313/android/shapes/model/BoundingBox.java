package edu.luc.etl.cs313.android.shapes.model;
import java.util.List;

/**
 * A shape visitor for calculating the bounding box, that is, the smallest
 * rectangle containing the shape. The resulting bounding box is returned as a
 * rectangle at a specific location.
 */
public class BoundingBox implements Visitor<Location> {

	// TODO entirely your job (except onCircle)

	@Override
	public Location onCircle(final Circle c) {
		final int radius = c.getRadius();
		return new Location(-radius, -radius, new Rectangle(2 * radius, 2 * radius));
	}

	@Override
	public Location onFill(final Fill f) {
		Location location;
		Shape s = f.getShape();
		location = s.accept(this);
		return location;
	}

	@Override
	public Location onGroup(final Group g) {
		List<? extends Shape> s = g.getShapes();
		Location current = s.get(0).accept(this);
		int x1 = current.getX();
		int y1 = current.getY();
		int x2 = ((Rectangle)current.getShape()).getWidth()+x1;
		int y2 = ((Rectangle)current.getShape()).getHeight()+y1;

		int i = 0;
		while(i < s.size()) {
			current = s.get(i).accept(this);
			if(current.getX()<x1) x1 = current.getX();
			if(current.getY()<y1) y1 = current.getY();
			if(current.getX()+((Rectangle)current.getShape()).getWidth()>x2) {
				x2 = current.getX()+((Rectangle)current.getShape()).getWidth();
			}
			if(current.getY()+((Rectangle)current.getShape()).getHeight()>y2) {
				y2 = current.getY()+((Rectangle)current.getShape()).getHeight();
			}

			i+= 1;
		}
		return new Location(x1,y1, new Rectangle(x2-x1,y2-y1));
	}


	@Override
	public Location onLocation(final Location l) {
		Location location = l.getShape().accept(this);
		final int x = l.getX() + location.getX();
		final int y = l.getY() + location.getY();
		return new Location(x,y, location.getShape());
	}

	@Override
	public Location onRectangle(final Rectangle r) {
		int w = r.getWidth();
		int h = r.getHeight();
		return new Location(0,0, new Rectangle( w, h));
	}

	@Override
	public Location onStrokeColor(final StrokeColor c) {
		Location location;
		Shape s = c.getShape();
		location = s.accept(this);
		return location;
	}
	@Override
	public Location onOutline(final Outline o) {
		Location location;
		Shape s = o.getShape();
		location = s.accept(this);
		return location;
	}

	@Override
	public Location onPolygon(final Polygon s) {
		return onGroup(s);
		//We are putting this in to make it draw everything other than the polygon.
	}
}
