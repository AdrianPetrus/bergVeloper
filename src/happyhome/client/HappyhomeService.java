package happyhome.client;



import gwtSql.shared.DBException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("HappyhomeService")
public interface HappyhomeService extends RemoteService {

	String test(String name) throws IllegalArgumentException, DBException;

}
