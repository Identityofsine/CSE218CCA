package backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class UserCenter implements Serializable{
	private static final long serialVersionUID = -1885080549564923773L;
	private final static String objSaveDir = "src/backend/assets/data/data.dat";
	private int users = 0;
	public static final HashSet<String> dictionary = UserCenter.initalizeDictionary("src/backend/assets/wordlist.txt"); // add code to read dictionary file
	private static UserCenter INSTANCE;
	private TreeMap<String, User> allUsers; //hashtree
	private PriorityQueue<User> waitList; //heap
	private LinkedList<User> denied;
	public enum STATUS{ //status enum. 
		PURGATORY,
		ACCEPTED,
		DENIED,
		WAITLISTED
	}
	
	
	//user logins using Hashtable, in user a variable is saved named status; 
	
	
	public boolean saveData() {
		try {
			FileOutputStream fileOut = new FileOutputStream(objSaveDir);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(this);
			objectOut.close();
			fileOut.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	
	public static HashSet<String> initalizeDictionary(String dir){ // reads dictionary file into hashtable, hopefully not an issue...
		HashSet<String> hst = new HashSet<String>();
		try {
			FileReader fr = new FileReader(dir);
	        BufferedReader br =new BufferedReader(fr);    
			while(br.ready()) {
			   hst.add(br.readLine());
			}
			br.close();
			return hst; // 
		}
		catch(Exception e){
			return null;
		}

		
	}
	public static UserCenter getInstance() {
		if(INSTANCE == null) {
				try {
					FileInputStream fileInput = new FileInputStream(new File(objSaveDir));
					ObjectInputStream objectInput = new ObjectInputStream(fileInput);
					TimeComplexity.checkTime(() -> {
						try {
							INSTANCE = (UserCenter)objectInput.readObject();
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});
					objectInput.close();
					fileInput.close();
				}
				catch(FileNotFoundException e) {
					e.printStackTrace();
					System.out.println("sorry file not found...");
					INSTANCE = new UserCenter();
					INSTANCE.allUsers = new TreeMap<String, User>();
					INSTANCE.waitList = new PriorityQueue<User>(10);
					INSTANCE.denied = new LinkedList<User>();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			return INSTANCE;
		}
		return INSTANCE;
	}
	
	private UserCenter() {
		
	}
	
	public boolean initalize() {
		//add code to load 
		return false;
	}
	
	public static boolean isEmail(String username) {
		if(username.isBlank()) return false;
		boolean hasAt = false;
		boolean hasDot = false;
		for(int i = 0; i < username.length() - 1; i++) {
			if(username.charAt(i) == '@') hasAt = true;
			if(username.charAt(i) == '.' && Character.isAlphabetic(username.charAt(i + 1))) hasDot = true;
		}
		
		return hasAt && hasDot;
	}
	
	public static boolean validPassword(String password) {
		if(password.length() < 7) return false;
		boolean hasDigit = false;
		boolean hasUpperCase = false;
		boolean hasLowerCase = false;
		for (int i = 0; i < password.length(); i++) {
			if(Character.isUpperCase(password.charAt(i)))
				hasUpperCase = true;
			if(Character.isLowerCase(password.charAt(i)))
				hasLowerCase = true;
			if(Character.isDigit(password.charAt(i)))
				hasDigit = true;	
		}
		return hasDigit && hasUpperCase && hasLowerCase;
	}
	
	
	//adding user block
	//-2 : bad username, -1 : user already exists, 0: bad password, 1:successful
	public int addUser(User user) {
		if(!isEmail(user.getUsername())) return -2;
		//determinationCode - if this 
		if(!allUsers.containsKey(user.getUsername().toLowerCase())) {
			if(validPassword(user.getPassword())) {
				user.setID(nextID());
				try {	//create a file -- /src/decisions]
					new File("src/decisions/").mkdir();
					File myObj = new File(String.format("src/decisions/%s-%s-%s.txt", user.getID(), user.getLastname(), user.getFirstname()));
						if (myObj.createNewFile()) {
					        System.out.println("File created: " + myObj.getName());
					      } 
						 FileWriter myWriter = new FileWriter(String.format("src/decisions/%s-%s-%s.txt", user.getID(), user.getLastname(), user.getFirstname()));
					      myWriter.write("Incomplete!");
					      myWriter.close();
				}catch(Exception e) {
					System.out.println("Something went wrong...");
					e.printStackTrace();
				}
				allUsers.putIfAbsent(user.getUsername().toLowerCase(), user);
				return 1;
			}
			return 0;
			}
		return -1;
		}
	
	
	//override
	public int addUser(String username, String password) {
		if(!isEmail(username)) return -2;
		//determinationCode - if this 
		if(!allUsers.containsKey(username)) {
			if(validPassword(password)) {
				User newUser = new User(username, password, nextID());
				try {	//create a file -- /src/decisions]
					new File("src/decisions/").mkdir();
					File myObj = new File(String.format("src/decisions/%s-%s-%s.txt", "id", "last", "first"));
					 if (myObj.createNewFile()) {
					        System.out.println("File created: " + myObj.getName());
					      } 
				}catch(Exception e) {
					System.out.println("Something went wrong...");
					e.printStackTrace();
				}
				allUsers.putIfAbsent(username, newUser);
				return 1;
			}
			return 0;
			}
		return -1;
		}
	
	
	@SuppressWarnings("unused")
	private static boolean createDecisionText() {
		return false;
	}
	
	
	private int nextID() {
		return users++;
	}
	
	//end of adding user block
	
	
	
	//this function should be called when the user enters all their info
	public STATUS scrutinizeUser(User user){
		FileWriter myWriter;
		user.setFletchScore((int) Essay.fletchScore(user.getEssay()));
		user.setAIndex(User.calculateAIndex(user));
		if(user.getGpa() >= 3.5 && Essay.wordCount(user.getEssay()) >= 250 && user.getFletchScore() < 60 && Essay.typos(user.getEssay(), 100) < 3 && user.getfIncome() > 9999 && user.getSatScore() >= 1200) {
			//run accept code
			try {
				myWriter = new FileWriter(String.format("src/decisions/%s-%s-%s.txt", user.getID(), user.getLastname(), user.getFirstname()));
				myWriter.write("Congratulations! You have been Accepted into Squidward Community College! Where the evolution clock ticks backwards!\n\n");
				myWriter.write("The information you gave us is outstanding.\nAll of your information was calculated in an AI(Admissible Index).\nYour AI Score was calculated by:\n");
				myWriter.write(String.format("%-5s%-15s%-15s%-15s%-15s%n","GPA","Fletch Score", "AVG Typos", "Family Income", "SAT Score"));
				myWriter.write(String.format("%-5s%-15s%-15s%-15s%-5s%n", user.getGpa(),user.getFletchScore(), Essay.typos(user.getEssay(), 100), "$" + user.getfIncome(), user.getSatScore()));
				myWriter.write(String.format("%-5s%-15s%-15s%-15s%-5s%n", "(20x)","(-.2x)", "(-1x)", "(.0001x)", "(.01x)"));
				myWriter.write(String.format("%-5s%-15.2f%-15s%-15.4f%-5.2f%n", user.getGpa() * 20, user.getFletchScore() * -.2, Essay.typos(user.getEssay(), 100) * -1, user.getfIncome() * .0001, user.getSatScore() * (.01)));
				myWriter.write("Your total AI Score was : " + user.getAIndex());
				myWriter.close();
			}
			catch(Exception e) {	}
			System.out.println("accepted");
			user.status = 0;
			return STATUS.ACCEPTED;
		} else if(user.getGpa() < 2.5 || Essay.wordCount(user.getEssay()) < 250 || user.getFletchScore() > 70 || Essay.typos(user.getEssay(), 100) > 10 || user.getfIncome() < 10000 || user.getSatScore() < 800) {
			try {
				myWriter = new FileWriter(String.format("src/decisions/%s-%s-%s.txt", user.getID(), user.getLastname(), user.getFirstname()));
				myWriter.write("you blow we accept literally anyone.\n");
				myWriter.write("Review this, because you are actually stupid. Take goddamn notes and maybe we will let you work as the janitor.\nAll of your information was calculated in an AI(Admissible Index). Heres what went wrong :\nYour AI Score was calculated by:\n");
				myWriter.write(String.format("%-5s%-15s%-15s%-15s%-15s%n","GPA","Fletch Score", "AVG Typos", "Family Income", "SAT Score"));
				myWriter.write(String.format("%-5s%-15s%-15s%-15s%-5s%n", user.getGpa(),user.getFletchScore(), Essay.typos(user.getEssay(), 100), "$" + user.getfIncome(), user.getSatScore()));
				myWriter.write(String.format("%-5s%-15s%-15s%-15s%-5s%n", "(20x)","(-.2x)", "(-1x)", "(.0001x)", "(.01x)"));
				myWriter.write(String.format("%-5s%-15.2f%-15s%-15.4f%-5.2f%n", user.getGpa() * 20, user.getFletchScore() * -.2, Essay.typos(user.getEssay(), 100) * -1, user.getfIncome() * .0001, user.getSatScore() * (.01)));
				myWriter.write("Your total AI Score was : " + user.getAIndex());
				myWriter.close();
			}
			catch(Exception e) {	}		
			user.status = 2;
			denied.add(user);
			System.out.println("denied");
			return STATUS.DENIED;
		}
		else {
			//run waitlist code
			try {
				myWriter = new FileWriter(String.format("src/decisions/%s-%s-%s.txt", user.getID(), user.getLastname(), user.getFirstname()));
				myWriter.write("You are on the Squidward Community College Waitlist...\n");
				myWriter.write("The information you gave us is good, but we have already have a whole bunch of \"good\" just like you...\nAll of your information was calculated in an AI(Admissible Index).\nYour AI Score was calculated by:\n");
				myWriter.write(String.format("%-5s%-15s%-15s%-15s%-15s%n","GPA","Fletch Score", "AVG Typos", "Family Income", "SAT Score"));
				myWriter.write(String.format("%-5s%-15s%-15s%-15s%-5s%n", user.getGpa(),user.getFletchScore(), Essay.typos(user.getEssay(), 100), "$" + user.getfIncome(), user.getSatScore()));
				myWriter.write(String.format("%-5s%-15s%-15s%-15s%-5s%n", "(20x)","(-.2x)", "(-1x)", "(.0001x)", "(.01x)"));
				myWriter.write(String.format("%-5s%-15.2f%-15s%-15.4f%-5.2f%n", user.getGpa() * 20, user.getFletchScore() * -.2, Essay.typos(user.getEssay(), 100) * -1, user.getfIncome() * .0001, user.getSatScore() * (.01)));
				myWriter.write("Your total AI Score was : " + user.getAIndex());
				myWriter.close();
			}
			catch(Exception e) {}
			System.out.println("waitlist");
			user.status = 1;
			TimeComplexity.checkTime( () -> waitList.add(user));
			Iterator<User> it = waitList.iterator(); 
			while(it.hasNext()) { // print waitlist after 
				var next = it.next();
				System.out.println(String.format("Name: %s AI: %s", next.getUsername(), next.getAIndex()));
			}
			return STATUS.WAITLISTED;
		}
	}
	
	public User login(String username, String password) {
		if(allUsers.containsKey(username)) {
			User temp = allUsers.get(username);
			if(temp.getPassword().equals(password))
				return temp;
			return new User("INVALID", "INVALID", -1); // wrong password
		}
		return null; // user not found
	}
	
	public void logout() {
		//save code
		return;
	}
		
	public double calculateFletchScore(User user) {
		//first check if all vars are existent, if not return -1
		//check fletch score;
		return Essay.fletchScore(user.getEssay());
	}
	
	public void displayUsers() {
		System.out.println(allUsers);
	}
	
	
}
