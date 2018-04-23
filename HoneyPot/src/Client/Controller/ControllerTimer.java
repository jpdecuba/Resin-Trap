package Client.Controller;

import javafx.application.Platform;

import java.util.TimerTask;

public class ControllerTimer extends TimerTask {

	private BaseController controller;
	private boolean overview;



	public ControllerTimer(BaseController controller) {
		this.controller = controller;
		if(this.controller instanceof OverviewController)
		{
			overview = true;
		}
	}


	@Override
	public void run() {

		int connections = controller.GetTotalConnections();
		OverviewController overviewController = (OverviewController)controller;
		if(overview)
		{
			Platform.runLater(()-> {
				overviewController.statusLbl.setText(overviewController.StatusCheck().toString());
				overviewController.connectionsLbl.setText(String.valueOf(connections));
				overviewController.threatLbl.setText(overviewController.getDatelastlog());
				overviewController.timeframeLbl.setText(String.valueOf(overviewController.Timeframes()));
			});
		}
	}
}
