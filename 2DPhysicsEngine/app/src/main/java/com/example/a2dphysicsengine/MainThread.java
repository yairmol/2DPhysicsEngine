package com.example.a2dphysicsengine;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean running;
    private double averageFPS;
    private int targetFPS = 60;
    private static float deltaTime = 0; // the time between this frame and the last frame represented in nano seconds
    public static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, GameView gameView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    public void setRunning(boolean isRunning){
        this.running = isRunning;
    }

    // returns the time between the last frame and this frame in seconds
    public static double getDeltaTime() {
        return deltaTime / 1000000000.0;
    }

    @Override
    public void run() {
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        long targetTime = 1000 / targetFPS;

        while(running) {
            startTime = System.nanoTime();
            canvas= null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gameView.update();
                    this.gameView.draw(canvas);
                }
            } catch (Exception e) {}
            finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime -timeMillis;
            try {
                this.sleep(waitTime);
            } catch (Exception e) {}

            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if (frameCount == targetFPS) {
                averageFPS = 1000 / ((totalTime / frameCount) / 1000000); // 1000 / (the average time it took for one frame)
                totalTime = 0;
                frameCount = 0;
                System.out.println(averageFPS);
            }
            deltaTime = System.nanoTime() - startTime;
        }
    }
}
