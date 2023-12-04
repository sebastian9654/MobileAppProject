package com.example.project1;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

public class Obstacle {
    private int x, y, width, height;
    private Paint paint;
    private Random random;

    public Obstacle(int initialX, int screenWidth, int screenHeight, int width, int height) {
        this.x = initialX;
        this.width = width;
        this.height = height;

        paint = new Paint();
        paint.setColor(Color.BLUE);

        random = new Random();
        resetPosition(screenWidth, screenHeight);
    }

    private void resetPosition(int screenWidth, int screenHeight) {
        // Calculate Y position to ensure the obstacle is within the screen
        y = random.nextInt(screenHeight - height);
        // Place the obstacle just outside the right edge of the screen
        x = screenWidth + random.nextInt(200);
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
            // Reset obstacle position to the right edge with a new Y position
            resetPosition(GameView.screenWidth, GameView.screenHeight);
        }
    }
}
