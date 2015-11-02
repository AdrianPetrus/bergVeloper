package happyhome.client.forms;

import gwtSql.client.forms.VForm;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class GroundFloor extends VForm {

	interface MyUiBinder extends UiBinder<Widget, GroundFloor> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	public GroundFloor() {
		initWidget(uiBinder.createAndBindUi(this));
		setTempJs();
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		loadPlan();
	}

	private native void loadPlan()
	/*-{
		$wnd.loadPlan();
	}-*/;

	public static void doAction(String id, String action) {
		Window.alert(id + " " + action);
	}

	public static native void setTempJs()/*-{
		$doc.doAction = @happyhome.client.forms.GroundFloor::doAction(*);
	}-*/;

}