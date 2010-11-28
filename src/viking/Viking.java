package viking;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Viking extends GameObject
{
	public final static double JUMP_VELOCITY = 4;
	public final double MOVE_SPEED = 0.2;
	
	public Image image;
	
	public Viking(int x,int y)
	{
		this.x=x;
		this.y=y;
		this.boundingBox=new Rectangle(0,0,17,31);
		this.solid=true;
		this.image=Loader.getImage("res/images/bv.png");
	}
	
	public void update()
	{
		KeyBank keys=MainApp.getApp().getKeyBank();
		GamePrefs prefs=MainApp.getApp().getPrefs();
		if (keys.getKeyDown(prefs.getJumpKey()) && keys.getStateNew(prefs.getJumpKey()) && this.yVelocity==0)
		{
			this.impulse(0,-JUMP_VELOCITY);
		}
		if (keys.getKeyDown(prefs.getLeftKey()))
		{
			this.impulse(-MOVE_SPEED,0);
		}
		if (keys.getKeyDown(prefs.getRightKey()))
		{
			this.impulse(MOVE_SPEED,0);
		}
	}
	
	public void render(Graphics g)
	{
		g.drawImage(this.image,-2,-3,MainApp.getApp().getCanvas());
	}
}
