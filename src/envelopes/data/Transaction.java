package envelopes.data;

import org.json.JSONException;
import org.json.JSONObject;

public class Transaction {
	private double amount;
	
	private String transactionType;
	
	public Transaction(JSONObject object) {
		try {
			this.amount = object.getJSONObject("amount").getDouble("amount");
			this.transactionType = object.getString("transactionType");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	
	
}
