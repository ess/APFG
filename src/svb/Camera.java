package svb;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import svb.Manager.WORLD;

import entities.Actor;
import entities.Fighter;
import entities.Hitbox;


public class Camera {

	public Rectangle screen;
	public Rectangle bounds;
	public Vector2f location;
	public Vector2f screenLocation;
	public Vector2f speed;
	
	public Actor target1, target2;
	public boolean crop = false;

	private GameContainer container;
	
	public int tempInt = 0;

	public Camera(GameContainer container) {
		this.container = container;
		
		location = new Vector2f();
		screenLocation = new Vector2f(50,125);
		screen = new Rectangle(0, 0, container.getWidth(), container.getHeight());
	}

	public void update(int delta)
	{
		if(target1 != null && target2 != null)
		{
			location.x = (target1.location.x + target1.zoneBox.getWidth()/2 + target2.location.x + target2.zoneBox.getWidth()/2)/2 - screen.getWidth()/2;
			location.y = (target1.location.y + target1.zoneBox.getHeight()/2 + target2.location.y + target2.zoneBox.getHeight()/2)/2 - screen.getHeight()/2;
		}
		screen.setLocation(screenLocation);
	}
	
	public void render(Graphics g)
			throws SlickException {
		
		Vector2f offset = new Vector2f();
		
		//TODO Delete me. Displays ground level.
		//-30 so that it looks like they're actually standing on something.
		g.drawLine(0, WORLD.groundLevel - location.y + screen.getY() - 30, container.getScreenWidth(), WORLD.groundLevel + screen.getY() - location.y - 30);
		
		for (Fighter f : Manager.fighters) {
			
			offset.x = 0;
			offset.y = 0;

			f.zoneBox.setLocation(f.location.add(location.negate().add(screenLocation)));
			if (screen.intersects(f.zoneBox)) {
				f.render(container, g);
			}
			f.zoneBox.setLocation(f.location.add(location).add(screenLocation.negate()));
		}

		for (Player p : Manager.players) 
		{
			//TODO Delete me. Shows hitboxes. Replace with effect class when done.
			for(Hitbox h : p.fighter.getState().getHitBoxes())
			{
				h.setX(h.getX() - location.x + screen.getX());
				h.setY(h.getY() - location.y + screen.getY());
				g.fill(h);
				h.setX(h.getX() + location.x - screen.getX());
				h.setY(h.getY() + location.y - screen.getY());
			}
			////
			p.render(container, g);
		}
		
	}
}
