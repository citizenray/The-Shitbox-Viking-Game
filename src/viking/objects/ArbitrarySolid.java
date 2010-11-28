package viking.objects;

import java.awt.Rectangle;
import viking.GameObject;
import java.awt.Graphics;
import java.awt.Color;

public class ArbitrarySolid extends GameObject
{
	public ArbitrarySolid(int x,int y,int width,int height)
	{
		this.x=x;
		this.y=y;
		this.boundingBox=new Rectangle(0,0,width,height);
		this.fixedPosition=true;
		this.solid=true;
	}
	
	public void update()
	{
	}
	
	public void render(Graphics g)
	{
	}
	
	public void handleCollision(GameObject obj)
	{
	}
}
