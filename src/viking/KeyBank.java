package viking;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.Set;
import java.util.TreeSet;

public class KeyBank implements KeyListener
{
	public static final int NO_KEY = -1;
	private Set<Integer> keys;
	private Set<Integer> newKeys;
	private int lastKey;
	
	public KeyBank()
	{
		this.keys=new TreeSet<Integer>();
		this.newKeys=new TreeSet<Integer>();
		this.lastKey=NO_KEY;
	}
	
	public void keyPressed(KeyEvent e)
	{
		int key=e.getKeyCode();
		if (this.keys.contains(key))
		{
			this.newKeys.remove(key);
		}
		else
		{
			this.newKeys.add(key);
			this.keys.add(key);
		}
		this.lastKey=key;
	}
	
	public void keyReleased(KeyEvent e)
	{
		int key=e.getKeyCode();
		if (this.keys.contains(key))
		{
			this.newKeys.add(key);
			
			this.keys.remove(key);
		}
		else
		{
			this.newKeys.remove(key);
		}
	}
	
	public void keyTyped(KeyEvent e) {}
	
	public void wipeKeys()
	{
		this.newKeys=new TreeSet<Integer>();
		this.lastKey=NO_KEY;
	}
	
	public int getLastKey()
	{
		return this.lastKey;
	}
	
	public static String getKeyName(int key)
	{
		return KeyEvent.getKeyText(key);
	}
	
	public boolean getKeyDown(int key)
	{
		return this.keys.contains(key);
	}
	
	public boolean getStateNew(int key)
	{
		return this.newKeys.contains(key);
	}
}
