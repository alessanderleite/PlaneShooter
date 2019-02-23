package br.com.alessanderleite.planeshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

public class GameView extends View {

    Bitmap background;

    public GameView(Context context) {
        super(context);
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(background,0,0,null);

    }
}
