package happyhome.client.widgets;

import happyhome.client.jsoverlays.DataReader;
import happyhome.shared.AppConstants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class WOneLight extends Composite {
	interface MyUiBinder extends UiBinder<Widget, WOneLight> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	private String label, device;

	@UiField
	HTML caption, lightIcon;

	public WOneLight(String label, String device) {
		this.label = label;
		this.device = device;
		caption = new HTML();
		lightIcon = new HTML();
		initWidget(uiBinder.createAndBindUi(this));
		caption.setHTML(this.label);
		// tmr.schedule(1000);
	}

	private void changeLightState() {		
		try {
			JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
			jsonp.setTimeout(5000);

			String url = AppConstants.raspberryIp + "/ask4web/device/" + device + "/action/BEC";

			jsonp.requestObject(url, new AsyncCallback<DataReader>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert(caught.getMessage());
				}

				@Override
				public void onSuccess(DataReader result) {
					if (result.getStatus().equals("success")) {
						String styleNames = lightIcon.getStyleName();
						if (styleNames.contains("element-enabled")) {
							lightIcon.removeStyleName("element-enabled");
							lightIcon.addStyleName("element-disabled");
						} else {
							lightIcon.removeStyleName("element-disabled");
							lightIcon.addStyleName("element-enabled");
						}
					} else {
						Window.alert(result.getMessage());
					}
				}
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@UiHandler("btnLight")
	void onClickbtnDetails(ClickEvent e) {
		changeLightState();
	}
}