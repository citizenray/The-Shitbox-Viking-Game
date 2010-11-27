package viking;

import java.io.Serializable;
import java.awt.event.KeyEvent;

public class GamePrefs implements Serializable
{
	private int leftKey=KeyEvent.VK_A;
	private int rightKey=KeyEvent.VK_D;
	private int upKey=KeyEvent.VK_W;
	private int downKey=KeyEvent.VK_S;
	private int jumpKey=KeyEvent.VK_SPACE;
	private int fullscreenKey=KeyEvent.VK_F4;
	
	public int getLeftKey()
	{
		return this.leftKey;
	}
	
	public int getRightKey()
	{
		return this.rightKey;
	}
	
	public int getUpKey()
	{
		return this.upKey;
	}
	
	public int getDownKey()
	{
		return this.downKey;
	}
	
	public int getFullScreenKey()
	{
		return this.fullscreenKey;
	}
	
	public int getJumpKey()
	{
		return this.jumpKey;
	}
}
