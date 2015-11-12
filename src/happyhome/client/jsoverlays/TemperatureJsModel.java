package happyhome.client.jsoverlays;

import com.google.gwt.core.client.JavaScriptObject;

public class TemperatureJsModel extends JavaScriptObject {
	protected TemperatureJsModel() {
	}

	public final native String getName() /*-{
		return this.name;
	}-*/;

	public final native String sensorId() /*-{
		return this.sensor_id;
	}-*/;

	public final native String getDateTime() /*-{
		return this.datetime;
	}-*/;

	public final native String getValue() /*-{
		return this.value;
	}-*/;

}