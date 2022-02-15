import acm.graphics.GRect;

import java.awt.*;
public class Brick extends GRect {

    public static final int WIDTH = 44;
    public static final int HEIGHT = 20;

    public int hitPoints = 1;

        public Brick(double x, double y, Color color, int row){
            super(x, y, WIDTH, HEIGHT);
            this.setFilled(true);
            this.setFillColor(color);

            if(row == 0 || row == 1){
                hitPoints = hitPoints + 4;
            }
            if(row == 2 || row == 3){
                hitPoints = hitPoints + 3;
            }
            if(row == 4 || row == 5){
                hitPoints = hitPoints + 2;
            }
            if(row == 6 || row == 7){
                hitPoints = hitPoints + 1;
            }

        }

    }
