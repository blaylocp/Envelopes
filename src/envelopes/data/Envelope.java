package envelopes.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Envelope {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int category_id; 
	private String category_name;
	private double envelope_amount;
	private String customer_username;
	
	public Envelope() {
		super();
	}
	
	public Envelope(String category_name) {
		super();
		this.category_name = category_name;
		this.envelope_amount = 0.00;
	}
		
	@Override
	public String toString() {
		return "Envelope [category_id=" + category_id + ", category_name="
				+ category_name + ", envelope_amount=" + envelope_amount
				+ ", customer_username=" + customer_username + "]";
	}

	public double getEnvelope_amount() {
		return envelope_amount;
	}
	public void setEnvelope_amount(double envelope_amount) {
		this.envelope_amount = envelope_amount;
	}
	
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}	
	
	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	public String getCustomer_username() {
		return customer_username;
	}

	public void setCustomer_username(String customer_username) {
		this.customer_username = customer_username;
	}	

}
