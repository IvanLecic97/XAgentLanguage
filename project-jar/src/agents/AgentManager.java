package agents;

import java.util.HashMap;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import model.AID;
import model.User;

@Singleton
@LocalBean
@Startup
public class AgentManager {
	
	private HashMap<String, Agent> agents;
	private HashMap<AID, ResearchAgent> researchAgents;
	private HashMap<AID, ResearchAgent> runningResearchAgents;
	
	public AgentManager() {
		this.agents = new HashMap<String, Agent>();
		this.researchAgents = new HashMap<AID, ResearchAgent>();
		this.runningResearchAgents = new HashMap<AID, ResearchAgent>();
	}

	
	public void createNewAgent(String username, User user) {
		this.agents.put(username, new Agent(username, user));
	}
	
	public Agent getAgentByUsername(String username) {
		return this.agents.get(username);
	}
	
	public HashMap<String, Agent> getAgents() {
		return agents;
	}

	public void setAgents(HashMap<String, Agent> agents) {
		this.agents = agents;
	}
	
	public void createNewResearchAgent(ResearchAgent agent) {
		this.researchAgents.put(agent.getAid(),agent);
	}


	public HashMap<AID, ResearchAgent> getResearchAgents() {
		return researchAgents;
	}


	public void setResearchAgents(HashMap<AID, ResearchAgent> researchAgents) {
		this.researchAgents = researchAgents;
	}


	public HashMap<AID, ResearchAgent> getRunningResearchAgents() {
		return runningResearchAgents;
	}


	public void setRunningResearchAgents(HashMap<AID, ResearchAgent> runningResearchAgents) {
		this.runningResearchAgents = runningResearchAgents;
	}
	
	public ResearchAgent getResearchAgentByName(String name) {
		ResearchAgent agent = new ResearchAgent();
		for(ResearchAgent r : this.researchAgents.values()) {
			if(r.getAid().getName().equals(name)) {
				agent = r;
			}
		}
		return agent;
	}
	
}
