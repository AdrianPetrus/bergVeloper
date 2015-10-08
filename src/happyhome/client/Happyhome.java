package happyhome.client;

import gwtSql.client.DBService;
import gwtSql.client.DBServiceAsync;
import gwtSql.client.controls.AlertWidget;
import gwtSql.client.forms.VForm;
import gwtSql.shared.DBRecord;
import happyhome.shared.TheApp;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Happyhome implements EntryPoint {

	private final HappyhomeServiceAsync hh = GWT.create(HappyhomeService.class);
	private final DBServiceAsync dbService = GWT.create(DBService.class);

	/**
	 * This is the entry point method.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void onModuleLoad() {

		// // test call happyHome service
		// hh.test("test", new AsyncCallback() {
		//
		// @Override
		// public void onFailure(Throwable caught) {
		// Window.alert(caught.getMessage());
		//
		// }
		//
		// @Override
		// public void onSuccess(Object result) {
		// Window.alert(result.toString());
		//
		// }
		// });

		String strIniFileName;
		strIniFileName = GWT.getHostPageBaseURL();
		// System.out.println(strIniFileName);
		// System.out.println(GWT.getModuleBaseURL());
		// getHostPageBaseURL return something like
		// http://server:port/application_Name/
		// and I need only application name
		int endIndex = strIniFileName.lastIndexOf("/");
		strIniFileName = strIniFileName.substring(0, endIndex);
		int beginIndex = strIniFileName.lastIndexOf("/");
		strIniFileName = strIniFileName.substring(beginIndex + 1);
		strIniFileName = strIniFileName.replace(":", "_") + ".ini";

		dbService.SetIniFileName(strIniFileName, new AsyncCallback() {
			@Override
			public void onFailure(Throwable caught) {
				AlertWidget.alertWidget("SetIniFileName  Fail  ").center();
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Object result) {
				// nothing
			}
		});

		// menu
		VForm M = new MainMenu();
		RootPanel.get("nav").add(M);
		TheApp._FORMS.put("MainMenu", M);

		// we initialize native JS. We call initJs from scripts.js
		initJs();

		// hide menu
		hidemenu();

		dbService.GetDBRecord("users", "username", "adip", new AsyncCallback<DBRecord>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert(caught.getMessage());
			}

			@Override
			public void onSuccess(DBRecord result) {
				// TODO Auto-generated method stub
				Window.alert(result.toString());
			}
		});

		TheApp.login();

	}

	private native void hidemenu()
	/*-{
		$doc.getElementById("nav").style.display = "none";
	}-*/;

	private native void initJs()
	/*-{
		$wnd.initJs();
	}-*/;

}
