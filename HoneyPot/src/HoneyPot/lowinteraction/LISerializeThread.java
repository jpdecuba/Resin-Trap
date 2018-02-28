package HoneyPot.lowinteraction;

import HoneyPot.logging.LogConnection;

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

			OutputStream outputStream = new FileOutputStream(this.path + this.fileName);
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
