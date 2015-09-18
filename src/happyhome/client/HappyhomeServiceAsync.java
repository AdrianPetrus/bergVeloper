package happyhome.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface HappyhomeServiceAsync {

	void test(String input, AsyncCallback<String> callback) throws IllegalArgumentException;

}