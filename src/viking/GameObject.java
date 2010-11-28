package viking;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;
import java.util.LinkedList;

public abstract class GameObject
{
	// this is how much momentum is transferred in a collision
	public static final double COLLISION_COEFFICIENT = 0.8;
	
	protected boolean markedForDestruction=false;
	protected boolean fixedPosition=false;
	protected boolean solid=true;
	
	protected double x;
	protected double y;
	protected int depth=0;
	protected double xVelocity=0;
	protected double yVelocity=0;
	
	protected Rectangle boundingBox;
	
	public final int getDepth()
	{
		return this.depth;
	}
	
	public final int getX()
	{
		return (int)this.x;
	}
	
	public final int getY()
	{
		return (int)this.y;
	}
	
	public final void updatePosition(List<GameObject> allObjects)
	{
		if (!this.fixedPosition)
		{
			Rectangle theoreticalRect=new Rectangle((int)(this.x+this.xVelocity+this.boundingBox.x),(int)(this.y+this.yVelocity+this.boundingBox.y),this.boundingBox.width,this.boundingBox.height);
			
			List<GameObject> colliding=new LinkedList<GameObject>();
			
			for (GameObject other : allObjects)
			{
				if (other!=this && other.isSolid())
				{
					if (theoreticalRect.intersects(other.getCollisionBox()))
					{
						colliding.add(other);
					}
				}
			}
			
			if (colliding.isEmpty())
			{
				this.x+=this.xVelocity;
				this.y+=this.yVelocity;
			}
			else
			{
				GameObject closest=null;
				double steps=Double.MAX_VALUE;
				boolean stopX=false;
				boolean stopY=false;
				
				Rectangle thisBox=this.getCollisionBox();
				
				for (GameObject other : colliding)
				{
					Rectangle otherBox=other.getCollisionBox();
					double xSteps=Math.abs(Math.min(Math.abs(thisBox.x-(otherBox.x+otherBox.width)),
										            Math.abs((thisBox.x+thisBox.width)-otherBox.x))
										            / this.xVelocity);
					double ySteps=Math.abs(Math.min(Math.abs(thisBox.y-(otherBox.y+otherBox.height)),
													Math.abs((thisBox.y+thisBox.height)-otherBox.y))
													/ this.yVelocity);
														  
					double minSteps;
					if (this.xVelocity==0)
					{
						minSteps=ySteps;
					}
					else if (this.yVelocity==0)
					{
						minSteps=xSteps;
					}
					else
					{
						minSteps=Math.min(xSteps,ySteps);
					}
					if (closest==null || steps>minSteps)
					{
						closest=other;
						steps=minSteps;
						if (steps==xSteps)
						{
							stopX=true;
						}
						else
						{
							stopX=false;
						}
						if (steps==ySteps)
						{
							stopY=true;
						}
						else
						{
							stopY=false;
						}
					}
				}
				
				if (stopX)
				{
					this.x+=steps*this.xVelocity;
					if (!closest.isFixedPosition())
					{
						closest.impulse(COLLISION_COEFFICIENT*this.xVelocity,0);
						if (!stopY)
						{
							closest.handleCollision(this);
						}
					}
					this.xVelocity=0;
				}
				else
				{
					this.x+=this.xVelocity;
				}
				if (stopY)
				{
					this.y+=steps*this.yVelocity;
					if (!closest.isFixedPosition())
					{
						closest.impulse(0,COLLISION_COEFFICIENT*this.yVelocity);
						closest.handleCollision(this);
					}
					this.yVelocity=0;
				}
				else
				{
					this.y+=this.yVelocity;
				}
			}
		}
	}
	
	public void handleCollision(GameObject other)
	{
	}
	
	public final void impulse(double xForce,double yForce)
	{
		if (!this.fixedPosition)
		{
			this.xVelocity+=xForce;
			this.yVelocity+=yForce;
		}
	}
	
	public void applyFriction(double friction)
	{
		this.xVelocity*=friction;
	}
	
	public final boolean isMarkedForDestruction()
	{
		return this.markedForDestruction;
	}
	
	public final boolean isSolid()
	{
		return this.solid;
	}
	
	public final boolean isFixedPosition()
	{
		return this.fixedPosition;
	}
	
	public void markForDestruction()
	{
		this.markedForDestruction=true;
	}
	
	public abstract void update();
	
	protected abstract void render(Graphics g);
	
	/**
	 * returns true if shit should indicate damage
	 */
	public boolean damage(int damage)
	{
		return false;
	}
	
	public final void draw(Graphics g)
	{
		g.translate((int)this.x,(int)this.y);
		this.render(g);
		g.translate((int)-this.x,(int)-this.y);
	}
	
	public final Rectangle getCollisionBox()
	{
		return new Rectangle((int)this.x+this.boundingBox.x,(int)this.y+this.boundingBox.y,this.boundingBox.width,this.boundingBox.height);
	}
	
	public boolean isCollidingWith(GameObject object)
	{
		return this.getCollisionBox().intersects(object.getCollisionBox());
	}
}
