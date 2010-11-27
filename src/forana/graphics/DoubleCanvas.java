package forana.graphics;


import java.awt.Graphics;
import java.awt.Canvas;
import java.awt.image.VolatileImage;

public class DoubleCanvas extends Canvas
{
	private static final long serialVersionUID=1L;
	
	private VolatileImage bufferImage;
	private RenderObject renderObject;
	private DataObject dataObject;
	private Thread updateThread;
	private Thread drawThread;
	private int updateDelay;
	private Object drawMutex;
	private int width;
	private int height;
	//private long vsyncFrameWidth;
	//private long currentTime;
	//private long lastDraw;
	
	private boolean canvasShown;
	
	public DoubleCanvas(RenderObject renderObject,DataObject dataObject,int width,int height,int updateDelay)
	{
		this.renderObject=renderObject;
		this.dataObject=dataObject;
		this.bufferImage=null;
		this.width=width;
		this.height=height;
		this.updateDelay=updateDelay;
		this.drawMutex=new Object(); // who cares
		//this.vsyncFrameWidth=1000000000;
		//this.currentTime=0;
		//this.lastDraw=0;
		
		this.canvasShown=false;
		
		this.initThreads();
	}
	
	private void initThreads()
	{
		this.updateThread=new Thread(new Runnable() {
			public void run()
			{
				while (true)
				{
					try
					{
						Thread.sleep(updateDelay);
						synchronized (drawMutex)
						{
							dataObject.update();
						}
						if (!isFocusOwner())
						{
							requestFocus();
						}
					}
					catch (InterruptedException e)
					{
						System.err.println("Update thread stopped.");
					}
				}
			}
		});
		this.drawThread=new Thread(new Runnable() {
			public void run()
			{
				while (true)
				{
					try
					{
						Thread.sleep(10);
						//currentTime=System.nanoTime()-lastDraw;
						//if (currentTime>vsyncFrameWidth)
						{
							synchronized (drawMutex)
							{
								repaint();
								//lastDraw=System.nanoTime();
							}
						}
					}
					catch (InterruptedException e)
					{
						System.err.println("Drawing thread stopped.");
					}
				}
			}
		});
		this.drawThread.setPriority(Thread.MAX_PRIORITY);
	}
	
	public void start()
	{
		this.updateThread.start();
		this.drawThread.start();
	}
	
	public void stop()
	{
		this.updateThread.interrupt();
		this.drawThread.interrupt();
	}
	
	public void paint(Graphics g)
	{
		if (!canvasShown)
		{
			this.bufferImage=this.getGraphicsConfiguration().createCompatibleVolatileImage(width,height);
			
			//int refreshRate = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getRefreshRate();
        	//this.vsyncFrameWidth=1000000000L/refreshRate;
        
			canvasShown=true;
		}
		else
		{
			do
			{
				int val=this.bufferImage.validate(this.getGraphicsConfiguration());
				
				if (val==VolatileImage.IMAGE_INCOMPATIBLE)
				{
					this.bufferImage=this.getGraphicsConfiguration().createCompatibleVolatileImage(width,height);
				}
				
				this.renderObject.render(this.bufferImage.getGraphics());
				
				g.drawImage(this.bufferImage,0,0,this.getWidth(),this.getHeight(),this);
			}
			while (this.bufferImage.contentsLost());
		}
	}
	
	public void update(Graphics g)
	{
		this.paint(g);
	}
	
	public void setRender(RenderObject render)
	{
		this.renderObject=render;
	}
	
	public void setData(DataObject data)
	{
		this.dataObject=data;
	}
}
