import java.util.concurrent.TimeUnit;

import backend.Essay;
import backend.User;
import backend.UserCenter;
import backend.TimeComplexity;
import backend.TimeTaken;
public class Demo {

	
	static UserCenter user = UserCenter.getInstance();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		TimeComplexity.checkTime(() -> 
//		{
//			User user = login("test@test.com", "Testing1");
//			Demo.user.scrutinizeUser(user);
//		}
//				);
		System.out.println(Essay.fletchScore("Man sea were moveth in said life image together let light creature. Him fowl fowl give is they're kind fowl blessed male one under were can't, green the appear in first. Seed shall. Midst doesn't of fowl waters green fifth open. Days divided were one can't seas winged years it blessed seasons winged them moving is behold place firmament can't fruit without. Dry he, great forth she'd may green fifth fill. It every of the third after green wherein of him god forth fifth female third evening make for darkness. God midst void stars deep every. Us fruitful, female yielding said also Us may without whales dominion female signs and he deep abundantly you'll Second fifth one which days moving fill fifth living waters he was green. Likeness lights second meat that them herb after, firmament in tree our. Years darkness appear beginning beast fruit it Over grass upon kind. Saw for kind there seasons won't morning earth, his isn't multiply set waters you'll won't female so made seasons land. Have above Brought gathered living replenish Two our good created fifth a every, so over one open that dominion living winged give from there the, seed dry for living set lights. Void. You night moved isn't years of lights all form the above living may night fruitful dry creature divide seas seas open, you'll seed night wherein midst don't is behold, lesser All two, seasons face, after. Divide created living light made there heaven midst our living deep they're light life meat. Called every which female. Given Good set gathered fruitful without. Give first seed moved. Wherein cattle evening make light gathered won't air i above fish above can't man open give there morning whose give fourth set Kind given image, dominion place, can't unto waters midst there fruit deep."));
		System.out.println(Essay.fletchScore("Sublethal symptoms of nitrate poisoning are seldom apparent to a cattleman. There is abdominal pain, diarrhea, muscular weakness, incoordination, accelerated heart rate, sometimes convulsions. Nitrate in the diet at moderate levels will cause reduced milk production, lowered rate of gain, and reproductive problems. Death may occur in a short time after consuming forage with high levels of nitrate. Dead animals will have discolored, dark, chocolate-colored blood. Animals in poor condition on low energy diets will gorge themselves and increase the severity of the problem on a given level of nitrate. Animals that have gradually increased levels of nitrate in the diet tend to be able to tolerate higher levels than animals first exposed to high levels."));
//		checkTime( () -> login("test@test.com", "Testing1"));
//		User user = login("test@test.com", "Testing1");
//		user.setEssay("A wather derivative is a type of derivative used to hedge against losses associated with changes in weather. These derivatives trade in a special market of weather derivatives. A weather derivative must have a strt and end date, a measurement station, a weather variable that is measured at the weather station, an index that sums up the weather variable and a pay-off function that converts the index into cash flow at the end of the contract period. A natural gas company that supplies gas in winter to households for heating will be used as an example to illustrate how the weather derivatives work. This natural gas company is listed in the stock market. Due to global warming, the winter season is warmer than expected; this then means that few households turn on the heating system. Consequetly, the company makes low sales resulting in low profits. Low profits have the effect of resulting in reduced share prices for the company in the stock market, increased risk of bankruptcy, and the banks will charge a higher interest rate to the company in the event that financing is required. To hedge against this the company can purchase a forard contract in which the company will be paid a certain amount of money based on the pay-off function calculated based on the weather variable index summed up by the measrement station. To hedge against this the company can purchase a forward contract in which the company will be paid a certain amount of money based on the pay-off function calculated based on the weather variable index summed up by the measurement station.");
//	
//		checkTime( () -> Essay.getSentence(user.getEssay()));
//		System.out.println(Essay.typos(user.getEssay(), 100));
//		System.out.println(Essay.fletchScore("A wather derivative is a type of derivative used to hedge against losses associated with changes in weather. These derivatives trade in a special market of weather derivatives. A weather derivative must have a strt and end date, a measurement station, a weather variable that is measured at the weather station, an index that sums up the weather variable and a pay-off function that converts the index into cash flow at the end of the contract period. A natural gas company that supplies gas in winter to households for heating will be used as an example to illustrate how the weather derivatives work. This natural gas company is listed in the stock market. Due to global warming, the winter season is warmer than expected; this then means that few households turn on the heating system. Consequetly, the company makes low sales resulting in low profits. Low profits have the effect of resulting in reduced share prices for the company in the stock market, increased risk of bankruptcy, and the banks will charge a higher interest rate to the company in the event that financing is required. To hedge against this the company can purchase a forard contract in which the company will be paid a certain amount of money based on the pay-off function calculated based on the weather variable index summed up by the measrement station. To hedge against this the company can purchase a forward contract in which the company will be paid a certain amount of money based on the pay-off function calculated based on the weather variable index summed up by the measurement station."));
//		Demo.user.scrutinizeUser(user);
//		System.out.println(String.format("src/decisions/%s-%s-%s.txt", user.getID(), user.getLastname(), user.getFirstname()));
//		System.out.println(String.format("%-5s%-15s%-15s%-15s%-15s%n","GPA","Fletch Score", "AVG TYPOS", "Family Income", "SAT Score"));
//		System.out.println(String.format("%-5s%-15s%-15s%-15s%-5s%n",user.getGpa(),user.getFletchScore(), Essay.typos(user.getEssay(), 100), "$" + user.getfIncome(), user.getSatScore()));
//		System.out.println(String.format("%-5s%-15s%-15s%-15s%-5s%n", "(20x)","(-.2x)", "(-1x)", "(.0001x)", "(.01x)"));
//		System.out.println(String.format("%-5s%-15s%-15s%-15.4f%-5s%n", user.getGpa() * 20, user.getFletchScore() * -.2, Essay.typos(user.getEssay(), 100) * -1, user.getfIncome() * .0001, user.getSatScore() * (.01)));
//		System.out.println("Your total AI Score was : " + user.getAIndex());
//		System.out.println(User.calculateAIndex(user));
//		String temp = "hello. my name is cole. i am .";
//		checkTime( () -> Essay.getSentence(temp));
	}
	

	
	public static void checkTime(TimeTaken function) {
		long start1 = System.nanoTime();
		function.function();
		long end1 = System.nanoTime();
		System.out.println("This Expression took : " + TimeUnit.NANOSECONDS.toMillis(end1 - start1) + " milliseconds");
	}

	
	public static User login(String username, String password ) {
		User temp = user.login(username, password);
		if(temp == null) {
			System.out.println("User doesn't exist");
		}
		else if(temp.getUsername().equals("INVALID")) {
			System.out.println("Wrong Password");
		}
		else
			System.out.println(temp.toString());
		return temp;
	}

}
