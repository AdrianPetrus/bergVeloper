package happyhome.client.forms;

import gwtSql.client.forms.VForm;
import happyhome.client.widgets.WTemperatureChart;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

public class Room1 extends VForm {

	interface MyUiBinder extends UiBinder<Widget, Room1> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);	
	
	@UiField(provided = true)
	WTemperatureChart wTChart;
	
	public Room1(){
		wTChart = new WTemperatureChart("esp1", "Camera 1");
		initWidget(uiBinder.createAndBindUi(this));		
	}
	
	
}