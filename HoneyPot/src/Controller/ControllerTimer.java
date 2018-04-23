package Controller;



import java.util.TimerTask;

public class ControllerTimer extends TimerTask {

	private BaseController controller;



	public ControllerTimer(BaseController controller) { this.controller = controller; }


	@Override
	public void run() {
		controller.GetTotalConnections(false);
	}
}
