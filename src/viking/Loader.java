package viking;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class Loader
{
	public static final String PREFS_FILENAME = "settings.thor";
	
	public static Image getImage(String filename)
	{
		return Toolkit.getDefaultToolkit().getImage(filename);
	}
	
	public static GamePrefs loadPrefs()
	{
		GamePrefs prefs=new GamePrefs();
		File f=new File(PREFS_FILENAME);
		if (f.exists())
		{
			try
			{
				ObjectInputStream ois=new ObjectInputStream(new FileInputStream(f));
				prefs=(GamePrefs)ois.readObject();
				ois.close();
			}
			catch (IOException e)
			{
			}
			catch (ClassNotFoundException e)
			{
			}
		}
		return prefs;
	}
	
	public static void savePrefs(GamePrefs prefs)
	{
		File f=new File(PREFS_FILENAME);
		try
		{
			ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(prefs);
			oos.close();
		}
		catch (IOException e)
		{
		}
	}
}
