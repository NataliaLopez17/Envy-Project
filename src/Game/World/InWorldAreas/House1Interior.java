package Game.World.InWorldAreas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import Game.Entities.EntityManager;
import Game.World.Walls;
import Main.GameSetUp;
import Main.Handler;
import Resources.Images;

public class House1Interior extends BaseArea{

	
	Rectangle exit;
    Rectangle playerRect;
    public static boolean isinHouse = false;

    private int imageWidth = 3680, imageHeight = 4000;
    public final static int playerXSpawn = -380, playerYSpawn = -3180;

    private Rectangle background = new Rectangle(3000, 3000);

    public static ArrayList<InWorldWalls> house1Walls;
    
    
	public House1Interior(Handler handler, EntityManager entityManager) {
		super(handler, entityManager);
        name="House 1";
        handler.setXInWorldDisplacement(playerXSpawn);
        handler.setYInWorldDisplacement(playerYSpawn);

        playerRect = new Rectangle((int) handler.getWidth() / 2 - 5, (int) (handler.getHeight() / 2) + 300, 70, 70);

        this.entityManager = entityManager;

        house1Walls = new ArrayList<>();
        AddWalls();

	}
	

    public void tick() {
        super.tick();

        for (Walls w : house1Walls) {
            w.tick();
        }
        if(!GameSetUp.LOADING) {
            entityManager.tick();
        }

    }
	
	 @Override
	    public void render(Graphics g) {
	        super.render(g);


	        Graphics2D g2 = (Graphics2D) g;

	        g2.setColor(Color.black);
	        g2.fill(background);

	        g.drawImage(Images.ScaledHouse1, handler.getXInWorldDisplacement(), handler.getYInWorldDisplacement(), null);

	        if (GameSetUp.DEBUGMODE) {
	            for (Walls w : house1Walls) {

	                if (w.getType().equals("Wall"))
	                    g2.setColor(Color.black);
	                else
	                    g2.setColor(Color.PINK);

	                w.render(g2);
	            }
	        }


	        entityManager.render(g);

	    }
	
	private void AddWalls() {


        house1Walls.add(new InWorldWalls(handler, 100, 0, 10, imageHeight, "Wall"));								// Left Border
        house1Walls.add(new InWorldWalls(handler, 0, imageHeight-100, imageWidth/3, 50, "Wall"));					// Bottom LeftBorder (Left side relative to Entrance)
        house1Walls.add(new InWorldWalls(handler, imageWidth/2-350, imageHeight-100, imageWidth/4, 50, "Wall"));	// Bottom RightBorder (Right side relative to Entrance)
        house1Walls.add(new InWorldWalls(handler, 0, 130, imageWidth, 10, "Wall"));								//
        house1Walls.add(new InWorldWalls(handler, imageWidth - 130, 0, 10, imageHeight, "Wall"));					// Right Border

        house1Walls.add(new InWorldWalls(handler, imageWidth/3, imageHeight, 300, 50, "Wall"));					// Entrance

        house1Walls.add(new InWorldWalls(handler, 2950, 340, 320, 100, "Start Exit"));							// Exit at Start
        house1Walls.add(new InWorldWalls(handler, 1230, 3900, 280, 100, "End Exit"));							// Exit at End

    }
	
	 @Override
	    public ArrayList<InWorldWalls> getWalls() {
	        return house1Walls;
	    }

}
