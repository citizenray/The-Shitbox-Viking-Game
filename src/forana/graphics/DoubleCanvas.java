/**
 * DoubleCanvas.java
 * (c) 2010 Alex Foran (alex@alexforan.com)
 * 
 * If you end up using this in something, please give me some credit somewhere.
 */

package forana.graphics;

import java.awt.Graphics;
import java.awt.Canvas;
import java.awt.image.VolatileImage;

/**
 * Provides a double-buffered canvas that also handles the updating of data at
 * a given time interval. In short, this class does far too much work, but it's
 * convenient for simple games.
 * 
 * Typical DoubleCanvas usage would proceed as follows:
 * 
 * <code>DoubleCanvas canvas=new DoubleCanvas(someRenderObject,someDataObject,someWidth,someHeight,someDelay);</code>
 * <code>... other setup code ...</code>
 * <code>canvas.start();</code>
 * 
 * @author forana
 */
public class DoubleCanvas extends Canvas
{
	private static final long serialVersionUID=1L;
	
	// The back buffer
	private VolatileImage bufferImage;
	
	// The renderer (V of MVC)
	private RenderObject renderObject;
	
	// The data (C of MVC, presumably knows how to contact the M)
	private DataObject dataObject;
	
	// Thread that handles updates
	private Thread updateThread;
	
	// Thread that handles drawing
	private Thread drawThread;
	
	// Delay between update() calls
	private int updateDelay;
	
	// Mutex for this class; shouldn't use the class object, yknow
	private Object drawMutex;
	
	// The width of this canvas
	private int width;
	
	// The height of this canvas
	private int height;
	
	// Whether or not the canvas has been shown yet;
	private boolean canvasShown;
	
	//private long vsyncFrameWidth;
	//private long currentTime;
	//private long lastDraw;
	
	/**
	 * Creates a new DoubleCanvas with specified objects, dimensions, and delay.
	 */
	public DoubleCanvas(RenderObject renderObject,DataObject dataObject,int width,int height,int updateDelay)
	{
		this.renderObject=renderObject;
		this.dataObject=dataObject;
		// start the buffer as null; we'll figure it out later
		this.bufferImage=null;
		this.width=width;
		this.height=height;
		this.updateDelay=updateDelay;
		this.drawMutex=new Object(); // who cares what this really is, any Object will work
		//this.vsyncFrameWidth=1000000000;
		//this.currentTime=0;
		//this.lastDraw=0;
		
		// canvas hasn't been shown yet and therefore can't do a few nifty tricks
		this.canvasShown=false;
		
		// create the threads but don't start them
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
						// synchronize to avoid seeing awkward conditions
						synchronized (drawMutex)
						{
							dataObject.update();
						}
						// java canvas likes focus
						// gimme dem focus
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
						// this theoretically caps us at 100 fps, good thing the human eye can't really tell
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
		// if you don't give the drawing thread priority within the program, strange things happen
		// ok, they aren't that strange, but it looks better this way dammit
		this.drawThread.setPriority(Thread.MAX_PRIORITY);
	}
	
	/**
	 * Start the threads.
	 */
	public void start()
	{
		this.updateThread.start();
		this.drawThread.start();
	}
	
	/**
	 * Stop the threads. They can be started again with no consequence, but should be stopped before program exits.
	 */
	public void stop()
	{
		this.updateThread.interrupt();
		this.drawThread.interrupt();
	}
	
	/**
	 * Draw all sorts of things.
	 */
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
			// we loop here because sometimes it takes multiple tries to draw the image
			do
			{
				int val=this.bufferImage.validate(this.getGraphicsConfiguration());
				
				// if the image wasn't "valid" then we need to remake that buffer
				if (val==VolatileImage.IMAGE_INCOMPATIBLE)
				{
					this.bufferImage=this.getGraphicsConfiguration().createCompatibleVolatileImage(width,height);
				}
				
				// render to the buffer
				this.renderObject.render(this.bufferImage.getGraphics());
				
				// draw the buffer as fast as we can
				g.drawImage(this.bufferImage,0,0,this.getWidth(),this.getHeight(),this);
			}
			while (this.bufferImage.contentsLost());
		}
	}
	
	public void update(Graphics g)
	{
		this.paint(g);
	}
	
	/**
	 * Set a new render object.
	 * 
	 * @param render
	 */
	public void setRender(RenderObject render)
	{
		this.renderObject=render;
	}
	
	/**
	 * Set a new data object.
	 * 
	 * @param data
	 */
	public void setData(DataObject data)
	{
		this.dataObject=data;
	}
}
