package happyhome.shared;

import gwtSql.client.LoginInfo;
import gwtSql.client.forms.LoadForm;
import gwtSql.client.forms.VForm;
import happyhome.client.Login;
import happyhome.client.MainMenu;

import java.util.HashMap;

import com.google.gwt.user.client.ui.RootPanel;


public class TheApp {


	final public static LoginInfo loginInfo = new LoginInfo();

	final public static String iniFileName = "";

	static LoadForm loadForm = null;
	static int loadFormCounter;

	@SuppressWarnings("rawtypes") 
	final public static HashMap _VAR = new HashMap();

	final public static HashMap<String, VForm> _FORMS = new HashMap<String, VForm>();

	public static void login() {

		if (!loginInfo.isLoggedIn()) {
			// do the login
			// Create the login dialog box
//			Login L = new Login();
//			RootPanel.get("page-wrapper").clear();
//			RootPanel.get("page-wrapper").add(L);
			
			VForm f = new MainMenu();
			RootPanel.get("wrapper").clear();
			RootPanel.get("wrapper").add(f);
		}
	}

	// apel cu un parametru
	public static String _VAR(String strKey) {
		return _VAR(strKey, "");
	}

	// apel cu 2 parametrii
	@SuppressWarnings("unchecked")
	public static String _VAR(String strKey, String strValue) {

		String strRetVal = (String) _VAR.get(strKey);

		if (strRetVal == null)
			_VAR.put(strKey, strValue);

		if (strRetVal == "")
			_VAR.put(strKey, strValue);

		strRetVal = (String) _VAR.get(strKey);

		strRetVal = strRetVal.trim();

		return strRetVal;
	}

	public static LoginInfo GetLoginInfo() {
		return TheApp.loginInfo;
	}

	public static void StartLoading() {
		if (loadForm == null) {
			loadForm = new LoadForm();
			loadFormCounter = 0;
		}
		loadForm.center();
		loadForm.show();
		loadFormCounter++;
		loadForm.Counter(loadFormCounter);
	}

	public static void StopLoading() {
		if (loadForm != null) {
			loadFormCounter--;
			if (loadFormCounter == 0)
				loadForm.hide();
		}

	}

}
