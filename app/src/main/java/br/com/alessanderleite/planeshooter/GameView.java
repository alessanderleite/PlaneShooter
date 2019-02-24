package br.com.alessanderleite.planeshooter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class GameView extends View {

    Bitmap background, tank;
    Rect rect;
    static int dWidth, dHeight;
    ArrayList<Plane> planes, planes2;
    Handler handler;
    Runnable runnable;
    final long UPDATE_MILLIS = 30;
    int tankWidth, tankHeight;

    public GameView(Context context) {
        super(context);
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        tank = BitmapFactory.decodeResource(getResources(), R.drawable.tank);
        Display display = ((Activity)getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight = size.y;
        rect = new Rect(0,0,dWidth,dHeight);
        planes = new ArrayList<>();
        planes2 = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Plane plane = new Plane(context);
            planes.add(plane);
            Plane2 plane2 = new Plane2(context);
            planes2.add(plane2);
        }

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
        tankWidth = tank.getWidth();
        tankHeight = tank.getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(background,0,0,null);
        canvas.drawBitmap(background,null,rect,null);
        for (int i = 0; i < planes.size(); i++) {
            canvas.drawBitmap(planes.get(i).getBitmap(),planes.get(i).planeX, planes.get(i).planeY, null);
            planes.get(i).planeFrame++;
            if (planes.get(i).planeFrame > 14) {
                planes.get(i).planeFrame = 0;
            }
            planes.get(i).planeX -= planes.get(i).velocity;
            if (planes.get(i).planeX < -planes.get(i).getWidth()) {
                planes.get(i).resetPosition();
            }
            canvas.drawBitmap(planes2.get(i).getBitmap(), planes2.get(i).planeX, planes2.get(i).planeY, null);
            planes2.get(i).planeFrame++;
            if (planes2.get(i).planeFrame > 9) {
                planes2.get(i).planeFrame = 0;
            }
            planes2.get(i).planeX += planes2.get(i).velocity;
            if (planes2.get(i).planeX > (dWidth + planes2.get(i).getWidth())) {
                planes2.get(i).resetPosition();
            }
        }
        canvas.drawBitmap(tank,(dWidth/2 - tankWidth/2), dHeight - tankHeight, null);
        handler.postDelayed(runnable, UPDATE_MILLIS);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            if (touchX >= (dWidth/2 - tankWidth/2) && touchX <= (dWidth/2 + tankWidth/2) && touchY >= (dHeight - tankHeight)) {
                Log.i("TANK", "is tapped");
            }
        }

        return true;
    }
}
