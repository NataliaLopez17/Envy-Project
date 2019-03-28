package Game.Entities.Statics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import Game.Entities.Dynamics.Player;
import Game.World.WorldManager;
import Main.GameSetUp;
import Main.Handler;
import Resources.Images;

public class Ahri extends BaseStaticEntity{

	public Rectangle collision;
	int width, height;
	public static boolean switch2 = false;
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
		
		if(collision.intersects(handler.getEntityManager().getPlayer().getCollision()) && WorldManager.enemyDefeated == false) {
			switch2 = true;
		}
		else {
			switch2 = false;
		}
		
		
		Graphics2D g2 = (Graphics2D) g;
		g.setColor(Color.WHITE);
		g.setFont(new Font("Courier New", Font.BOLD, 32));
		
		g.drawImage(Images.ahri,  (int)(handler.getXDisplacement() + xPosition),(int)( handler.getYDisplacement() + yPosition), width, height, null);
		
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
