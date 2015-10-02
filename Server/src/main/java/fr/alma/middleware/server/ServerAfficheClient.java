package fr.alma.middleware.server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import fr.alma.middleware.remote.InterfaceAffichageClient;

public class ServerAfficheClient extends UnicastRemoteObject implements InterfaceAffichageClient, Serializable {

	protected ServerAfficheClient() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void affiche(String Message) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getLogsContent(String logsFile) throws RemoteException {
		BufferedReader br = null;

		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader("logs/"+logsFile+".logs"));

			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return logsFile;
	}

}
