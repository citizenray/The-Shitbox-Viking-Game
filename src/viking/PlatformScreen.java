package viking;

import java.awt.Graphics;

public abstract class PlatformScreen extends GameScreen
{
	public static final double BASE_GRAVITY = 0.2;
	public static final double BASE_FRICTION = 0.9;
	
	protected Viking player;
	protected ObjectCollection objects;
	
	public PlatformScreen()
	{
		this.objects=new ObjectCollection();
	}
	
	public void update()
	{
		this.objects.sortForData();
		for (GameObject object : this.objects.getObjects())
		{
			object.impulse(0,BASE_GRAVITY);
		}
		this.objects.updatePositions();
		this.objects.purgeDestroyed();
		for (GameObject object : this.objects.getObjects())
		{
			object.update();
			object.applyFriction(BASE_FRICTION);
		}
	}
	
	public void render(Graphics g)
	{
		this.objects.sortForRender();
		for (GameObject object : this.objects.getObjects())
		{
			object.draw(g);
		}
	}
}
