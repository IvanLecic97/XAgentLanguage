package agents;

import javax.ejb.Singleton;

import model.AID;

@Singleton
public abstract class AgentClass implements AgentInterface {

	private AID id;
	
	public AID getAid() {
		return id;
	}

	public void setAid(AID aid) {
		this.id = aid;
	}
	
}
