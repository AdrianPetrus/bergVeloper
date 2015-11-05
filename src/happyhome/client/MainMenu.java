package happyhome.client;

import gwtSql.client.forms.VForm;
import happyhome.client.forms.AppSettings;
import happyhome.client.forms.BedroomOne;
import happyhome.client.forms.DashBoard;
import happyhome.client.forms.Environment;
import happyhome.client.forms.Garden;
import happyhome.client.forms.GroundFloor;
import happyhome.client.forms.Kitchen;
import happyhome.client.forms.Living;
import happyhome.client.forms.Resources;
import happyhome.client.forms.Sensors;
import happyhome.client.forms.Temperature;
import happyhome.client.forms.UserProfile;
import happyhome.shared.TheApp;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class MainMenu extends VForm {

	interface MyUiBinder extends UiBinder<Widget, MainMenu> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);	

	public MainMenu() {
		initWidget(uiBinder.createAndBindUi(this));
		
		final Timer tmr = new com.google.gwt.user.client.Timer() {
			
			@Override
			public void run() {
				InitMenuJs();				
			}
		};
		tmr.schedule(1000);
	}
	
	@UiHandler("btnDashboard")
	void onClickbtnDashboard(ClickEvent e) {
		VForm dashBoard = new DashBoard();
		RootPanel.get("page-wrapper").clear();
		RootPanel.get("page-wrapper").add(dashBoard);
	}		
	
	@UiHandler("btnParter")
	void onClickbtnParter(ClickEvent e) {
		VForm parter = new GroundFloor();
		RootPanel.get("page-wrapper").clear();
		RootPanel.get("page-wrapper").add(parter);
	}
	
	@UiHandler("btnSenzori")
	void onClickbtnSensors(ClickEvent e) {
		VForm senzori = new Sensors();
		RootPanel.get("page-wrapper").clear();
		RootPanel.get("page-wrapper").add(senzori);
	}
	
	@UiHandler("btnTemperature")
	void onClickbtnTemperature(ClickEvent e) {
		VForm temperatura = new Temperature();
		RootPanel.get("page-wrapper").clear();
		RootPanel.get("page-wrapper").add(temperatura);
	}
	
	@UiHandler("btnEnvironment")
	void onClickbtnEnvironment(ClickEvent e) {
		VForm env = new Environment();
		RootPanel.get("page-wrapper").clear();
		RootPanel.get("page-wrapper").add(env);
	}
	
	@UiHandler("btnResources")
	void onClickbtnResources(ClickEvent e) {
		VForm res = new Resources();
		RootPanel.get("page-wrapper").clear();
		RootPanel.get("page-wrapper").add(res);
	}
	
	@UiHandler("btnGarden")
	void onClickbtnGarden(ClickEvent e) {
		VForm garden = new Garden();
		RootPanel.get("page-wrapper").clear();
		RootPanel.get("page-wrapper").add(garden);
	}
	
	@UiHandler("btnProfil")
	void onClickbtnProfil(ClickEvent e) {
		VForm usrProfile = new UserProfile();
		RootPanel.get("page-wrapper").clear();
		RootPanel.get("page-wrapper").add(usrProfile);
	}
	
	@UiHandler("btnSettings")
	void onClickbtnSettings(ClickEvent e) {
		VForm appSettings = new AppSettings();
		RootPanel.get("page-wrapper").clear();
		RootPanel.get("page-wrapper").add(appSettings);
	}
	
	@UiHandler("btnLogout")
	void onClickbtnLogout(ClickEvent e) {
		// remove cookies
		Cookies.removeCookie("hh.Login");
		Cookies.removeCookie("hh.Password");

		// login
		TheApp.loginInfo.setLoggedIn(false);
		TheApp.login();
	}
	
	@UiHandler("btnKitchen")
	void onClickbtnBucatarie(ClickEvent e) {
		VForm kitchen = new Kitchen();
		RootPanel.get("page-wrapper").clear();
		RootPanel.get("page-wrapper").add(kitchen);
	}
	
	@UiHandler("btnLiving")
	void onClickbtnLiving(ClickEvent e) {
		VForm living = new Living();
		RootPanel.get("page-wrapper").clear();
		RootPanel.get("page-wrapper").add(living);
	}
	
	@UiHandler("btnBedroomOne")
	void onClickbtnBedroomOne(ClickEvent e) {
		VForm bOne = new BedroomOne();
		RootPanel.get("page-wrapper").clear();
		RootPanel.get("page-wrapper").add(bOne);
	}	

	private native void InitMenuJs()/*-{
		$wnd.InitMenu();
	}-*/;
}
