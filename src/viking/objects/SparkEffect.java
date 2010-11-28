package viking.objects;

import java.awt.Graphics;
import java.awt.Color;
import viking.GameObject;
import java.awt.Rectangle;
import java.util.Set;
import java.util.HashSet;
import viking.PlatformScreen;
import java.util.Iterator;

public class SparkEffect extends GameObject
{
	private Set<Spark> sparks;
	private int theta;
	
	public SparkEffect(int x,int y,int theta)
	{
		super();
		this.depth=1;
		this.x=x;
		this.y=y;
		this.boundingBox=new Rectangle(0,0,0,0);
		this.fixedPosition=true;
		this.sparks=new HashSet<Spark>();
		this.theta=theta;
		this.solid=false;
	}
	
	public void update()
	{
		if (Math.random()*3<1)
		{
			this.sparks.add(new Spark(this.theta));
		}
		synchronized (SparkEffect.class)
		{
			for (Iterator<Spark> iter=this.sparks.iterator(); iter.hasNext(); )
			{
				Spark currentSpark=iter.next();
				currentSpark.update();
				if (currentSpark.isDead())
				{
					iter.remove();
				}
			}
		}
	}
	
	public void render(Graphics g)
	{
		synchronized (SparkEffect.class)
		{
			for (Spark spark : this.sparks)
			{
				spark.render(g);
			}
		}
	}
	
	private class Spark
	{
		private double xVelocity;
		private double yVelocity;
		private double x;
		private double y;
		private int life;
		
		public Spark(int theta)
		{
			this.x=0;
			this.y=0;
			double angle=Math.PI/180*(theta-50+100*Math.random());
			double power=Math.pow(Math.random()*2,2);
			this.xVelocity=power*Math.cos(angle);
			this.yVelocity=power*Math.sin(angle);
			this.life=(int)(Math.random()*75);
		}
		
		public boolean isDead()
		{
			return this.life<=0;
		}
		
		public void update()
		{
			this.x+=xVelocity;
			this.y-=yVelocity;
			this.yVelocity-=PlatformScreen.BASE_GRAVITY;
			this.life--;
		}
		
		public void render(Graphics g)
		{
			g.setColor(Color.YELLOW);
			g.drawLine((int)this.x,(int)this.y,(int)this.x,(int)this.y);
		}
	}
}
