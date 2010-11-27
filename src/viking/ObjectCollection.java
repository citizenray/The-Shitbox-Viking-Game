package viking;

import java.util.List;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class ObjectCollection
{
	private List<GameObject> objects;
	private Comparator<GameObject> dataComparator;
	private Comparator<GameObject> renderComparator;
	
	public ObjectCollection()
	{
		this.objects=new LinkedList<GameObject>();
		this.dataComparator=new PositionComparator();
		this.renderComparator=new DepthComparator();
	}
	
	public void sortForData()
	{
		Collections.sort(this.objects,this.dataComparator);
	}
	
	public void sortForRender()
	{
		Collections.sort(this.objects,this.renderComparator);
	}
	
	public void add(GameObject obj)
	{
		this.objects.add(obj);
	}
	
	public void remove(GameObject obj)
	{
		this.objects.remove(obj);
	}
	
	public void purgeDestroyed()
	{
		Iterator<GameObject> iter=this.objects.iterator();
		while (iter.hasNext())
		{
			GameObject obj=iter.next();
			if (obj.isMarkedForDestruction())
			{
				this.objects.remove(obj);
			}
		}
	}
	
	public List<GameObject> getObjects()
	{
		return this.objects;
	}
	
	public void updatePositions()
	{
		for (GameObject object : this.objects)
		{
			object.updatePosition(this.objects);
		}
	}
	
	private class PositionComparator implements Comparator<GameObject>
	{
		public int compare(GameObject obj1,GameObject obj2)
		{
			if (obj1.getX()==obj2.getX())
			{
				return obj1.getY()-obj2.getY();
			}
			return obj2.getX()-obj1.getX();
		}
	}
	
	private class DepthComparator implements Comparator<GameObject>
	{
		public int compare(GameObject obj1,GameObject obj2)
		{
			return obj1.getDepth()-obj2.getDepth();
		}
	}
}
