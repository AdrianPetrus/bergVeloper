package happyhome.client.widgets;

import gwtSql.client.forms.VForm;
import happyhome.client.forms.Sensors;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class WTotalPowerSupply extends Composite {
	interface MyUiBinder extends UiBinder<Widget, WTotalPowerSupply> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	private ArrayList<Widget> devices = new ArrayList<Widget>();

	@UiField
	HTML socketsOn;

	private final Timer tmr = new Timer() {

		@Override
		public void run() {
			socketsOn.setHTML("1");
			schedule(10000);
		}
	};

	public WTotalPowerSupply() {
		initWidget(uiBinder.createAndBindUi(this));
		socketsOn.setHTML("Loading...");
		tmr.schedule(1000);
		devices.add(new WOnePowerSupply("Camera1", "esp1"));
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

	@UiHandler("btnDetails")
	void onClickbtnDetails(ClickEvent e) {
		VForm form = new Sensors(devices);
		RootPanel.get("page-wrapper").clear();
		RootPanel.get("page-wrapper").add(form);
	}
}
