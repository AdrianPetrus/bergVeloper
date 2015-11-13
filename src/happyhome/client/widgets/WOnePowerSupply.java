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

public class WOnePowerSupply extends Composite {
	interface MyUiBinder extends UiBinder<Widget, WOnePowerSupply> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	private String label, device;

	@UiField
	HTML caption, pwrIcon;

	public WOnePowerSupply(String label, String device) {
		this.label = label;
		this.device = device;
		caption = new HTML();
		pwrIcon = new HTML();
		initWidget(uiBinder.createAndBindUi(this));
		caption.setHTML(this.label);
		// tmr.schedule(1000);
	}

	private void changePwrState() {
		try {
			JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
			jsonp.setTimeout(5000);

			String url = AppConstants.raspberryIp + "/ask4web/device/" + device + "/action/PRZ";

			jsonp.requestObject(url, new AsyncCallback<DataReader>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert(caught.getMessage());
				}

				@Override
				public void onSuccess(DataReader result) {
					if (result.getStatus().equals("success")) {
						String styleNames = pwrIcon.getStyleName();
						if (styleNames.contains("element-enabled")) {
							pwrIcon.removeStyleName("element-enabled");
							pwrIcon.addStyleName("element-disabled");
						} else {
							pwrIcon.removeStyleName("element-disabled");
							pwrIcon.addStyleName("element-enabled");
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

	@UiHandler("btnSocket")
	void onClickbtnSocket(ClickEvent e) {
		changePwrState();
	}
}