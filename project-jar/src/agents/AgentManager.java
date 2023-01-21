package agents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;

import model.AID;
import model.User;

@Singleton
@LocalBean
public class AgentManager {
	
	private HashMap<String, Agent> agents;
	private HashMap<AID, ResearchAgent> researchAgents;
	private HashMap<AID, ResearchAgent> runningResearchAgents;
	
	public AgentManager() {
		agents = new HashMap<String, Agent>();
		researchAgents = new HashMap<AID, ResearchAgent>();
		runningResearchAgents = new HashMap<AID, ResearchAgent>();
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
	
	public void createNewResearchAgent(AID aid) {
		ResearchAgent agent = new ResearchAgent();
		agent.setAid(aid);
		researchAgents.put(aid, agent);
	}
	
	public ResearchAgent getResearchAgentByName(String username) {
		ResearchAgent agent = new ResearchAgent();
		for(AID a : researchAgents.keySet()) {
			if(a.getName().equals(username)) {
				agent = researchAgents.get(a);
			}
		} 
		return agent;
	}
	
	public void setResearchAgentRunning(ResearchAgent agent) {
		runningResearchAgents.put(agent.getAid(), agent);
	}
	
	public ResearchAgent getResearchAgentByAID(AID aid) {
		ResearchAgent agent = new ResearchAgent();
		//return researchAgents.entrySet().stream().filter(e -> e.getKey().equals(aid)).map(Map.Entry::getValue).findFirst().orElse(null);
		for(AID a : researchAgents.keySet()) {
			if(a.equals(aid)) {
				agent = researchAgents.get(a);
			}
		}
		return agent;
	}
	
	public AID[] getReceiversForMessage(String username) {
		//AID[] retVal = new AID[runningResearchAgents.size()];
		List<AID> keys = new ArrayList<AID>();
		for(AID a : runningResearchAgents.keySet()) {
			if(!a.getName().equals(username)) {
				keys.add(a);
			}
		}
		AID[] retVal = keys.stream().toArray(AID[]::new);
		
		return retVal;
		
	}
	
	public void stopAgentRunning(AID aid) {
		getRunningResearchAgents().remove(aid);
	}
	
}
