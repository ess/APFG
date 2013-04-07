package svb;

import gameState.EyeCatch;
import gameState.Fight;
import gameState.MainMenu;
import gameState.Roster;
import gameState.SplashScreen;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Where everything starts. Resolution, fullscreen mode and framerate are set down the bottom in main(), 
 * and this also sets up the name of the window and instantiates a few important classes.
 * 
 * Later, this will probably be setting up the menu instead.
 * @author Jubilee
 *
 */
public class Main extends StateBasedGame {
	
	public Main() {
		super("AFPG");
	}
	
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		
		addState(new SplashScreen());
		addState(new MainMenu());
		addState(new Roster());
		addState(new EyeCatch());
		addState(new Fight());
		
		Player player;
		Camera camera;
		Manager manager;
		
		manager = new Manager();

		camera = new Camera(container, this);
		manager.cameras.add(camera);
		
		//PLAYERS
		player = new Player(500,0);
		manager.player1 = player;
		manager.players.add(player);
		
		player = new Player(-500,0);
		player.setKeys(Input.KEY_NUMPAD8, Input.KEY_NUMPAD2, Input.KEY_NUMPAD4, Input.KEY_NUMPAD6, 
				Input.KEY_O, Input.KEY_P, Input.KEY_K, Input.KEY_L);
		manager.player2 = player;
		manager.players.add(player);
		//
	}

	//@Override
	/*public void init(GameContainer container) throws SlickException {
		Player player;
		Camera camera;
		Manager manager;
		
		manager = new Manager();
		

		//PLAYERS
		player = new Player(500,0);
		Manager.player1 = player;
		Manager.players.add(player);
		
		player = new Player(-500,0);
		player.setKeys(Input.KEY_NUMPAD8, Input.KEY_NUMPAD2, Input.KEY_NUMPAD4, Input.KEY_NUMPAD6, 
				Input.KEY_O, Input.KEY_P, Input.KEY_K, Input.KEY_L);
		Manager.player2 = player;
		Manager.players.add(player);
		//
		
		//TODO: Put in roster.complete()
		camera = new Camera(container);
		Manager.cameras.add(camera);
		StateFactory mf = new StateFactory();
		Stage stage = new Stage();
		manager.stage = stage;
		//
		
	}*/

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		super.update(container, delta);
		if(container.getInput().isKeyPressed(Input.KEY_TAB))
			Manager.debug = !Manager.debug;
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		//The graphics context should start at 1920x1080, and then scale down to
		//whatever system it's working on. (ContainerWidth / 1920) ?
		g.scale(container.getWidth() / 1920f, container.getHeight() / 1080f);
		super.render(container, g);
	}

	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new Main());
			app.setDisplayMode(1280, 764, false);
			app.setResizable(true);
			app.setAlwaysRender(true);
			app.setTargetFrameRate(60);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

}