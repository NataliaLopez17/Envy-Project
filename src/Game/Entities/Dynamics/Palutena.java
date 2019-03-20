package Game.Entities.Dynamics;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import Game.Entities.Statics.BaseStaticEntity;
import Main.GameSetUp;
import Main.Handler;
import Resources.Images;

public class Palutena extends BaseStaticEntity{

	Rectangle collision;
	int width, height;

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
		collision = new Rectangle((int)(handler.getXInWorldDisplacement() + xPosition - 50),(int)( handler.getYInWorldDisplacement() + yPosition - 50), width + 30, height + 100);
		
		
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
