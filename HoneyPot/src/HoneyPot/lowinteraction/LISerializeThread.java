package HoneyPot.lowinteraction;

import HoneyPot.logging.LogConnection;
import com.sun.corba.se.impl.orbutil.ObjectWriter;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class LISerializeThread implements Runnable {

	private LogConnection log;
	private String path;
	private String fileName;

	public LISerializeThread (LogConnection log)
	{
		this.log = log;
		this.path = System.getenv("APPDATA") + "/Honeypot/";
		new File(path).mkdirs();
		this.fileName = "AllLogs.txt";
	}

	@Override
	public void run()
	{
		try {
			File file = new File(path + fileName);
			if(!file.exists())
			{
				file.createNewFile();
			}

			OutputStream outputStream = new FileOutputStream(this.path + this.fileName, true);
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
			ObjectOutputStream objectWriter = new ObjectOutputStream(bufferedOutputStream);


			objectWriter.writeObject(this.log);
			objectWriter.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {

			Thread.currentThread().interrupt();
		}
	}
}
