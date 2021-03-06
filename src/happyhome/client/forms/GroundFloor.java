package happyhome.client.forms;

import gwtSql.client.forms.VForm;
import happyhome.client.jsoverlays.DataReader;
import happyhome.shared.AppConstants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

/**
 * This class will generate the ground floor page
 * 
 * @author Adrian Petrus
 *
 */
public class GroundFloor extends VForm {

	interface MyUiBinder extends UiBinder<Widget, GroundFloor> {
	}

	// initialization of uiBinder
	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	private final Timer tmr = new Timer() {

		@Override
		public void run() {
			getState();
			schedule(5000);
		}
	};

	public GroundFloor() {
		initWidget(uiBinder.createAndBindUi(this));
		setTempJs();
		tmr.schedule(1000);
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		loadPlan();
	}
	
	@Override
	public void onUnload() {
		tmr.cancel();
	}

	/**
	 * Responsable for loading the plan on the page
	 */
	private native void loadPlan()
	/*-{
		$wnd.loadPlan();
	}-*/;

	/**
	 * This function will be triggerred from the client side to execute the
	 * actual async call to raspberry
	 * 
	 * @param id
	 *            The id of the element that is being pressed
	 * @param action
	 *            The action that will be sent to RaspBerry
	 */
	public static void doAction(String id, String action) {
		try {
			JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
			jsonp.setTimeout(5000);

			String url = AppConstants.raspberryIp + "/ask4web/device/esp1/action/" + action;

			jsonp.requestObject(url, new AsyncCallback<DataReader>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert(caught.getMessage());
				}

				@Override
				public void onSuccess(DataReader result) {
					if (result.getStatus().equals("success")) {
						GWT.log(result.getValue());
					} else {
						Window.alert(result.getMessage());
					}
				}
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This function will update the appearance of plan icons
	 * 
	 */

	private void getState() {
		try {
			JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
			jsonp.setTimeout(5000);

			String url = AppConstants.raspberryIp + "/ask4web/device/esp1/action/STIR";

			jsonp.requestObject(url, new AsyncCallback<DataReader>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert(caught.getMessage());
				}

				@Override
				public void onSuccess(DataReader result) {
					if (result.getStatus().equals("success")) {
						if (result.getValue().equals("OFF")) {
							changeStateJs("disabled", "lock1");
						} else {
							changeStateJs("enabled", "lock1");
						}
					} else {
						Window.alert(result.getMessage());
					}
				}
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This function will expose the GWT doAction function to javascript
	 * 
	 */
	public static native void setTempJs()/*-{
		$doc.doAction = @happyhome.client.forms.GroundFloor::doAction(*);
	}-*/;

	/**
	 * This function will change an elements state
	 * 
	 */
	public static native void changeStateJs(String status, String id)/*-{
		$wnd.toggleState(status, id);
	}-*/;

}