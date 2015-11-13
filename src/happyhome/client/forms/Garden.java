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
	
	@UiField(provided=true) WSprinklers wSprinklers;
	
	public Garden(){
		wSprinklers = new WSprinklers();		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	protected void onLoad() {
		super.onLoad();
		loadSprinkler1();		
		loadSprinkler2();
	}

	private native void loadSprinkler1()
	/*-{		
		$wnd.loadSprinkler1();		
	}-*/;
	private native void loadSprinkler2()
	/*-{		
		$wnd.loadSprinkler2();		
	}-*/;
}