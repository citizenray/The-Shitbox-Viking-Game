package viking;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Color;

public class Viking extends GameObject
{
	public final static double JUMP_VELOCITY = 5*PlatformScreen.BASE_GRAVITY;
	
	public Image image;
	
	public Viking(int x,int y)
	{
		this.x=x;
		this.y=y;
		this.boundingBox=new Rectangle(0,0,22,32);
		this.solid=true;
		this.image=Loader.getImage("res/images/hagar.png");
	}
	
	public void update()
	{
	}
	
	public void render(Graphics g)
	{
		//g.drawImage(this.image,0,0,MainApp.getApp().getCanvas());
		g.setColor(Color.RED);
		g.fillRect(0,0,this.boundingBox.width,this.boundingBox.height);
	}
}
