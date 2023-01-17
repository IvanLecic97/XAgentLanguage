package agents;

import javax.ejb.Singleton;

import model.AID;

@Singleton
public abstract class AgentClass implements AgentInterface {

	private AID aid;

	public AID getAid() {
		return aid;
	}

	public void setAid(AID aid) {
		this.aid = aid;
	}
	
	
	
	
}
