package com.example.prithvisathiyamoorth.mazemeup.Levels;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.shapes.OvalShape;
import android.view.Display;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.prithvisathiyamoorth.mazemeup.AlarmDialog;
import com.example.prithvisathiyamoorth.mazemeup.R;



public class EasyLevel extends View {

    private Paint paint = new Paint();
    private int pathPaint, ballPaint, textPaint;
    private boolean move = false;
    private Context mContext;
    private Canvas canvasNew;
    private int canvasHeight, canvasWidth;



    //SHAPES
    private Rect rect, rect2, conRect, goal;
    private Rect ballRect;
    private boolean firstDrawBallRect = true;
    private int ballRectX, ballRectY;
    private int dim=220;
    private float initX, initY;

    public EasyLevel(Context context) {
        super(context);

        mContext = context;
        //set all possible themes
        //APPROVED - black, dark-black, wood, purple feather
        int[] background = {R.drawable.black_pattern, R.drawable.dark_black_pattern, R.drawable.blue_gradient_pattern, R.drawable.wood2, R.drawable.marron_pattern, R.drawable.purple_feather_pattern};
        int[] pathColor = {Color.LTGRAY, Color.GRAY, Color.BLUE, Color.WHITE, Color.MAGENTA, Color.DKGRAY};
        int[] ballColor = {Color.RED, Color.WHITE, Color.WHITE, Color.GREEN, Color.BLACK, Color.RED};
        int[] textColor = {Color.GREEN,Color.GREEN,Color.GREEN,Color.GREEN, Color.GREEN, Color.GREEN};
        //choosing the theme
        int Min = 0, Max = background.length;
        int randNum = Min + (int)(Math.random() * ((Max - Min)));
        setBackgroundResource(background[randNum]);
        pathPaint = pathColor[randNum]; ballPaint = ballColor[randNum]; textPaint = textColor[randNum];

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvasNew = canvas;

        //one time function
        if (firstDrawBallRect) {
            canvasHeight = canvas.getHeight();canvasWidth = canvas.getWidth();
            //dim = canvasWidth/5;
            ballRectX = canvasWidth/2-dim/2; ballRectY = canvasHeight - dim;
            ballRect = new Rect(canvasWidth/2 - dim/2, canvasHeight - dim, canvasWidth/2+dim/2, canvasHeight);
            firstDrawBallRect = false;
        }

        //drawing the paths and connectors
        paint.setColor(pathPaint);
        rect = new Rect(canvasWidth/2 - dim/2 - 10, canvasHeight/2, canvasWidth/2 + dim/2 + 10, canvasHeight);
        canvas.drawRect(rect, paint);

        conRect = new Rect(rect.left, rect.top - dim, rect.right, rect.top + dim);
        canvas.drawRect(conRect, paint);

        rect2 = new Rect(conRect.left, rect.top - rect.width(), canvasWidth - 200, rect.top);
        canvas.drawRect(rect2,paint);


        //drawing the goal
        paint.setColor(Color.CYAN);
        goal = new Rect(rect2.right - 110, rect2.top+10, rect2.right - 10, rect2.bottom - 10);
        canvas.drawRect(goal, paint);

        //drawing the ball
        paint.setColor(ballPaint);
        canvas.drawRect(ballRect, paint);

        //drawing the text
        paint.setColor(textPaint);
        paint.setTextSize(40);
        canvas.drawText("Ball at (" + ballRect.exactCenterX() + "," + ballRect.exactCenterY() + ")", 30, 50, paint);

        //paint.setColor();

        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float newX = event.getX(), newY = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (ballRect.contains((int)newX, (int)newY)) {
                    initX = newX;
                    initY = newY;
                    move = true;
                }
                else {
                    move = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (move) {
                    ballRect.offset((int) (newX - initX), (int) (newY - initY));
                    initX = newX;initY = newY;
                    if ((!rect.contains(ballRect) && !rect2.contains(ballRect)) && !conRect.contains(ballRect)){
                        move = false;
                        ballRect.offsetTo(ballRectX, ballRectY);
                        Toast.makeText(mContext, "out of bounds", Toast.LENGTH_SHORT).show();
                    }
                    else if (ballRect.contains(goal.centerX(), goal.centerY())) {
                        ballRect.offset(-30, 0);
                        //goal.setEmpty();
                        Toast.makeText(mContext, "Congratulations\n Get UP!", Toast.LENGTH_SHORT).show();
                        AlarmDialog.vibrator.cancel();
                        ((Activity) getContext()).finish();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                ballRect.offsetTo(ballRectX, ballRectY);
                break;

        }
        return (true);
    }
}
