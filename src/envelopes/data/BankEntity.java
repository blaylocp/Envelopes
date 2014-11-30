package envelopes.data;

/**
 * @author Travis
 *
 * This class holds the different entity's within a bank. For example,
 * it can be a bank, mortgage, stocks, other loan, credit, etc. 
 */
public class BankEntity {
	private int contentServiceId;
	private int siteId;
	private String containerName;
	private int assetTypeID;
	public int getContentServiceId() {
		return contentServiceId;
	}
	public void setContentServiceId(int contentServiceId) {
		this.contentServiceId = contentServiceId;
	}
	public int getSiteId() {
		return siteId;
	}
	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}
	public String getContainerName() {
		return containerName;
	}
	public void setContainerName(String containerName) {
		this.containerName = containerName;
	}
	public int getAssetTypeID() {
		return assetTypeID;
	}
	public void setAssetTypeID(int assetTypeID) {
		this.assetTypeID = assetTypeID;
	}
}
