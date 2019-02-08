package Game.Entities.Dynamics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import Game.GameStates.MapState;
import Game.GameStates.PauseState;
import Game.GameStates.State;
import Game.World.InvisibleWalls;
import Game.World.WorldManager;
import Main.GameSetUp;
import Main.Handler;
import java.awt.event.KeyEvent;


public class Player extends BaseDynamicEntity {

    Rectangle player;



    public Player(Handler handler, int xPosition, int yPosition) {
        super(handler, yPosition, yPosition);

        this.xPosition = xPosition;
        this.yPosition = yPosition;

        player = new Rectangle();
    }

    @Override
    public void tick() {
        super.tick();
        UpdateNextMove();
        if(handler.getKeyManager().runbutt){
            speed = 2;
        }else{
            speed = 8;
        }
        PlayerInput();
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)) {
            PauseState.lastState = State.getState();
            State.setState(handler.getGame().pauseState);
        }
    }



    @Override
    public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;

        player = new Rectangle((int)xPosition, (int)yPosition, 25, 25);

        g2.setColor(Color.RED);
        g2.draw(player);

        if(GameSetUp.DEBUGMODE){
            g2.fill(nextArea);
        }
    }

    private void UpdateNextMove() {
        switch (facing){
            case "Down" :
                nextArea = new Rectangle((int)xPosition, (int)yPosition-speed, 25, 25);

                break;
            case "Up" :
                nextArea = new Rectangle((int)xPosition, (int)yPosition+speed, 25, 25);

                break;
            case "Right" :
                nextArea = new Rectangle((int)xPosition-speed, (int)yPosition, 25, 25);

                break;
            case "Left" :
                nextArea = new Rectangle((int)xPosition+speed, (int)yPosition, 25, 25);

                break;
        }
    }

    private void PlayerInput() {
        boolean canMove = true;
        if(State.getState() instanceof MapState) {
            for (InvisibleWalls iv :((MapState) State.getState()).getInvisibleWalls()) {
                if(getCollision().intersects(iv)){
                    canMove = false;
                    switch (facing){
                        case "Down" :
                            handler.setYDisplacement(handler.getYDisplacement() + speed);
                            break;
                        case "Up" :
                            handler.setYDisplacement(handler.getYDisplacement() - speed);
                            break;
                        case "Right" :
                            handler.setXDisplacement(handler.getXDisplacement() + speed);
                            break;
                        case "Left" :
                            handler.setXDisplacement(handler.getXDisplacement() - speed);
                            break;
                    }
                    break;
                }

            }

        }

        if (handler.getKeyManager().down && canMove){
            handler.setYDisplacement(handler.getYDisplacement() - speed);
            facing = "Down";
        }
        else if (handler.getKeyManager().up && canMove){
            handler.setYDisplacement(handler.getYDisplacement() + speed);
            facing = "Up";
        }
        else if (handler.getKeyManager().right && canMove){
            handler.setXDisplacement(handler.getXDisplacement() - speed);
            facing = "Right";
        }
        else if (handler.getKeyManager().left && canMove){
            handler.setXDisplacement(handler.getXDisplacement() + speed);
            facing = "Left";
        }

    }


    @Override
    public Rectangle getCollision() {
        return player;
    }

    /**
     * Called when the player has collided with another static entity.
     * Used to push the player back from passing through a static entity.
     *
     * @param collidedXPos the xPosition the static entity is located at.
     */
    public void WallBoundary(double collidedXPos) {

        int playerXPos = Math.abs(handler.getXDisplacement());

        if (playerXPos < collidedXPos / 2) {
            handler.setXDisplacement(handler.getXDisplacement() + 2);
        }
        else if(playerXPos > collidedXPos / 2) {
            handler.setXDisplacement(handler.getXDisplacement() - 2);
        }
    }



    /*
     * Although the TRUE Player position is in the middle of the screen,
     * these two methods give us the value as if the player was part of the world.
     */
    @Override
    public double getXOffset() {
        return -this.handler.getXDisplacement() + xPosition;
    }

    @Override
    public double getYOffset() {
        return -this.handler.getYDisplacement() + yPosition;
    }

}