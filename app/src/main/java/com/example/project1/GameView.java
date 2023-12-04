package com.example.project1;// Import necessary packages
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

    private int broccoliX, broccoliY, broccoliSpeed;
    private boolean isJumping;

    private boolean running;  // Flag to control the game loop

    private Bitmap broccoliBitmap; // Bitmap for the broccoli image
    private int score; //store score
    private static final float GRAVITY = 0.8f;  // You can adjust this value based on your desired gravity strength
    private static final float JUMP_STRENGTH = -5.0f;  // You can adjust this value based on your desired jump strength
    private float broccoliVelocity = 0;
    private boolean gameOver = false;
    private Context context; // Add a reference to the Context

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
        broccoliSpeed = 8;
        score = 0; //initialize score
    }

    private void drawGame(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#ADD8E6")); // Light blue background
        if (gameOver) {
            paint.setTextSize(100);
            paint.setColor(Color.BLACK);
            canvas.drawText("Game Over", getWidth() / 4, getHeight() / 2, paint);
            paint.setTextSize(50);
            canvas.drawText("Score: " + score, getWidth() / 3, getHeight() / 2 + 100, paint);
        } else {
            // Define the destination rectangle for the broccoli
            Rect destRect = new Rect(broccoliX, broccoliY, broccoliX + 100, broccoliY + 100); // Adjust size as needed

            // Draw broccoli with the destination rectangle
            canvas.drawBitmap(broccoliBitmap, null, destRect, paint);
            // Draw the score
            canvas.drawText("Score: " + score, 50, 50, paint);

            // Draw obstacle
            obstacle.draw(canvas);
            paint.setTextSize(80);  // Adjust the size as needed
            paint.setColor(Color.BLACK);  // Adjust the color as needed
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        running = true;  // Start the game loop

        // Initialize the obstacle with the correct width inside surfaceCreated
        obstacle = new Obstacle(getWidth(), getHeight() - 200, 100, 150);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {
                    Canvas canvas = holder.lockCanvas(); // Obtain Canvas from SurfaceHolder
                    if (canvas != null) {
                        drawGame(canvas);  // Draw on the obtained canvas
                        holder.unlockCanvasAndPost(canvas); // Unlock the canvas and post the result
                    }

                    update();
                    try {
                        Thread.sleep(16); // Adjust as needed for the desired frame rate
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Handle surface changes
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
            // Update broccoli position
            if (isJumping) {
                broccoliVelocity += JUMP_STRENGTH;
                isJumping = false;  // Only jump once per touch
            }

            broccoliVelocity += GRAVITY;
            broccoliY += broccoliVelocity;

            // Limit how far down the broccoli can fall (adjust as needed)
            if (broccoliY >= getHeight() - 100) {
                broccoliY = getHeight() - 100;
                broccoliVelocity = 0;  // Reset velocity when reaching the bottom
            }

            // Check for scoring conditions (customize based on your game logic)
            if (broccoliX > obstacle.getX() + obstacle.getWidth()) {
                // Broccoli has passed the obstacle, increase the score
                score++;
            }

            // Update obstacle position
            obstacle.update();

            // Check for collision
            if (isCollision()) {
                // Handle collision (e.g., end the game)
                handleCollision();
            }

            // Check if the score is 100
            if (score == 20) {
                showLevelCompleteMessage();
                startNextLevelActivity();
            }
        }
    }

    private void handleCollision() {
        // Set the game over flag to true
        gameOver = true;
    }

    private void jump() {
        isJumping = true;
    }

    private boolean isCollision() {
        return broccoliX + 100 > obstacle.getX() && broccoliX < obstacle.getX() + obstacle.getWidth()
                && broccoliY + 100 > obstacle.getY() && broccoliY < obstacle.getY() + obstacle.getHeight();
    }

    private void showLevelCompleteMessage() {
        // Display a toast or other message indicating level completion
        // You can use Toast or any other UI element for this purpose
        // For example:
        // Toast.makeText(context, "Congratulations! You beat this level!", Toast.LENGTH_SHORT).show();
    }


    private void startNextLevelActivity() {
        // Create an Intent to start the next level activity
        System.out.println("startnextlevel call");
        Intent intent = new Intent(context, FoodMatch.class);
        context.startActivity(intent);
        gameOver = true;
        ((Activity) context).finish();
    }

    public void resume() {
        // Resume game loop logic, if necessary
    }

    public void pause() {
        // Pause game loop logic, if necessary
    }
}