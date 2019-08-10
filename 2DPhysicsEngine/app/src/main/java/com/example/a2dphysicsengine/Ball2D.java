package com.example.a2dphysicsengine;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class Ball2D implements GameObject {
    private Point center;
    private float radius;
    private float vx;
    private float vy;
    private double angle;
    private final double RADIANS_TO_DEGREES = 57.295;
    private final double DEGREES_TO_RADIANS = 0.01745;
    private final double g = 980; // gravity is 980 assuming that 100px = 1 Meter

    public Ball2D() {
        // create a new ball in the screens center
        this.center = new Point(Resources.getSystem().getDisplayMetrics().widthPixels/2, Resources.getSystem().getDisplayMetrics().heightPixels/2);
        this.radius = 50;
        this.vx = 0;
        this.vy = 0;
        this.angle = 0;
    }

    public  Ball2D (float radius, int cx, int cy) {
        this.radius = radius;
        this.center = new Point(cx,cy);
        this.vx = 0;
        this.vy = 0;
        this.angle = 0;
    }


    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(center.x, center.y, radius, new Paint());
    }

    @Override
    public void update() {
        double dt = MainThread.getDeltaTime();
        int x = center.x;
        int y = center.y;
        if (center.y < Resources.getSystem().getDisplayMetrics().heightPixels - 200) {
            vy += g*dt;
        } else {
            vy = 0;
        }
        y += vy*dt;
        x += vx*dt;
        center.set(x,y);

    }

    public void addForce(Vector2 force) {
        System.out.println("true");
        double x = MainThread.getDeltaTime()*force.x;
        System.out.println(x);
        vx += x;
        vy += MainThread.getDeltaTime()*force.y;
    }
}
