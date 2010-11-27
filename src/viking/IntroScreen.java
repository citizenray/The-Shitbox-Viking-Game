package viking;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;

public class IntroScreen extends GameScreen
{
	private Image dude;
	private Image sword;
	private Image current;
	private Image bg;
	private int x;
	private int y;
	
	public IntroScreen()
	{
		this.dude=Loader.getImage("res/images/hagar.png");
		this.sword=Loader.getImage("res/images/hagarsword.png");
		this.current=this.dude;
		this.bg=Loader.getImage("res/images/dungeon.png");
		this.x=0;
		this.y=0;
	}
	
	public void render(Graphics g)
	{
		g.drawImage(this.bg,0,0,MainApp.getApp().getCanvas());
		g.drawImage(this.current,x,y,MainApp.getApp().getCanvas());
	}
	
	public void update()
	{
		if (MainApp.getApp().getKeyBank().getKeyDown(KeyEvent.VK_LEFT))
		{
			x-=5;
		}
		if (MainApp.getApp().getKeyBank().getKeyDown(KeyEvent.VK_RIGHT))
		{
			x+=5;
		}
		if (MainApp.getApp().getKeyBank().getKeyDown(KeyEvent.VK_DOWN))
		{
			y+=5;
		}
		if (MainApp.getApp().getKeyBank().getKeyDown(KeyEvent.VK_UP))
		{
			y-=5;
		}
		if (MainApp.getApp().getKeyBank().getKeyDown(KeyEvent.VK_S) && MainApp.getApp().getKeyBank().getStateNew(KeyEvent.VK_S))
		{
			this.current=(this.current==this.dude?this.sword:this.dude);
		}
	}
}
