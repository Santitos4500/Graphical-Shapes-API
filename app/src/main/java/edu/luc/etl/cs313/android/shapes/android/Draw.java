package edu.luc.etl.cs313.android.shapes.android;
// List imported for OnPolygon
import java.util.List;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import edu.luc.etl.cs313.android.shapes.model.*;
import java.util.Iterator;
/**
 * A Visitor for drawing a shape to an Android canvas.
 */
public class Draw implements Visitor<Void> {

	// TODO entirely your job (except onCircle)

	private final Canvas canvas;

	private final Paint paint;

	public Draw(final Canvas canvas, final Paint paint) {
		this.canvas = canvas;
		this.paint = paint;
		paint.setStyle(Style.STROKE);
	}

	@Override
	public Void onCircle(final Circle c) {
		canvas.drawCircle(0, 0, c.getRadius(), paint);
		return null;
	}

	@Override
	public Void onStrokeColor(final StrokeColor c) {
		final int newColor = paint.getColor();
		paint.setColor(c.getColor());
		c.getShape().accept(this);
		paint.setColor(newColor);
		return null;
	}

	@Override
	public Void onFill(final Fill f) {

		paint.setStyle(Style.FILL_AND_STROKE);
		f.getShape().accept(this);
		paint.setStyle(Style.STROKE);
		return null;
	}

	@Override
	public Void onGroup(final Group g) {
		final Iterator<? extends Shape > shape = g.getShapes().iterator();
				while (shape.hasNext()) {
					shape.next().accept(this);
				}
		return null;

	}

	@Override
	public Void onLocation(final Location l) {
		canvas.translate(l.getX(), l.getY());
		l.getShape().accept(this);
		canvas.translate(-l.getX(), -l.getY());
		return null;
	}

	@Override
	public Void onRectangle(final Rectangle r) {
//		https://developer.android.com/reference/android/graphics/Rect
		canvas.drawRect(0, 0, r.getWidth(), r.getHeight(), paint);
		return null;
	}

	@Override
	public Void onOutline(Outline o) {
        final Style ol = paint.getStyle();
        paint.setStyle(Style.STROKE);
        o.getShape().accept(this);
        paint.setStyle(ol);
		return null;
	}

	@Override
	public Void onPolygon(final Polygon s) {
        List<? extends Point> points = s.getPoints();

        final float[] pts = {
        		//TO DO
		};
//		https://developer.android.com/reference/android/graphics/Canvas#drawLines(float%5B%5D,%2520int,%2520int,%2520android.graphics.Paint)
		canvas.drawLines(pts, paint);
		return null;
	}
}
