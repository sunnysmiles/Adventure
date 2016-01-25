package engine.server;

import java.util.ArrayList;

import engine.network.ClientPacket;
import engine.network.ServerPacket;

public abstract class AbstractServerPlayer {
	
	protected PlayerConnection connection;

	public AbstractServerPlayer(PlayerConnection con, int id){
		this.connection = con;
	}
	
	public ArrayList<ServerPacket> getRecievedPackts(){
		return connection.getRecievedPackets();
	}
	
	public void clearRecievedPackets(){
		connection.clearRecievedPackets();
	}

	public void stack(ClientPacket p){
		connection.stack(p);
	}
}
