package happyhome.client.forms;

import gwtSql.client.forms.VForm;
import happyhome.client.jsoverlays.DataReader;
import happyhome.client.widgets.WTemperature;
import happyhome.client.widgets.WTotalKW;
import happyhome.client.widgets.WTotalLight;
import happyhome.client.widgets.WTotalPowerSupply;

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

public class DashBoard extends VForm {

	interface MyUiBinder extends UiBinder<Widget, DashBoard> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	@UiField(provided = true)
	WTotalLight wTLight;
	@UiField(provided = true)
	WTotalPowerSupply wTSupply;
	@UiField(provided = true)
	WTemperature wTemp;
	@UiField(provided = true)
	WTotalKW wTotalKW;

	public DashBoard() {

		wTLight = new WTotalLight();
		wTSupply = new WTotalPowerSupply();
		wTemp = new WTemperature();
		wTotalKW = new WTotalKW();
		initWidget(uiBinder.createAndBindUi(this));
		readTemp();

	}

	private void readTemp() {
		try {
			JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
			jsonp.setTimeout(5000);

			String url = "http://192.168.44.200:8080/api/ask4web/device/esp3/action/ST";

			jsonp.requestObject(url, new AsyncCallback<DataReader>() {
				@Override
				public void onFailure(Throwable caught) {
					Window.alert(caught.getMessage());
				}

				@Override
				public void onSuccess(DataReader result) {
					if (result.getStatus().equals("success")) {						
						
						Window.alert(result.getStatus() + " " + result.getValue() + " " + result.getRawData() + " " + result.getDeviceName() + " "
								+ result.getMessage());
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