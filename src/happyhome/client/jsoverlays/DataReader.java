package happyhome.client.jsoverlays;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;

public class DataReader extends JavaScriptObject {

	protected DataReader() {

	}

	public final native String getStatus() /*-{
		return this.status;
	}-*/;

	public final native String getValue() /*-{
		return this.value;
	}-*/;

	public final native JSONObject getRawData() /*-{
		return this.rawData;
	}-*/;

	public final native String getDeviceName() /*-{
		return this.deviceName;
	}-*/;

	public final native String getMessage() /*-{
		return this.message;
	}-*/;

}