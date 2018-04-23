package Client.HoneyPot.honeyrj;


import Client.HoneyPot.lowinteraction.LIModule;
import Client.HoneyPot.lowinteraction.LIProtocol;
import Client.HoneyPot.protocol.BlankProtocol;
import Client.HoneyPot.protocol.FtpProtocol;
import Client.HoneyPot.protocol.IrcProtocol;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class HoneyRJMain {

	private static HoneyRJ honeyrj = null;

	public static void main(String[] args) {
		launchRJ();
		createSampleProtocols();
	}
	
	private static void launchRJ() {

		try {
			honeyrj = new HoneyRJ();
		} catch (HoneyRJException e) {
			System.err.println("Failed to create logging directory...Exiting");
			System.exit(-1);
		}

	}

	private static void createSampleProtocols() {
		LIProtocol ftpP = new FtpProtocol();
		LIModule ftpM = new LIModule(ftpP,honeyrj);
		LIProtocol ircP = new IrcProtocol();
		LIModule ircM = new LIModule(ircP,honeyrj);

		BlankProtocol blank = new BlankProtocol(53);
		LIModule blankm = new LIModule(blank,honeyrj);
		honeyrj.RegisterService(ftpM);
		honeyrj.RegisterService(ircM);
		honeyrj.startPort(21);
		honeyrj.startPort(ircM.getPort());

		System.out.println("started");








/*		File file = new File(System.getenv("APPDATA") + "/Honeypot/AllLogs.txt");
		if(file.exists())
		{
			try {
				ExecutorService executorService = Executors.newSingleThreadExecutor();
				Future<Set<LogConnection>> future = executorService.submit(new LIDeserializeThread());
				Set<LogConnection> logs = future.get();
				for (LogConnection log : logs)
				{
					System.out.println(log.message() + "\n\n");
				}
				System.out.println("Eind");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}

		}*/



		/*LinkedList<LogConnection> list = new LinkedList<>(honeyrj.getLogs());
		System.out.println(list.get(0).message());
		System.out.println(list.get(0).getDate());*/






	}
}
