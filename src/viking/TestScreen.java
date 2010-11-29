package viking;

import java.awt.Graphics;
import java.awt.Image;
import viking.objects.ArbitrarySolid;
import viking.objects.SparkEffect;

public class TestScreen extends PlatformScreen
{
	private Image bg;
	
	public TestScreen()
	{
		super();
		this.player=new Viking(100,10);
		this.objects.add(this.player);
		this.objects.add(new ArbitrarySolid(94,216,128,24));
		this.objects.add(new ArbitrarySolid(94,152,96,16));
		this.objects.add(new ArbitrarySolid(250,212,48,8));
		this.objects.add(new ArbitrarySolid(10,174,48,8));
		this.objects.add(new ArbitrarySolid(10,79,48,8));
		this.objects.add(new ArbitrarySolid(250,84,48,8));
		this.objects.add(new SparkEffect(214,215,90));
		this.objects.add(new SparkEffect(190,160,0));
		this.bg=Loader.getImage("res/images/2-2.png");
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
