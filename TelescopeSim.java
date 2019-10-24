/**
 * Represents a person who wants to observe at the telescope
 * @author laurenfossel
 * @version 3-4-18
 */
public class Person {
	
	private int id;
	private int arrived;
	private int interest; //their interest in seeing a cooler object (through a larger telescope)
	
	/**
	* Constructor that initializes instance variables
	* @param name the person's id number
	* @param came when the person arrived
	*/
	public Person(int name, int came) {
		interest = (int)(30+Math.random()*70);
		id = name;
		arrived = came;
	}
	
	/**
	* Returns the id number
	* @return the id number
	*/
	public int getId(){
		return id;
	}
	
	/**
	* @return the arrival time
	*/
	public int getArrived(){
		return arrived;
	}
	
	/**
	* @return the person's interest in astronomy
	*/
	public int getInterest(){
		return interest;
	}
}


/** Represents a technician for a telescope
 * 
 * @author laurenfossel
 * @version 3-4-18
 */
public class Technician {
	
	private int timeRemaining;
	private int ability; //ability with telescope
	private Person user;
	private int origServeTime;

	/**
	* Constructor that initializes instance vars
	*/
	public Technician() {
		timeRemaining = 0;
		ability = determineAbility();
		user = null;
		origServeTime = 0;
	}
	
	/**
	* Determines the technician's ability
	* @return the technician's ability
	*/
	public int determineAbility(){
		double rand = Math.random();
		if (rand<.33333)
			ability = -1; //bad
		else if (rand>=.33333 && rand<.66667)
			ability = 0; //average
		else
			ability = 1; //good
		return ability;
	}
	
	/** Serves the next person at the telescope
	* @param time the time the person would spend at the telescope with an average technician
	* @param person the person observing
	*/
	public void serve(int time, Person person){
		timeRemaining = time + determineAbility();
		origServeTime = timeRemaining;
		user = person;
	}
	
	/**
	* Decreases the time someone is at the telescope for
	*/
	public void passTime(){
		if(isBusy())
			timeRemaining--;
	}
	
	/**
	* Determines if the technician is currently serving someone
	* @return if the technician is busy
	*/
	public boolean isBusy(){
		return timeRemaining !=0;
	}
	
	/**
	* Returns the technician's ability
	* @return the technician's ability
	*/
	public int getAbility(){
		return ability;
	}
	/**
	* Returns the user currently at the telescope
	* @return the user currently at the telescope
	*/
	public Person getUser(){
		return user;
	}
	
	/**
	* Returns the initial time the person is observing for
	* @return the initial time the person is observing for
	*/
	public int getOrigServeTime(){
		return origServeTime;
	}
	
	/**
	* Sets the person currently observing at the telescope
	* @param person the person to observe
	*/
	public void setUser(Person person){
		user = person;
	}
}

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author laurenfossel
 * @verson 3-4-18
 */
public class AstroNight {
	private int numCustomers;
	private int waitTimeSumSchulman;
	private int waitTimeSumPhillips;
	private int maxWaitSchulman;
	private int maxWaitPhillips;
	private int numPhillips;
	private int numSchulman;
	
	private Queue<Person> schulmanTele;
	private Queue<Person> phillipsTele;
	private Technician schulmanTech;
	private Technician phillipsTech;
	
	private boolean shiftTimeSchulman;
	private boolean shiftTimePhillips;

	private final int NUM_MINUTES=300;
	private final int SCHULMAN = 0;
	private final int PHILLIPS = 1;
	private final double ARRIVAL_PROB = 20;
	private final int MAX_OBSERVATION = 8;
	private final int MIN_OBSERVATION = 2; //takes time to move telescope and get set up
	private final int SHIFT_LENGTH = 60;

	/**
	* Constructor that initializes instance variables
	*/
	public AstroNight() {
		numCustomers = 0;
		waitTimeSumSchulman = 0;
		waitTimeSumPhillips = 0;
		schulmanTele = new LinkedList<Person>();
		phillipsTele = new LinkedList<Person>();
		schulmanTech = new Technician();
		phillipsTech = new Technician();
		shiftTimeSchulman = false;
		shiftTimePhillips = false;
		maxWaitSchulman = 0;
		maxWaitPhillips = 0;
		numSchulman = 0;
		numPhillips = 0;
	}

	/**
	* Decides which telescope the person will choose
	* @param person the person to decide which telescope
	* @return which telescope the person chooses
	*/
	private int decideTele(Person person){
		if(schulmanTele.size()<phillipsTele.size()) //if cooler telescope has a shorter line, go there
			return SCHULMAN;
		else if(schulmanTele.size()>15 && person.getInterest()<70) //if cool telescope has a long line, and person isn't extremely interesting in astronomy
			return PHILLIPS;
		else if(person.getInterest()>66) //if person is very interested in astronomy, they choose cool telescope
			return SCHULMAN;
		else
			return PHILLIPS;
	}

	/**
	* @param tech the technician to display the ability of
	* @return a string saying the technician's ability
	*/
	private String displayAbility(Technician tech){
		int ability = tech.getAbility();
		switch (ability){
		case -1: return "poor";
		case 0: return "average";
		}
		return "good";
	}

	/**
	* Sets up the initial values of the animation
	*/
	private void setUp(){
		StdDraw.enableDoubleBuffering();
		StdDraw.setCanvasSize(1300, 600);
		StdDraw.setXscale(-750, 750);
		StdDraw.setYscale(-300,300);
		StdDraw.picture(0, 0, "NightSkySized.jpg");
		StdDraw.picture(-150, -150, "telescope-icon.png");
		StdDraw.picture(150, -200, "telescope-icon.png", 150, 150);
		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
		StdDraw.text(0, 250, "University of Arizona Sky Center");
		StdDraw.text(-180, 0, "32 Inch Schulman Telescope");
		StdDraw.text(180, 0, "24 Inch Phillips Telescope");
		StdDraw.show();
		//StdDraw.pause(100);
	}

	/**
	* Draws the simulation
	*/
	private void drawSim(){
		StdDraw.picture(0, 0, "NightSkySized.jpg");
		StdDraw.picture(-150, -150, "telescope-icon.png");
		StdDraw.picture(150, -200, "telescope-icon.png", 150, 150);
		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
		StdDraw.text(0, 250, "University of Arizona Sky Center");
		StdDraw.text(-180, 0, "32 Inch Schulman Telescope");
		StdDraw.text(180, 0, "24 Inch Phillips Telescope");
		int xCoordS = -200;
		int xCoordP = 200;
		int yCoord = -250;
		for(Person person: schulmanTele){
			if(person.getId()%3==0)
				StdDraw.picture(xCoordS, yCoord, "person.png", 30, 50);
			else if(person.getId()%3==1)
				StdDraw.picture(xCoordS, yCoord, "person2.png", 30, 50);
			else
				StdDraw.picture(xCoordS, yCoord, "person3.png", 30, 50);
			xCoordS-=20;
		}
		if(schulmanTech.isBusy()){
			if(schulmanTech.getUser().getId()%3==0)
				StdDraw.picture(-100, -200, "person.png", 30, 50);
			else if(schulmanTech.getUser().getId()%3==1)
				StdDraw.picture(-100, -200, "person2.png", 30, 50);
			else
				StdDraw.picture(-100, -200, "person3.png", 30, 50);
		}

		for(Person person: phillipsTele){
			if(person.getId()%3==0)
				StdDraw.picture(xCoordP, yCoord, "person.png", 30, 50);
			else if(person.getId()%3==1)
				StdDraw.picture(xCoordP, yCoord, "person2.png", 30, 50);
			else
				StdDraw.picture(xCoordP, yCoord, "person3.png", 30, 50);
			xCoordP+=20;
		}

		if(phillipsTech.isBusy()){
			if(phillipsTech.getUser().getId()%3==0)
				StdDraw.picture(100, -200, "person.png", 30, 50);
			else if(phillipsTech.getUser().getId()%3==1)
				StdDraw.picture(100, -200, "person2.png", 30, 50);
			else
				StdDraw.picture(100, -200, "person3.png", 30, 50);
		}
		StdDraw.show();
		//StdDraw.pause(100);
	}

	/**
	* Simulates the sky night
	*/
	public void simulate(){
		int min = 0;

		//Checks to make sure that the last user departs from each telescope
		boolean finishedS = false;
		boolean finishedP = false;

		setUp();

		//Continues serving people after hours if people are still in line
		while((min<NUM_MINUTES || !schulmanTele.isEmpty() || !phillipsTele.isEmpty() || schulmanTech.isBusy() || phillipsTech.isBusy() || !finishedS || !finishedP) && !(min>= NUM_MINUTES && numCustomers==0)){
			min++;
			if(min%SHIFT_LENGTH==0 && min!=NUM_MINUTES){ //Determines if time to change shifts
				shiftTimeSchulman = true;
				shiftTimePhillips = true;
			}

			//If the sky center is still open, new people can enter
			if(min<NUM_MINUTES){
				if (Math.random()*100<ARRIVAL_PROB){

					numCustomers++;
					Person newPerson = new Person(numCustomers, min);

					//If person chooses Schulman Telescope
					if(decideTele(newPerson) == SCHULMAN){
						numSchulman++;
						schulmanTele.add(newPerson);
						System.out.println(min+ ": Person " + newPerson.getId() + " arrives and waits for Schulman Telescope."); 
					}

					//If person chooses Phillips Telescope
					else {
						phillipsTele.add(newPerson);
						System.out.println(min+ ": Person " + newPerson.getId() + " arrives and waits for Phillips Telescope.");
						numPhillips++;
					}
				}
			}

			//If Schulman Telescope is available
			if(!schulmanTech.isBusy()){

				//If there is a departing user from the Schulman Telescope, print that they left
				if(schulmanTech.getUser()!=null){ 
					System.out.println(min + ": Person " + schulmanTech.getUser().getId() + " leaves. They were observing for " + schulmanTech.getOrigServeTime() + " min at the Schulman Telescope.");
					schulmanTech.setUser(null);
					finishedS = true;
				}

				//If it's time for the shift change and the previous Schulman technician isn't currently busy, change the technician
				if(shiftTimeSchulman && !schulmanTech.isBusy()){ 
					shiftTimeSchulman = false;
					schulmanTech = new Technician();
					System.out.println(min + ": New technician for Schulman Telescope arrives. Ability = " + displayAbility(schulmanTech));
				}

				//If there are people in line for the Schulman Telescope, serve the next person
				if(!schulmanTele.isEmpty()){ 
					Person nextPerson = schulmanTele.remove();
					schulmanTech.serve(MIN_OBSERVATION+(int)(Math.random()*(MAX_OBSERVATION-MIN_OBSERVATION)), nextPerson);
					System.out.println(min + ": Person " + nextPerson.getId() + " gets to the Schulman Telescope.");
					finishedS = false;

					//Calculates the wait time for Schulman Telescope
					int wait = min-nextPerson.getArrived();
					waitTimeSumSchulman+=wait;
					if(wait>maxWaitSchulman)
						maxWaitSchulman = wait;
				}
			}
			schulmanTech.passTime();

			//If Phillips Telescope is available
			if(!phillipsTech.isBusy()){

				//If there is a departing user from the Phillips Telescope, print that they left
				if(phillipsTech.getUser()!=null){ 
					System.out.println(min + ": Person " + phillipsTech.getUser().getId() + " leaves. They were observing for " + phillipsTech.getOrigServeTime() + " min at the Phillips Telescope.");
					phillipsTech.setUser(null);
					finishedP = true;
				}

				//If it's time for the shift change and the previous Phillips technician isn't currently busy, change the technician
				if (shiftTimePhillips && !phillipsTech.isBusy()){ 
					shiftTimePhillips = false;
					phillipsTech = new Technician();
					System.out.println(min + ": New technician for Phillips Telescope arrives. Ability = " + displayAbility(phillipsTech));
				}

				//If there are people in line for the Phillips Telescope, serve the next person
				if(!phillipsTele.isEmpty()){ 
					Person nextPerson = phillipsTele.remove();
					phillipsTech.serve(MIN_OBSERVATION+(int)(Math.random()*(MAX_OBSERVATION-MIN_OBSERVATION)), nextPerson);
					System.out.println(min + ": Person " + nextPerson.getId() + " gets to the Phillips Telescope.");
					finishedP = false;

					//Calculates wait time for the Phillips Telescope
					int wait = min-nextPerson.getArrived();
					if(wait>maxWaitPhillips)
						maxWaitPhillips = wait;
					waitTimeSumPhillips+=wait;
				}
			}
			phillipsTech.passTime();
			drawSim();
		}
		printFinalResults();
	}

	/**
	* Prints the final results and statistics from the simulation
	*/
	private void printFinalResults(){

		System.out.println("\nTotal number of people that observed: " + numCustomers);
		System.out.println("Number of customers that observed through the Schulman Telescope: " + numSchulman);
		System.out.println("Number of customers that observed through the Phillips Telescope: " + numPhillips);
		if(numSchulman==0) //Avoids NaN
			System.out.println("Average wait time for Schulman Telescope: 0");
		else
			System.out.println("Average wait time for Schulman Telescope: " + String.format("%1$.2f",(double)waitTimeSumSchulman/numSchulman));
		if(numPhillips == 0) //Avoids NaN
			System.out.println("Average wait time for Phillips Telescope: 0");
		else
			System.out.println("Average wait time for Phillips Telescope: " + String.format("%1$.2f",(double)waitTimeSumPhillips/numPhillips));
		System.out.println("Max wait time for Schulman Telescope: " + maxWaitSchulman);
		System.out.println("Max wait time for Phillips Telescope: " + maxWaitPhillips);
	}
}


/** Tests the AstroNight class
 * @author laurenfossel
 *@version 3-4-18
 */
public class AstroDriver {

	public static void main(String [] args){
		AstroNight night = new AstroNight();
		night.simulate();
	}
}
