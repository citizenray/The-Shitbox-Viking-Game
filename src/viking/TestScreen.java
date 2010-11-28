package viking;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;
import viking.objects.ArbitrarySolid;

public class TestScreen extends PlatformScreen
{
	private Image bg;
	
	public TestScreen()
	{
		super();
		this.player=new Viking(40,10);
		this.objects.add(this.player);
		this.objects.add(new ArbitrarySolid(0,210,320,30));
		this.bg=Loader.getImage("res/images/dungeon.png");
	}
	
	public void render(Graphics g)
	{
		g.drawImage(this.bg,0,0,MainApp.getApp().getCanvas());
		super.render(g);
	}
	
	public void update()
	{
		super.update();
	}
}
