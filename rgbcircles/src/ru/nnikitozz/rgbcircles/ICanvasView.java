package ru.nnikitozz.rgbcircles;

/**
 * Created by Jesus on 28.11.2016.
 */
public interface ICanvasView {
    void drawCircle(SimpleCircle circle);

    void redraw();

    void showMessage(String s);
}
