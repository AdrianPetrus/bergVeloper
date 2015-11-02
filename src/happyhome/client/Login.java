package happyhome.client;

import gwtSql.client.DBService;
import gwtSql.client.DBServiceAsync;
import gwtSql.client.controls.AlertWidget;
import gwtSql.client.forms.VForm;
import gwtSql.shared.DBRecord;
import happyhome.client.forms.Garden;
import happyhome.shared.TheApp;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class Login extends VForm {

	interface MyUiBinder extends UiBinder<Widget, Login> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	private final DBServiceAsync dbService = GWT.create(DBService.class);

	// Label lblAlias;
	@UiField(provided = true)
	TextBox textAlias;

	// Label lblPassword;
	@UiField(provided = true)
	PasswordTextBox textPassword;

	@UiField(provided = true)
	Button loginButton;

	@UiField(provided = true)
	HTML errorLabel;

	// constructor
	public Login() {
		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				DoLogin(null, null);
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					DoLogin(null, null);
				}
			}

		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();

		errorLabel = new HTML();

		loginButton = new Button();
		loginButton.setText("Login");

		textAlias = new TextBox();
		String strUserName = Cookies.getCookie("hh.Login");
		if (strUserName != null)
			if (!strUserName.isEmpty())
				textAlias.setText(strUserName);

		textPassword = new PasswordTextBox();

		textPassword.addKeyUpHandler(handler);
		textAlias.addKeyUpHandler(handler);
		loginButton.addClickHandler(handler);

		initWidget(uiBinder.createAndBindUi(this));

		/* if the username is set ... doLogin */
		String strAlias = textAlias.getText();
		if (!strAlias.isEmpty()) {
			String strPassword = Cookies.getCookie("hh.Password");
			if (strPassword != null) {
				DoLogin(strAlias, strPassword);
			} else {
				AlertWidget.alertWidget("No Username specified !").center();
			}
		}
	}

	/*
	 * functia principala de login citeste din baza de date si valideaza parola
	 */
	void DoLogin(String strAlias, String strPassword) {
		if (strAlias == null)
			strAlias = textAlias.getText();
		else
			textAlias.setText(strAlias);
		if (strPassword == null)
			strPassword = textPassword.getText();
		else
			textPassword.setText(strPassword);

		dbService.DoLogin("users", "USERNAME", strAlias, "PASSWORD", strPassword, new AsyncCallback<DBRecord>() {

			public void onSuccess(DBRecord result) {
				// setez pe TheApp
				try {

					if (result.tableName.isEmpty()) {
						// alias gresit
						TheApp.loginInfo.setLoggedIn(false);
						String errorMsg = "Wrong username and password. Please fill the form with the correct user - password combination !!!";
						errorLabel.setHTML("<div class=\"messages\"><ul><li class=\"sad\">" + errorMsg + "</li></ul></div>");
					} else {
						TheApp.loginInfo.setLoggedIn(true);
						TheApp.loginInfo.User = result;
						TheApp.loginInfo.sqlServerName = result.getString("DBConnection.sqlServerName");
						TheApp.loginInfo.sqlDatabase = result.getString("DBConnection.sqlDatabase");
						TheApp.loginInfo.sqlSufix = result.getString("DBConnection.sqlSufix");
						TheApp.loginInfo.sqlIDFirma = result.getString("DBConnection.sqlIDFirma");

						// save values
						Date now = new Date();
						long nowLong = now.getTime();
						nowLong = nowLong + (1000 * 60 * 60 * 24 * 7);// seven days
						now.setTime(nowLong);
						Cookies.setCookie("hh.Login", textAlias.getText(), now);
						Cookies.setCookie("hh.Password", textPassword.getText(), now);
						
						VForm menu = new MainMenu();
						RootPanel.get("page-wrapper").clear();
						RootPanel.get("wrapper").clear();
						RootPanel.get("wrapper").add(menu);

						
					}
				} catch (Exception e) {
					System.out.println(e.toString());
				}
			}

			public void onFailure(Throwable caught) {
				AlertWidget.alertWidget("Login getDBrecord Fail  ").center();
				caught.printStackTrace();
			}

		});

	}

	// private native void HideControl(String id)
	// /*-{
	// for (i = 1; i < 5; i++) {
	// id1 = id + "_" + i;
	//
	// if ($doc.getElementById(id1) != null) {
	//
	// $doc.getElementById(id1).style.display = "none";
	// }
	// }
	// }-*/;

}
