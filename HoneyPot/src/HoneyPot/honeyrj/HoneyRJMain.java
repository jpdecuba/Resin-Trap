package HoneyPot.honeyrj;


import HoneyPot.lowinteraction.LIModule;
import HoneyPot.lowinteraction.LIProtocol;
import HoneyPot.protocol.FtpProtocol;
import HoneyPot.protocol.IrcProtocol;

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
		LIModule ftpM = new LIModule(ftpP);
		LIProtocol ircP = new IrcProtocol();
		LIModule ircM = new LIModule(ircP);
		honeyrj.RegisterService(ftpM);
		honeyrj.RegisterService(ircM);
		honeyrj.startPort(21);
		honeyrj.startPort(6667);

		System.out.println("started");





		System.out.println(ftpM.isStarted());

		honeyrj.PauseNewConnections(ftpM);

		System.out.println(ftpM.isStarted());
		honeyrj.ResumeNewConnections(ftpM);


		System.out.println(ftpM.isStarted());





	}
}
