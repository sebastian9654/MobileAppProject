package com.example.project1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private Obstacle obstacle;
    private Paint paint;
    private int broccoliX, broccoliY;
    private boolean isJumping;
    public static int screenWidth;
    public static int screenHeight;
    private boolean running;
    private Bitmap broccoliBitmap; // Bitmap for the broccoli image
    private int score;
    private static final float GRAVITY = 0.8f;
    private static final float JUMP_STRENGTH = -15.0f;
    private float broccoliVelocity = 0;
    private boolean gameOver = false;
    private Context context; // Add a reference to the Context
    private final int MINIMUM_SCORE = 100;
    public GameView(Context context) {
        super(context);
        this.context = context; // Initialize the context
        getHolder().addCallback(this);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(50);

        // Load the broccoli image from resources
        broccoliBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.broccoli);

        // Set initial position and speed for the broccoli
        broccoliX = 100;
        broccoliY = getHeight() / 2;
        score = 0; //initialize score
    }

    private void drawGame(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#58e058")); // Light blue background
        if (gameOver) {
            paint.setTextSize(100);
            paint.setColor(Color.BLACK);
            canvas.drawText("Game Over", getWidth() / 4, getHeight() / 2, paint);
            paint.setTextSize(50);
            canvas.drawText("Score: " + score, getWidth() / 3, getHeight() / 2 + 100, paint);
        } else {
            Rect destRect = new Rect(broccoliX, broccoliY, broccoliX + 100, broccoliY + 100);
            canvas.drawBitmap(broccoliBitmap, null, destRect, paint);
            canvas.drawText("Score: " + score, 50, 90, paint);

            // Draw obstacle
            obstacle.draw(canvas);
            paint.setTextSize(80);
            paint.setColor(Color.BLACK);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        running = true;  // Start the game loop

        // Set the screen dimensions
        screenWidth = getWidth();
        screenHeight = getHeight();

        // Initialize the obstacle with the correct width inside surfaceCreated
        obstacle = new Obstacle(screenWidth, screenWidth, screenHeight, 100, 150);

        new Thread(() -> {
            while (running) {
                Canvas canvas = holder.lockCanvas(); // Obtain Canvas from SurfaceHolder
                if (canvas != null) {
                    drawGame(canvas);  // Draw on the obtained canvas
                    holder.unlockCanvasAndPost(canvas); // Unlock the canvas and post the result
                }

                update();
                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        running = false;  // Stop the game loop
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // Touch event handling
                if (!isJumping) {
                    jump();
                }
                break;
        }
        return true;
    }

    public void update() {
        // Update game state
        if (!gameOver) {
            if (isJumping) {
                broccoliVelocity += JUMP_STRENGTH;
                isJumping = false;  // Only jump once per touch
            }

            broccoliVelocity += GRAVITY;
            broccoliY += broccoliVelocity;

            // Limit how far down the broccoli can fall
            if (broccoliY >= getHeight() - 100) {
                broccoliY = getHeight() - 100;
                broccoliVelocity = 0;  // Reset velocity when reaching the bottom
            }

            // Check for scoring conditions
            if (broccoliX > obstacle.getX() + obstacle.getWidth()) {
                score++;
            }

            // Update obstacle position
            obstacle.update();

            // Check for collision
            if (isCollision()) {
                handleCollision();
                if (score >= MINIMUM_SCORE) {
                    startCongratsActivity();
                }
                else {
                    restartThis();
                }
            }
        }
    }

    private void handleCollision() {
        gameOver = true;
    }

    private void jump() {
        isJumping = true;
    }

    private boolean isCollision() {
        return broccoliX + 100 > obstacle.getX() && broccoliX < obstacle.getX() + obstacle.getWidth()
                && broccoliY + 100 > obstacle.getY() && broccoliY < obstacle.getY() + obstacle.getHeight();
    }

    private void startCongratsActivity() {
        // Create an Intent to start the next level activity
        System.out.println("startnextlevel call");
        Intent intent = new Intent(context, CongratulationsActivity.class);
        context.startActivity(intent);
        gameOver = true;
        ((Activity) context).finish();
    }

    private void restartThis() {
        Intent intent = new Intent(context, RestartGameActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("nextLevelClass", BroccoliRunActivity.class);
        context.startActivity(intent);
        ((Activity) context).finish();
    }

    public void resume() {
    }

    public void pause() {
    }
}