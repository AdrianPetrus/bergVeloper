package happyhome.client.forms;

import gwtSql.client.forms.VForm;
import happyhome.client.widgets.WTemperature;
import happyhome.client.widgets.WTotalKW;
import happyhome.client.widgets.WTotalLight;
import happyhome.client.widgets.WTotalPowerSupply;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

/**
 * This class is resposible for generating the dashboard page
 * 
 * @author Adrian Petrus
 *
 */
public class DashBoard extends VForm {

	interface MyUiBinder extends UiBinder<Widget, DashBoard> {
	}
	//initialization of uiBinder
	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	//declaration of uiFields
	@UiField(provided = true)
	WTotalLight wTLight;
	@UiField(provided = true)
	WTotalPowerSupply wTSupply;
	@UiField(provided = true)
	WTemperature wTempRoom1;
	@UiField(provided = true)
	WTemperature wTempRoom2;
	@UiField(provided = true)
	WTotalKW wTotalKW;

	public DashBoard() {
		
		//initialization of ui widgets
		wTLight = new WTotalLight();
		wTSupply = new WTotalPowerSupply();
		wTempRoom1 = new WTemperature("temperatura camera 1", "esp1");
		wTempRoom2 = new WTemperature("temperatura camera 2", "esp2");
		wTotalKW = new WTotalKW();
		
		//initizalization of the actual page
		initWidget(uiBinder.createAndBindUi(this));
	}	
}