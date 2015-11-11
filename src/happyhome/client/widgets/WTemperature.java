package happyhome.client.widgets;

import happyhome.client.jsoverlays.DataReader;

import com.google.gwt.core.client.GWT;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class WTemperature extends Composite {
	interface MyUiBinder extends UiBinder<Widget, WTemperature> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	@SuppressWarnings("unused")
	private String label = "temperature", device = "esp1";
	private final Timer tmr = new Timer() {

		@Override
		public void run() {
			executeRead();			
			schedule(10000);
		}
	};
	
	@UiField HTML tempValue, labelHtml;

	public WTemperature(String label, String device) {
		this.label = label;
		this.device = device;
		tempValue = new HTML();		
		labelHtml = new HTML();
		
		initWidget(uiBinder.createAndBindUi(this));
		tempValue.setHTML("Loading...");
		labelHtml.setHTML(label);
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
	
	private void executeRead() {
		try {
			JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
			jsonp.setTimeout(5000);

			String url = "http://192.168.44.200:8080/api/ask4web/device/" + device + "/action/TMP";

			jsonp.requestObject(url, new AsyncCallback<DataReader>() {				

				@Override
				public void onFailure(Throwable caught) {
					Window.alert(caught.getMessage());
				}

				@Override
				public void onSuccess(DataReader result) {
					if (result.getStatus().equals("success")) {						
						tempValue.setHTML(result.getValue() + "&#176;");
					} else {
						Window.alert(result.getMessage());
					}
				}
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
