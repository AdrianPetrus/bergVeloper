package happyhome.client.forms;

import gwtSql.client.forms.VForm;
import happyhome.client.widgets.TemperatureChart;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

public class DashBoard extends VForm {

	interface MyUiBinder extends UiBinder<Widget, DashBoard> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);	
	
	@UiField(provided=true) TemperatureChart tChart;

	public DashBoard() {
		
		tChart = new TemperatureChart();
		initWidget(uiBinder.createAndBindUi(this));

	}	
}