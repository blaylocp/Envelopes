package envelopes.data;

import java.util.ArrayList;
import java.util.List;

public class Bank {
	private String displayName;
	private String orgName;
	private int siteID;
	private int orgID;
	private List<BankEntity> entities;
	
	public Bank() {
		entities = new ArrayList<BankEntity>();
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public int getSiteID() {
		return siteID;
	}
	public void setSiteID(int siteID) {
		this.siteID = siteID;
	}
	public int getOrgID() {
		return orgID;
	}
	public void setOrgID(int orgID) {
		this.orgID = orgID;
	}
	public List<BankEntity> getEntities() {
		return entities;
	}
	public void setEntities(List<BankEntity> entities) {
		this.entities = entities;
	}
	public void addEntities(BankEntity entity) {
		entities.add(entity);
	}
}
