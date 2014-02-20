package org.ginryan.drawarcs;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class ArcProgressCircle extends View {
	private int mArcColor = Color.RED;
	private float mArcRad = 0;
	private Drawable mArcDrawable;
	private Paint mPaint;

	public ArcProgressCircle(Context context) {
		super(context);
		init(null, 0);
	}

	public ArcProgressCircle(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0);
	}

	public ArcProgressCircle(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs, defStyle);
	}

	RectF rectf = null;

	private void init(AttributeSet attrs, int defStyle) {
		final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ArcProgressCircle, defStyle, 0);

		mArcColor = a.getColor(R.styleable.ArcProgressCircle_arcColor, mArcColor);
		mArcRad = a.getFloat(R.styleable.ArcProgressCircle_arcRad, 90);
		if (a.hasValue(R.styleable.ArcProgressCircle_arcDrawable)) {
			mArcDrawable = a.getDrawable(R.styleable.ArcProgressCircle_arcDrawable);
			mArcDrawable.setCallback(this);
		}

		a.recycle();

		mPaint = new Paint();
		mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
		mPaint.setTextAlign(Paint.Align.LEFT);
		rectf = new RectF(0, 0, 0, 0);
		invalidateTextPaintAndMeasurements();
	}

	private void invalidateTextPaintAndMeasurements() {
		try {
			mPaint.setColor(mArcColor);
			invalidate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		int paddingLeft = getPaddingLeft();
		int paddingTop = getPaddingTop();
		int paddingRight = getPaddingRight();
		int paddingBottom = getPaddingBottom();

		int contentWidth = getWidth();
		int contentHeight = getHeight();
		rectf.set(paddingLeft, paddingTop, contentWidth - paddingRight, contentHeight - paddingBottom);
		canvas.drawArc(rectf, -90, mArcRad, true, mPaint);

	}

	public void setPercentageProgress(float percent) {
		setRad(percent / 100 * 359.999f);
	}

	public void setRad(float rad) {
		mArcRad = rad - 0.0001f;
		mArcRad %= 360;
		Log.i("arc", "Rad:" + rad);
		invalidate();
	}
}
