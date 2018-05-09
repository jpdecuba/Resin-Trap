package Client.HoneyPot.lowinteraction;

import Client.HoneyPot.logging.LogConnection;

import java.io.*;

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

	public void SerializeLogToFile(){
		try {
			File file = new File(path + fileName);

			if(!file.exists()) {
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
	}

	@Override
	public void run() {
		try{
			SerializeLogToFile();
		}
		finally {
			Thread.currentThread().interrupt();
		}
	}
}
