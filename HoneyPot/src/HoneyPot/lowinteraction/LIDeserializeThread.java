package HoneyPot.lowinteraction;

import HoneyPot.logging.LogConnection;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

public class LIDeserializeThread implements Callable<Set<LogConnection>> {

	@Override
	public Set<LogConnection> call() throws Exception {
		Set<LogConnection> logs = new HashSet<>();
		ObjectInputStream objectReader = null;
		try
		{
			InputStream inputStream = new FileInputStream(System.getenv("APPDATA") + "/Honeypot/AllLogs.txt");
			BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
			objectReader = new ObjectInputStream(bufferedInputStream);

			Object object = objectReader.readObject();
			while (object != null)
			{
				logs.add((LogConnection)object);

				objectReader = new ObjectInputStream(bufferedInputStream);
				object = objectReader.readObject();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				objectReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return logs;
	}
}
