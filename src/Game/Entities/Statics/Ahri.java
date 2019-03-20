package Game.Entities.Statics;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import Main.GameSetUp;
import Main.Handler;
import Resources.Images;

public class Ahri extends BaseStaticEntity{

	Rectangle collision;
	int width, height;
	
	public Ahri(Handler handler, int xPosition, int yPosition) {
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
		
		g.drawImage(Images.ahri,  (int)(handler.getXDisplacement() + xPosition),(int)( handler.getYDisplacement() + yPosition), width, height, null);
		collision = new Rectangle((int)(handler.getXDisplacement() + xPosition - 30), (int)(handler.getYDisplacement() + yPosition + 50), width + 50, height);
		
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
