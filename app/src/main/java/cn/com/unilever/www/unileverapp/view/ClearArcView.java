package cn.com.unilever.www.unileverapp.view;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

//主页面的加速球
public class ClearArcView extends View{
	private RectF oval;
	private Paint paint;
	private float startAngle = -90;//开始的角度
	private float sweepAngle = 180;//扫过的角度
	private int width;
	private int height;
	private int state;//表示扇形的两种状态：0——后退，1——前进
	private boolean isRun = true;//是否开始timer任务

	public ClearArcView(Context context) {
		super(context);
		paint = new Paint();
	}
	public ClearArcView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint = new Paint();
	}
	public ClearArcView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		paint = new Paint();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		width = MeasureSpec.getSize(widthMeasureSpec);
		height = MeasureSpec.getSize(heightMeasureSpec);
		oval = new RectF(0, 0, width, height);
		setMeasuredDimension(width, height);//返回测量后的结果
	}

	public void setAngle(final int angle){
		if (!isRun) {
			return;
		}
		isRun = false;
		state = 0;
		final Timer timer = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				switch (state) {
					case 0:
						sweepAngle -= 10;
						if (sweepAngle <= 0) {
							state = 1;
						}
						break;

					case 1:
						sweepAngle += 10;
						if (sweepAngle >= angle) {
							timer.cancel();
							isRun = true;
						}
						break;
				}
				postInvalidate();//通知重绘
			}
		};
		timer.schedule(task, 20, 5);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		paint.setColor(0xFF6699FF);
		paint.setAntiAlias(true);//消除锯齿
		canvas.drawArc(oval, startAngle, sweepAngle, true, paint);
	}
}
