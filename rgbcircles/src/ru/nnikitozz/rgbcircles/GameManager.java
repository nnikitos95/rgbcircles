package ru.nnikitozz.rgbcircles;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

/**
 * Created by Jesus on 28.11.2016.
 */
public class GameManager {
    public static final int MAX_CIRCLES = 10;
    private MainCircle mainCircle;
    private ArrayList<EnemyCircle> circles;
    private CanvasView canvasView;
    private static int width;
    private static int height;


    public GameManager(CanvasView canvasView, int w, int h) {
        this.canvasView = canvasView;
        width = w;
        height = h;
        initMainCircle();
        initEnemyCircle();
    }

    private void initEnemyCircle() {
        SimpleCircle mainCircleArea = mainCircle.getCircleArea();
        circles = new ArrayList<EnemyCircle>();
        for (int i = 0; i < MAX_CIRCLES; i++) {
            EnemyCircle circle;

            do {
                circle = EnemyCircle.getRandomCircle();
            }while (circle.isIntersect(mainCircleArea));
            circles.add(circle);

        }
        calcelateAndSetCircleColor();
    }

    private void calcelateAndSetCircleColor() {
        for (EnemyCircle circle:circles){
            circle.setEnemyOrFoodColorDepenson(mainCircle);
        }
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }


    private void initMainCircle() {
        mainCircle = new MainCircle(width/5,height/4);
    }

    public void onDraw(Canvas canvas) {
        canvasView.drawCircle(mainCircle);
        for(EnemyCircle circle:circles){
            canvasView.drawCircle(circle);
        }
    }

    public void onTouchEvent(int x, int y) {

        mainCircle.moveMainCircleWhenTouchAt(x,y);
        checkCollusion();
        moveCircles();
    }

    private void checkCollusion() {
        SimpleCircle circleForDel = null;
        for (EnemyCircle circle : circles) {
            if (mainCircle.isIntersect(circle)){
                if (circle.isSmallerThan(mainCircle)){
                    mainCircle.growRadius(circle);
                    circleForDel = circle;
                    calcelateAndSetCircleColor();
                    break;
                }else {
                    gameEnd("YOU LOSE");
                    return;
                }

            }
        }
        if (circleForDel!=null){
            circles.remove(circleForDel);
        }
        if (circles.isEmpty()){
            gameEnd("YOU WIN");
        }
    }

    private void gameEnd(String s) {
        canvasView.showMessage(s);
        mainCircle.initRadius();
        initEnemyCircle();
        canvasView.redraw();
    }

    private void moveCircles() {
        for (EnemyCircle circle : circles) {
            circle.moveOneStep();
        }
    }
}
