package backend;

import java.io.Serializable;

public class Address implements Serializable{
	private static final long serialVersionUID = -6510187590951544271L;
	private String address;
	private String town;
	private int zipcode;
	private String state;
	
	public Address(String address, String town, String State, int zipcode){
		this.address = address;
		this.town = town;
		this.state = State;
		this.zipcode = zipcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public int getZipcode() {
		return zipcode;
	}

	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public boolean isBlank() {
		return address.isBlank() || town.isBlank() || state.isBlank() || zipcode <= 0;
	}
	
	@Override
	public String toString() {
		return address + ", " + town + ", " + state + " " + zipcode;
	}
	
	
}
