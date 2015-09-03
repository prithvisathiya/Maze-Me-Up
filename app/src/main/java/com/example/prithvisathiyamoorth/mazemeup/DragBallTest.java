package com.example.prithvisathiyamoorth.mazemeup;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.Display;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by prithvisathiyamoorth on 9/1/15.
 */
public class DragBallTest extends View {

    private Paint paint = new Paint();
    private int ballRadius = 20;
    private float ballX, ballY;
    private int width;
    private Context mContext;


    public DragBallTest(Context context) {
        super(context);

        int[] background = {R.drawable.black_pattern, R.drawable.dark_black_pattern, R.drawable.pink_pattern, R.drawable.wood2};
        int Min = 0, Max = background.length;
        int randNum = Min + (int)(Math.random() * ((Max - Min)));
        setBackgroundResource(background[randNum]);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        ballX = size.x/2;ballY = size.y - 500;
        Toast.makeText(context,"Width: " + ballX + " Height: " + ballY, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.BLACK);
        //canvas.drawRect(100, 300, 300, 500, paint);


        paint.setColor(Color.GREEN);
        paint.setTextSize(40);
        canvas.drawText("Ball at (" + ballX + "," + ballY +")", 10, 40, paint);

        paint.setColor(Color.RED);
        canvas.drawCircle(ballX, ballY, ballRadius, paint);
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                ballX = event.getX();
                ballY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
        }
        return (true);
    }

//    @Override
//    public boolean onDragEvent(DragEvent event) {
//        ballX = event.getX();
//        ballY = event.getY();
//        return super.onDragEvent(event);
//    }
}
