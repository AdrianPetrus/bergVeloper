package happyhome.server;

import gwtSql.shared.DBException;
import happyhome.client.HappyhomeService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class HappyhomeServiceImpl extends RemoteServiceServlet implements HappyhomeService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5218983103310946540L;

	public String test(String t) throws DBException {

		return "Me ... Happy at Home !";
	}


}
