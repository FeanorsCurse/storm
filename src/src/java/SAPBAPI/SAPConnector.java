package SAPBAPI;
import com.sap.mw.jco.*;

public class SAPConnector {
	JCO.Client mConnection;

	IRepository mRepository;

	public SAPConnector() {

	}

	public void connectionAttributes() {
		System.out.println(mConnection.getAttributes());
	}
	
	public void execute(JCO.Function function){
		mConnection.execute(function);
	}

	public boolean openConnectionToSAP(String clientSAP, String login,
			String passwort, String language, String server, String systemNumber) {
		try {
			mConnection = JCO.createClient(clientSAP, login, passwort,
					language, server, systemNumber);

			mConnection.connect();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public void closeConnectionToSAP() {
		mConnection.disconnect();
	}

	public JCO.Function createFunction(String name) throws Exception {
		try {
			mRepository = new JCO.Repository("myRepository", mConnection);
			IFunctionTemplate ft = mRepository.getFunctionTemplate(name
					.toUpperCase());
			if (ft == null)
				return null;
			return ft.getFunction();
		} catch (Exception ex) {
			throw new Exception(
					"The JCO.Function-Objekt connot be created.");
		}
	}



}// Ende der Klasse
