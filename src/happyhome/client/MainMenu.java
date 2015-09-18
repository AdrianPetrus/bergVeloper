package happyhome.client;

import gwtSql.client.forms.VForm;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;

public class MainMenu extends VForm {

	interface MyUiBinder extends UiBinder<Widget, MainMenu> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	
	//@UiField Anchor Login;, Colaboratori, Comenzi, Oferte, Facturi, RaportGestiuneFN, RapGestComclFacturabil, Users;

//	private final BCHRServiceAsync BCHR = GWT.create(BCHRService.class);

	MainMenu() {

		initWidget(uiBinder.createAndBindUi(this));

	}

	/**
	 * 
	 * @param c
	 */
//	@UiHandler("Overview_1")
//	public static void OnClickOverview_1(ClickEvent c) {
//		// calendar - always create
//		// cause of scroll
//		VForm B;
//		// B = TheApp._FORMS.get("FrmCalendar");
//		// if (B == null) {
//		B = new Login();
//		// TheApp._FORMS.put("FrmCalendar", B);
//		// }
//		RootPanel.get("page-body").clear();
//		RootPanel.get("page-body").add(B);
//	}

	

}
