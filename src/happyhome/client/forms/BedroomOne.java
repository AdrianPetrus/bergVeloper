package happyhome.client.forms;

import gwtSql.client.forms.VForm;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;

public class BedroomOne extends VForm {

	interface MyUiBinder extends UiBinder<Widget, BedroomOne> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	
	public BedroomOne(){
		initWidget(uiBinder.createAndBindUi(this));
	}
}