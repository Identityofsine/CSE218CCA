package backend;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;

public class User implements Comparable<User>, Cloneable, Serializable{
	private static final long serialVersionUID = 1113799434508676095L;
	private int ID;
	private String firstname = "";
	private String lastname = "";
	private String username; //!!
	private String password; //!! maybe hash it...
	private double phoneNumber;
	private Address address;
	private double gpa;
	private int satScore;
	private int fletchScore = 0;
	private String essay; // keep as string or change to file... or generate a new type
	private double fIncome;
	private double aI;
	public int status = -1; // -1 just created, 0 - accepted, 1 - waitlist, 2 - denied.
	
	public User(String firstname, String lastname, String username, String password, double phoneNumber, Address address,
			double gpa, int satScore, String essay, double fIncome, double aI, int id) {
		this.ID = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.gpa = gpa;
		this.satScore = satScore;
		this.essay = essay;
		this.fIncome = fIncome;
		this.aI = aI;
	}

	public User(String username, String password, int id) {
		this.ID = id;
		this.username = username;
		this.password = password;
	}
	

	
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public double getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(double phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public double getGpa() {
		return gpa;
	}

	public void setGpa(double gpa) {
		this.gpa = gpa;
	}
	
	public void setFletchScore(int fletch) {
		this.fletchScore = fletch;
	}
	
	public int getFletchScore() {
		return fletchScore;
	}

	public int getSatScore() {
		return satScore;
	}

	public void setSatScore(int satScore) {
		this.satScore = satScore;
	}

	public String getEssay() {
		return essay;
	}

	public void setEssay(String essay) {
		this.essay = essay;
	}

	public double getfIncome() {
		return fIncome;
	}

	public void setfIncome(double fIncome) {
		this.fIncome = fIncome;
	}

	public double getAIndex() {
		return aI;
	}

	public void setAIndex(double fletchScore) {
		this.aI = fletchScore;
	}

	public String getUsername() {
		return username;
	}
	
	public boolean isInComplete() {
		return username.isBlank() || username == null || password.isBlank() || firstname.isBlank() || firstname == null || lastname.isBlank() || lastname == null || phoneNumber == 0 || address == null || address.isBlank() || gpa < 0.0 || gpa > 4.1 || satScore < 0 || satScore > 1600 || essay.isBlank() || fIncome == 0;
	}
	
	@Override
	public String toString() {
		return "User [firstname=" + firstname + ", lastname=" + lastname + ", username=" + username + ", aI="
				+ aI + ", id : " + ID + "]" ;
	}

	public void setID(int ID) {
		this.ID = ID;		
	}
	public int getID() {
		return ID;
	}
	
	
	public static double calculateAIndex(User user) {
		double aIndex = (user.getGpa() * 20 + (user.getfIncome() * .0001) + ((user.getSatScore()-400) * .01))  - (Math.abs(user.getFletchScore()) * .2) - ((Essay.typos(user.getEssay(), 100)));
		System.out.println(aIndex);
		return (int)aIndex;
	}

	@Override
	public int compareTo(User o) {
		// TODO Auto-generated method stub
		return (int)(o.aI - this.aI);
	}
	
	

	
}
