package com.example.project1;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Obstacle {
    private int x, y, width, height;
    private Paint paint;

    public Obstacle(int initialX, int y, int width, int height) {
        this.x = initialX;
        this.y = y;
        this.width = width;
        this.height = height;

        paint = new Paint();
        paint.setColor(Color.BLUE);
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(x, y, x + width, y + height, paint);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    public void update() {
        x -= 5; // Move the obstacle towards the left

        // Check if the obstacle has moved off the left edge
        if (x + width < 0) {
            // Reset obstacle position to the right edge
            x = getWidth() + 900;
        }
    }

}
