package happyhome.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class WTemperature extends Composite{
	interface MyUiBinder extends UiBinder<Widget, WTemperature> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	private final Timer tmr = new Timer(){

		@Override
		public void run() {			
			schedule(10000);
		}
	};
	
	public WTemperature(){
		initWidget(uiBinder.createAndBindUi(this));
		tmr.schedule(1000);
	}
	@Override
	public void onUnload(){		
		tmr.cancel();
	}
}
