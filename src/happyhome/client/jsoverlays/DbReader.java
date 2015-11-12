package happyhome.client.jsoverlays;

import com.google.gwt.core.client.JavaScriptObject;

public class DbReader extends JavaScriptObject {

	protected DbReader() {

	}

	public final native String getStatus() /*-{
		return this.status;
	}-*/;

	public final native JsArray<JavaScriptObject> getData() /*-{
		return this.data;
	}-*/;	

	public final native String getDeviceName() /*-{
		return this.deviceName;
	}-*/;

	public final native String getMessage() /*-{
		return this.message;
	}-*/;

}