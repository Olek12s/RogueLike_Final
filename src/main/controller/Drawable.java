package main.controller;

import java.awt.*;

public interface Drawable
{
    int getDrawPriority();
    void draw(Graphics g2);
}
