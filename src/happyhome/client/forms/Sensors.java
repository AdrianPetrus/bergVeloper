package happyhome.client.forms;

import gwtSql.client.forms.VForm;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class Sensors extends VForm {

	interface MyUiBinder extends UiBinder<Widget, Sensors> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	@SuppressWarnings("unused")
	private ArrayList<Widget> devices;

	@UiField
	HTMLPanel content;

	public Sensors(ArrayList<Widget> devices) {
		this.devices = devices;
		content = new HTMLPanel("");

		initWidget(uiBinder.createAndBindUi(this));
		for(int i = 0; i < devices.size(); i++){
			content.add(devices.get(i));			
		}

	}
}