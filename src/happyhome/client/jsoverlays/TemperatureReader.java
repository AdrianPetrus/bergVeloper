package happyhome.client.jsoverlays;

import com.google.gwt.core.client.JavaScriptObject;

public class TemperatureReader extends JavaScriptObject {

	protected TemperatureReader() {
		
	}

	public final native String getTemp() /*-{
		return this.temp;
	}-*/;

}