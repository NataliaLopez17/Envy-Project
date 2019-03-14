package Game.Entities.Dynamics;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import Game.GameStates.InWorldState;
import Game.GameStates.State;
import Game.World.Walls;
import Game.World.InWorldAreas.InWorldWalls;
import Input.KeyManager;
import Main.GameSetUp;
import Main.Handler;
import Resources.Animation;
import Resources.Images;

public class Lucina extends BaseDynamicEntity{

	private Rectangle player;
	private Rectangle lucina;
	private KeyManager keyManager;
	private boolean canMove;
	public static boolean checkInWorld;


	public static final int InMapWidthFrontAndBack = 15 * 3, InMapHeightFront = 27 * 3, InMapHeightBack = 23 * 3,
			InMapWidthSideways = 13 * 3, InMapHeightSideways = 22 * 3, 
			InAreaWidthFrontAndBack = 15 * 5, InAreaHeightFront = 27 * 5, InAreaHeightBack = 23 * 5,
			InAreaWidthSideways = 13 * 5, InAreaHeightSideways = 22 * 5;

	private int currentWidth, currentHeight;
	public static boolean isinArea = false;
	private int switchingCoolDown = 0;

	// Animations
	private Animation animDown, animUp, animLeft, animRight;
	private int animWalkingSpeed = 150;

	public Lucina(Handler handler, int xPosition, int yPosition) {

		super(handler, yPosition, yPosition, null);

		this.xPosition = xPosition;
		this.yPosition = yPosition;

		currentWidth = InMapWidthFrontAndBack;
		currentHeight = InMapHeightFront;

		animDown = new Animation(animWalkingSpeed, Images.lucina_front);
		animLeft = new Animation(animWalkingSpeed, Images.lucina_left);
		animRight = new Animation(animWalkingSpeed, Images.lucina_right);
		animUp = new Animation(animWalkingSpeed, Images.lucina_back);

		speed = 30;
		lucina = new Rectangle();
		checkInWorld = false;

	}

	@Override
	public void tick() {


		if (!GameSetUp.LOADING) {

			animDown.tick();
			animUp.tick();
			animRight.tick();
			animLeft.tick();

			UpdateNextMove();

			if (GameSetUp.SWITCHING) {
				switchingCoolDown++;
			}
			if (switchingCoolDown >= 30) {
				GameSetUp.SWITCHING = false;
				switchingCoolDown = 0;

			}

			if (State.getState().equals(handler.getGame().inWorldState)) {
				checkInWorld = true;
			} else {
				checkInWorld = false;
			}

		}

	}


	@Override
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		g.drawImage(
				getCurrentAnimationFrame(animDown, animUp, animLeft, animRight, Images.lucina_front, Images.lucina_back,
						Images.lucina_left, Images.lucina_right),
				(int) xPosition, (int) yPosition, currentWidth, currentHeight, null);

		lucina = new Rectangle((int) xPosition, (int) yPosition+(currentHeight/2)+5, currentWidth-3, currentHeight/2);

		if (GameSetUp.DEBUGMODE) {
			g2.draw(nextArea);
			g2.draw(getCollision());
		}
	}

	private void UpdateNextMove() {
		switch (facing) {
		case "Up":
			nextArea = new Rectangle( player.x, player.y - speed, player.width, speed);
			break;
		case "Down":
			nextArea = new Rectangle(player.x , player.y+player.height-20 , player.width, speed);

			break;
		case "Left":
			nextArea = new Rectangle(player.x - speed, player.y, speed, player.height);

			break;
		case "Right":
			nextArea = new Rectangle(player.x + player.width, player.y, speed, player.height);

			break;
		}
	}

	private void PushPlayerBack() {

		canMove = false;
		switch (facing) {
		case "Down":
			Move(false, 1);
			break;
		case "Up":
			Move(false, -1);
			break;
		case "Right":
			Move(true, 1);
			break;
		case "Left":
			Move(true, -1);
			break;
		}
	}

	private void CheckForWalls() {

		if (!checkInWorld) {
			for (Walls w : handler.getWorldManager().getWalls()) {

				if (nextArea.intersects(w)) {

					if (w.getType().equals("Wall")) {
						PushPlayerBack();
					}

				}
			}
		} 

		else if (Lucina.isinArea) {

			for (InWorldWalls iw : InWorldState.SArea.getWalls()) {

				if (nextArea.intersects(iw)) {
					if (iw.getType().equals("Wall"))
						PushPlayerBack();

				}
			}
		}
	}

	/**
	 *
	 * @param XorY  where true is X and false is Y
	 * @param speed
	 */
	private void Move(boolean XorY, int speed) {

		isMoving = true;

		if (!checkInWorld) {
			if (XorY) {
				setWidthAndHeight(InMapWidthSideways, InMapHeightSideways);
				handler.setXDisplacement(handler.getXDisplacement() + speed);
			} else {
				if (facing.equals("Up")) {
					setWidthAndHeight(InMapWidthFrontAndBack, InMapHeightBack);
				} else {
					setWidthAndHeight(InMapWidthFrontAndBack, InMapHeightFront);
				}
				handler.setYDisplacement(handler.getYDisplacement() + speed);
			}
		} else {
			if (XorY) {
				setWidthAndHeight(InAreaWidthSideways, InAreaHeightSideways);
				handler.setXInWorldDisplacement((handler.getXInWorldDisplacement() + speed));
			} else {
				if (facing.equals("Up")) {
					setWidthAndHeight(InAreaWidthFrontAndBack, InAreaHeightBack);
				} else {
					setWidthAndHeight(InAreaWidthFrontAndBack, InAreaHeightFront);
				}

				handler.setYInWorldDisplacement(handler.getYInWorldDisplacement() + speed);
			}

		}

	}

	@Override
	public Rectangle getCollision() {
		return lucina;
	}

	/**
	 * !!!!!!!!!TO REDESIGN OR DELETE!!!!!!!
	 *
	 *
	 * Called when the player has collided with another static entity. Used to push
	 * the player back from passing through a static entity.
	 *
	 * @param collidedXPos the xPosition the static entity is located at.
	 */
	public void WallBoundary(double collidedXPos, double collidedYPos) {

		int playerXPos = Math.abs(handler.getXDisplacement());
		int playerYPos = Math.abs(handler.getYDisplacement());

		if (playerXPos < collidedXPos / 2) {
			handler.setXDisplacement(handler.getXDisplacement() + 2);
		} 

		else if (playerXPos > collidedXPos / 2) {
			handler.setXDisplacement(handler.getXDisplacement() - 2);
		}

		if (playerYPos < collidedYPos / 2) {
			handler.setYDisplacement(handler.getYDisplacement() + 2);
		}

		else if (playerYPos > collidedYPos / 2) {
			handler.setYDisplacement(handler.getYDisplacement() - 2);
		}
	}

	/*
	 * Although the TRUE Player position is in the middle of the screen, these two
	 * methods give us the value as if the player was part of the world.
	 */
	@Override
	public double getXOffset() {

		if (!checkInWorld)
			return -this.handler.getXDisplacement() + xPosition;
		else
			return -this.handler.getXInWorldDisplacement() + xPosition;
	}

	@Override
	public double getYOffset() {

		if (!checkInWorld)
			return -this.handler.getYDisplacement() + yPosition;
		else
			return -this.handler.getYInWorldDisplacement() + yPosition;
	}

	public void setWidthAndHeight(int newWidth, int newHeight) {
		this.currentWidth = newWidth;
		this.currentHeight = newHeight;
	}
}

