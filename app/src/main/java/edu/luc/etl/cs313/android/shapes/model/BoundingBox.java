package edu.luc.etl.cs313.android.shapes.model;

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

		return null;
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
		return null;
	}
}
