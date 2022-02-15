import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.program.GraphicsProgram;
import svu.csc213.Dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class Breakout extends GraphicsProgram {

    //Lives
    //Brick Hitpoints
    //Life Counter
    //Point System
    //Game Over
    //Brick Hitmarker
    //Way to Win / Multiple levels



    private Ball ball;
    private Paddle paddle;

    private JLabel lifeCounter;
    private int points;
    private JLabel pointCounter;

    private int numBricksInRow;

    private Color[] rowColors = {Color.RED, Color.RED, Color.ORANGE, Color.ORANGE, Color.GREEN, Color.GREEN, Color.CYAN, Color.CYAN, Color.BLUE, Color.BLUE};

    @Override
    public void init(){
        int lives = 3;

        lifeCounter = new JLabel("Lives: " + lives);
        add(lifeCounter, NORTH);

        pointCounter = new JLabel("Points: " + points);
        add(pointCounter, NORTH);

        numBricksInRow = getWidth() / (Brick.WIDTH + 5);

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < numBricksInRow; col++) {

                double brickX = 10 + col * (Brick.WIDTH + 5);
                double brickY = Brick.HEIGHT + row * (Brick.HEIGHT + 5);

                Brick brick = new Brick(brickX, brickY, rowColors[row], row);
                add(brick);

            }

        }

        ball = new Ball(getWidth()/2, 350, 10, this.getGCanvas());
        add(ball);

        paddle = new Paddle(230, 430, 50 ,10);
        add(paddle);

    }

    @Override
    public void run(){
        addMouseListeners();
        waitForClick();
        gameLoop();
    }

    @Override
    public void mouseMoved(MouseEvent me){
        // make sure that the paddle doesn't go offscreen
        if((me.getX() < getWidth() - paddle.getWidth()/2)&&(me.getX() > paddle.getWidth() / 2)){
            paddle.setLocation(me.getX() - paddle.getWidth()/2, paddle.getY());
        }
    }

    private void gameLoop(){
        int lives = 3;

        while (true){

            ball.handleMove();

            handleCollisions();

            if (ball.lost){
                handleLoss();
                lives = lives - 1;
                lifeCounter.setText("Lives: " + lives);
            }

            if (lives == 0){
                Dialog.showMessage("You Lose");
                System.exit(0);
            }

            pause(4);
        }


    }

    private void handleCollisions() {

        GObject obj = null;

        if (obj == null){
            obj = this.getElementAt(ball.getX() + ball.getWidth(), ball.getY());
        }

        if (obj == null){
            obj = this.getElementAt(ball.getX() - ball.getWidth(), ball.getY());
        }

        if (obj == null){
            obj = this.getElementAt(ball.getX(), ball.getY() + ball.getHeight());
        }

        if (obj == null){
            obj = this.getElementAt(ball.getX() + ball.getWidth(), ball.getY() + ball.getHeight());
        }

        if (obj != null){

            if (obj instanceof Paddle){

                if (ball.getX() < (paddle.getX()) + paddle.getWidth() * .2){
                    //left edge of the paddle
                    ball.bounceLeft();
                }else if (ball.getX() > paddle.getX() + paddle.getWidth() * .8){
                    ball.bounceRight();
                }else{
                    //middle of the paddle
                    ball.bounce();
                }

            }

            if (obj instanceof Brick){
                ball.bounce();
                if(((Brick) obj).hitPoints == 1){
                    this.remove(obj);
                } else {
                    ((Brick) obj).hitPoints = ((Brick) obj).hitPoints - 1;
                    ((Brick) obj).setFillColor(((Brick) obj).getFillColor().darker());
                }

                points = points + 1;
                pointCounter.setText("points: " + points);

            }


        }

    }

    private void handleLoss() {
        ball.lost = false;
        reset();
    }

    private void reset() {
        paddle.setLocation( 230 ,430);
        ball.setLocation(getWidth()/2, 350);
        waitForClick();
    }


    public static void main(String[] args) {
        new Breakout().start();
    }

}
