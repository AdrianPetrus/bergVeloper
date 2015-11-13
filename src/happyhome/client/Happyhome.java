package happyhome.client;

import gwtSql.client.DBService;
import gwtSql.client.DBServiceAsync;
import gwtSql.client.controls.AlertWidget;
import happyhome.shared.TheApp;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * This class is called when the application is executed.
 */
public class Happyhome implements EntryPoint {
	
	//serviciul de comunicare cu baza de date
	private final DBServiceAsync dbService = GWT.create(DBService.class);

	/**
	 * This is the entry point method.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void onModuleLoad() {

		String strIniFileName;
		strIniFileName = GWT.getHostPageBaseURL();

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

		TheApp.login();

	}
}
