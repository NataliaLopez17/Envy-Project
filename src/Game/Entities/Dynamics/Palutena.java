package Game.Entities.Dynamics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import Game.Entities.Statics.Ahri;
import Game.Entities.Statics.BaseStaticEntity;
import Game.World.WorldManager;
import Main.GameSetUp;
import Main.Handler;
import Resources.Images;

public class Palutena extends BaseStaticEntity{

	Rectangle collision;
	int width, height;
	Palutena palutena;

	public Palutena(Handler handler, int xPosition, int yPosition) {
		super(handler, xPosition, yPosition);

		width = 150;
		height = 150;

		this.setXOffset(xPosition);
		this.setYOffset(yPosition);

		collision = new Rectangle();
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		g.drawImage(Images.palutena, (int)(handler.getXInWorldDisplacement() + xPosition),(int)( handler.getYInWorldDisplacement() + yPosition), width, height, null);
		
		collision = new Rectangle((int)(handler.getXInWorldDisplacement() + xPosition - 50),(int)( handler.getYInWorldDisplacement() + yPosition - 50), width + 30, height + 200);
		
		if(collision.intersects(handler.getEntityManager().getPlayer().getCollision())) {
			handler.getEntityManager().getPlayer().pushMe();
			if (WorldManager.enemyDefeated == false) {
				g.drawImage(Images.E, (int) collision.getX(), (int) collision.getY() + 100, null);
			}
			else {
				//put the image here breh
				//g.drawImage(Images.E, (int) collision.getX(), (int) collision.getY() + 100, null);
			}
			
			Player.switch1 = true;
		}
		else {
			Player.switch1 = false;
		}
		
		if(handler.getKeyManager().attbut) {
			g.drawImage(Images.mission, (int)collision.getX() + 200, (int)collision.getY(), null);
		}
			
		
		if (GameSetUp.DEBUGMODE) {
			g2.draw(collision);
		}
		
	}

	@Override
	public Rectangle getCollision() {
		return collision;
	}

	@Override
	public double getXOffset() {
		return xPosition;
	}


}
