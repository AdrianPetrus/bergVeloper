package happyhome.client.jsoverlays;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;

public class DbReader extends JavaScriptObject {

	protected DbReader() {

	}

	public final native String getStatus() /*-{
		return this.status;
	}-*/;

	public final native JSONArray getData() /*-{
		return this.value;
	}-*/;	

	public final native String getDeviceName() /*-{
		return this.deviceName;
	}-*/;

	public final native String getMessage() /*-{
		return this.message;
	}-*/;

}