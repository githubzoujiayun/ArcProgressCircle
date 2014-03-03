package org.ginryan.drawarcs;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;
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
		mArcRad = a.getFloat(R.styleable.ArcProgressCircle_arcRad, 0);
		if (a.hasValue(R.styleable.ArcProgressCircle_arcDrawable)) {
			mArcDrawable = a.getDrawable(R.styleable.ArcProgressCircle_arcDrawable);
			mArcDrawable.setCallback(this);
		}

		a.recycle();
		mPaint = new Paint();
		mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
		mPaint.setAntiAlias(true);
		mPaint.setTextAlign(Paint.Align.LEFT);
		rectf = new RectF(0, 0, 0, 0);
		invalidateTextPaintAndMeasurements();
		bitmapbg = (BitmapDrawable) getResources().getDrawable(R.drawable.image27);
		canvasBackg = bitmapbg.getBitmap();
	}

	private void invalidateTextPaintAndMeasurements() {
		try {
			mPaint.setColor(mArcColor);
			invalidate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	BitmapDrawable bitmapbg;
	private Bitmap canvasBackg;
	Path path = new Path();

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int paddingLeft = getPaddingLeft();
		int paddingTop = getPaddingTop();
		int paddingRight = getPaddingRight();
		int paddingBottom = getPaddingBottom();

		int contentWidth = getWidth();
		int contentHeight = getHeight();
		getSectorClip(canvas, contentWidth / 2, contentHeight / 2, (contentHeight - paddingLeft - paddingRight) / 2, 0, mArcRad);
		canvas.drawBitmap(canvasBackg, 0, 0, mPaint);
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

	/**
	 * 
	 * 返回一个扇形的剪裁区
	 * 
	 * @param canvas
	 *            //画笔
	 * @param center_X
	 *            //圆心X坐标
	 * @param center_Y
	 *            //圆心Y坐标
	 * @param r
	 *            //半径
	 * @param startAngle
	 *            //起始角度
	 * @param sweepAngle
	 *            //终点角度
	 */
	private void getSectorClip(Canvas canvas, float center_X, float center_Y, float r, float startAngle, float sweepAngle) {
		Path path = new Path();
		// 下面是获得一个三角形的剪裁区
		path.moveTo(center_X, center_Y); // 圆心
		path.lineTo((float) (center_X + r * Math.cos(startAngle * Math.PI / 180)), // 起始点角度在圆上对应的横坐标
				(float) (center_Y + r * Math.sin(startAngle * Math.PI / 180))); // 起始点角度在圆上对应的纵坐标
		path.lineTo((float) (center_X + r * Math.cos(sweepAngle * Math.PI / 180)), // 终点角度在圆上对应的横坐标
				(float) (center_Y + r * Math.sin(sweepAngle * Math.PI / 180))); // 终点点角度在圆上对应的纵坐标
		path.close();
		// //设置一个正方形，内切圆
		RectF rectF = new RectF(center_X - r, center_Y - r, center_X + r, center_Y + r);
		// 下面是获得弧形剪裁区的方法

		path.addArc(rectF, startAngle, sweepAngle - startAngle);
		canvas.clipPath(path, Region.Op.REPLACE);

	}
}
