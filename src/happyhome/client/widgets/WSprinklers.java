package happyhome.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class WSprinklers extends Composite {
	interface MyUiBinder extends UiBinder<Widget, WSprinklers> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	private final Timer tmr = new Timer() {

		@Override
		public void run() {
			//TODO get reading from db
			schedule(10000);
		}
	};

	public WSprinklers() {
		initWidget(uiBinder.createAndBindUi(this));
		tmr.schedule(1000);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.user.client.ui.Widget#onUnload()
	 * 
	 * This method is called when the widget is destroyed (leave the page).
	 * Because this widget is refreshing to update data, we need to stop the
	 * timer when the widget is not used.
	 */

	@Override
	public void onUnload() {
		tmr.cancel();
	}
}
