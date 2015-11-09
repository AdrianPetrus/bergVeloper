package happyhome.client.forms;

import gwtSql.client.forms.VForm;
import happyhome.client.widgets.WSprinklers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

public class Garden extends VForm {

	interface MyUiBinder extends UiBinder<Widget, Garden> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	
	@UiField(provided=true) WSprinklers wSprinkler1;
	
	public Garden(){
		wSprinkler1 = new WSprinklers();
		initWidget(uiBinder.createAndBindUi(this));
	}
}