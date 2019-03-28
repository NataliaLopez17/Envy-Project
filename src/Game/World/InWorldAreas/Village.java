package Game.World.InWorldAreas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import Game.Entities.EntityManager;
import Game.Entities.Dynamics.Palutena;
import Game.World.Walls;
import Main.GameSetUp;
import Main.Handler;
import Resources.Images;

public class Village extends BaseArea{


	Rectangle exit;
	Rectangle playerRect;
	public static boolean isinVillage = false;
	Palutena palutena;

	private int imageWidth = 3680, imageHeight = 4000;
	public final static int playerXSpawn = -380, playerYSpawn = -3180;

	private Rectangle background = new Rectangle(3000, 3000);

	public static ArrayList<InWorldWalls> villageWalls;


	public Village(Handler handler, EntityManager entityManager) {
		super(handler, entityManager);
		name="Village";
		handler.setXInWorldDisplacement(playerXSpawn);
		handler.setYInWorldDisplacement(playerYSpawn);

		playerRect = new Rectangle((int) handler.getWidth() / 2 - 5, (int) (handler.getHeight() / 2) + 300, 70, 70);

		this.entityManager = entityManager;

		this.entityManager.AddEntity(new Palutena(handler, 1150, 900));

		villageWalls = new ArrayList<>();
		AddWalls();

	}


	public void tick() {
		super.tick();

		for (Walls w : villageWalls) {
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
		
		if(handler.getEntityManager().getPlayer().getCollision().contains(new Rectangle(1000,500))) {
			g2.setColor(Color.RED);
		}
		
		g.drawImage(Images.ScaledVillage, handler.getXInWorldDisplacement(), handler.getYInWorldDisplacement(), null);

		if (GameSetUp.DEBUGMODE) {
			for (Walls w : villageWalls) {

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
		villageWalls.add(new InWorldWalls(handler, 700, 4000, 500, 500, "Wall"));

		villageWalls.add(new InWorldWalls(handler, 0, 0, 400, imageHeight, "Wall"));// Left Border
		villageWalls.add(new InWorldWalls(handler, 0, imageHeight-900, imageWidth/3 -200, 900, "Wall"));					// Bottom LeftBorder (Left side relative to Entrance)
		villageWalls.add(new InWorldWalls(handler, imageWidth/2-450, imageHeight-500, imageWidth, 700, "Wall"));	// Bottom RightBorder (Right side relative to Entrance)
		villageWalls.add(new InWorldWalls(handler, 0, 50, imageWidth, 200, "Wall"));								//
		villageWalls.add(new InWorldWalls(handler, imageWidth - 130, 0, 100, imageHeight, "Wall"));	
		
		villageWalls.add(new InWorldWalls(handler, imageWidth/2-450, imageHeight-700, 900, 1000, "Wall"));	
		villageWalls.add(new InWorldWalls(handler, 0, 0, 670, 1600, "Wall"));	
		
		villageWalls.add(new InWorldWalls(handler, 2800, 2800, 1000, 1000, "Wall"));	
		
		villageWalls.add(new InWorldWalls(handler, 1600, 2800, 650, 700, "Wall"));	
		
		villageWalls.add(new InWorldWalls(handler, 500, 1700, 2000, 700, "Wall"));
		
		villageWalls.add(new InWorldWalls(handler, 970, 2450, 400, 500, "Wall"));	
		
		villageWalls.add(new InWorldWalls(handler, 1400, 2100, 1090, 590, "Wall"));	
		
		villageWalls.add(new InWorldWalls(handler, 2450, 2800, 500, 550, "Wall"));	
		
		villageWalls.add(new InWorldWalls(handler, 2580, 2400, 250, 250, "Wall"));	//tree
		
		villageWalls.add(new InWorldWalls(handler, 3000, 2150, 1000, 500, "Wall")); //house next to the tree
		
		villageWalls.add(new InWorldWalls(handler, 850, 100, 200, 200, "Door Exit 1")); //exit 1
		
		villageWalls.add(new InWorldWalls(handler, 1100, 3800, 300, 200, "Door Exit 2")); //exit 2
		
		villageWalls.add(new InWorldWalls(handler, 1350, 800, 600, 550, "Wall")); //pond
		
		villageWalls.add(new InWorldWalls(handler, 2100, 200, imageWidth, 700, "Wall")); //log house
		
		villageWalls.add(new InWorldWalls(handler, 1000, 200, imageWidth, 500, "Wall")); //upper trees
		
		villageWalls.add(new InWorldWalls(handler, 3000, 1100, imageWidth, 400, "Wall")); //right corner houses
		
		villageWalls.add(new InWorldWalls(handler, 1950, 1050, 850, 300, "Wall")); //wall besides the pond
		
	}

	@Override
	public ArrayList<InWorldWalls> getWalls() {
		return villageWalls;
	}

}
