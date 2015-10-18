package happyhome.client.forms;

import gwtSql.client.forms.VForm;
import happyhome.client.widgets.WTemperature;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

public class DashBoard extends VForm {

	interface MyUiBinder extends UiBinder<Widget, DashBoard> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);	
	
	@UiField(provided=true) WTemperature temp;

	public DashBoard() {
		
		temp = new WTemperature();

		initWidget(uiBinder.createAndBindUi(this));

	}	
}