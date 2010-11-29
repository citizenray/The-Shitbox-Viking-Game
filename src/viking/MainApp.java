package viking;

import javax.swing.JFrame;
import java.awt.Insets;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import forana.graphics.DoubleCanvas;
import forana.graphics.DataObject;
import java.awt.GraphicsEnvironment;

public class MainApp implements ComponentListener,DataObject
{
	private static MainApp app=null;
	public static final int GAME_WIDTH=320;
	public static final int GAME_HEIGHT=240;
	public static final int WINDOW_WIDTH=800;
	public static final int WINDOW_HEIGHT=600;
	public static final String GAME_TITLE="THE VIKING GAME THING";
	public static final int GAME_DELAY=20; // 50 FPS
	
	public static void launch()
	{
		GamePrefs prefs=Loader.loadPrefs();
		Loader.savePrefs(prefs);
		app=new MainApp(prefs);
	}
	
	public static MainApp getApp()
	{
		return app;
	}
	
	private JFrame frame;
	private int frameInsetWidth;
	private int frameInsetHeight;
	private boolean fullscreen=false;
	
	private DoubleCanvas canvas;
	private KeyBank keyBank;
	private GameScreen screen;
	
	private GamePrefs prefs;
	
	private MainApp(GamePrefs prefs)
	{
		this.prefs=prefs;
		
		GameScreen introScreen=new TestScreen();
		
		this.canvas=new DoubleCanvas(null,null,GAME_WIDTH,GAME_HEIGHT,GAME_DELAY);
		this.canvas.setLocation(0,0);
		this.canvas.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
		this.canvas.setData(this);
		
		this.keyBank=new KeyBank();
		this.canvas.addKeyListener(this.keyBank);
		
		this.frame=new JFrame(GAME_TITLE);
		this.frame.setLocationByPlatform(true);
		this.frame.setVisible(true);
		Insets insets=this.frame.getInsets();
		this.frameInsetWidth=insets.left+insets.right;
		this.frameInsetHeight=insets.top+insets.bottom;
		this.frame.setSize(WINDOW_WIDTH+frameInsetWidth,WINDOW_HEIGHT+frameInsetHeight);
		this.frame.setMinimumSize(new Dimension(GAME_WIDTH+frameInsetWidth,GAME_HEIGHT+frameInsetHeight));
		this.frame.setLayout(null);
		this.frame.addComponentListener(this);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.frame.setVisible(true);
		this.frame.add(canvas);
		
		this.setScreen(introScreen);
		
		this.setFullScreen(false);
		
		this.canvas.start();
	}
	
	public void setScreen(GameScreen screen)
	{
		this.screen=screen;
		this.canvas.setRender(this.screen);
	}
	
	public KeyBank getKeyBank()
	{
		return this.keyBank;
	}
	
	public void update()
	{
		this.screen.update();
		if (this.keyBank.getLastKey()==this.prefs.getFullScreenKey())
		{
			System.out.println("this is where I should fullscreen");
			this.setFullScreen(!this.fullscreen);
		}
		this.keyBank.wipeKeys();
	}
	
	public Canvas getCanvas()
	{
		return this.canvas;
	}
	
	public GamePrefs getPrefs()
	{
		return this.prefs;
	}
	
	private void setFullScreen(boolean fullscreen)
	{
		this.fullscreen=fullscreen;
		
		if (this.fullscreen)
		{
			GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(this.frame);
		}
		else
		{
			GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(null);
		}
	}
	
	public void componentMoved(ComponentEvent e) {}
	public void componentHidden(ComponentEvent e) {}
	public void componentShown(ComponentEvent e) {}
	
	public void componentResized(ComponentEvent e)
	{
		if (e.getSource()==this.frame)
		{
			this.canvas.setSize(this.frame.getWidth()-this.frameInsetWidth,this.frame.getHeight()-this.frameInsetHeight);
		}
	}
}
