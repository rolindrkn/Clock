import java.time.LocalTime;
import static java.lang.Math.PI;

/** Tester for the clock and hand class
	@author Jeremy H for CPSC 1181 201730 at Langara
	@author Rosalind Ng
	@version 2017-10-06*/
public class HandClockTester {

    private final static double RADIANS = PI*2;
    private final static double EPSILON = 1E-12;

	public static void main(String[] args) {
		new TestRunner(HandClockTester.class).run();
	}
	
	public static void test_handAngle_noPartial_special_0() {
		LocalTime t = LocalTime.parse("00:00");
		Hand h;
		h = new Hand(Hand.TimeUnit.SECOND, null, t);
		assert 0 == h.getAngle();
		h = new Hand(Hand.TimeUnit.MINUTE, null, t);
		assert 0 == h.getAngle();
		h = new Hand(Hand.TimeUnit.HOUR, null, t);
		assert 0 == h.getAngle();
	}
	
	// TODO more tests for TDD
	//to help test for the angles
	public static boolean equals(double expected, double actual) {
		return Math.abs(expected - actual) <= EPSILON;
	}
	public static void test_handAngle_noPartial_boundary_0() {
		LocalTime t = LocalTime.parse("12:00:00");
		Hand h0 = new Hand(Hand.TimeUnit.MILLS, null, t);
		Hand h1 = new Hand(Hand.TimeUnit.SECOND, null, t, h0);
		assert 0 == h1.getAngle();
		Hand h2 = new Hand(Hand.TimeUnit.MINUTE, null, t, h1);
		assert 0 == h2.getAngle();
		Hand h3 = new Hand(Hand.TimeUnit.HOUR, null, t, h2);
		assert 0 == h3.getAngle();
	}
	
	public static void test_handAngle_noPartial_boundary_ninety() {
		//test hour hand to return PI/2
		LocalTime t = LocalTime.parse("03:00:00");
		Hand sec = new Hand(Hand.TimeUnit.SECOND, null, t);
		assert 0 == sec.getAngle();
		Hand min = new Hand(Hand.TimeUnit.MINUTE, null, t, sec);
		assert 0 == min.getAngle();
		Hand hour = new Hand(Hand.TimeUnit.HOUR, null, t, min);
		assert equals(RADIANS/4, hour.getAngle());
		
		//test minute hand to return PI/2
		t = LocalTime.parse("12:15:00");
		sec = new Hand(Hand.TimeUnit.SECOND, null, t);
		assert 0 == sec.getAngle();
		min = new Hand(Hand.TimeUnit.MINUTE, null, t, sec);
		assert equals(RADIANS/4, min.getAngle());
		hour = new Hand(Hand.TimeUnit.HOUR, null, t, min);
		assert equals(RADIANS/48, hour.getAngle()): hour.getAngle();
		
		//test angle should return pi/2 for the second hand
		t = LocalTime.parse("12:00:15");
		sec = new Hand(Hand.TimeUnit.SECOND, null, t);
		assert equals(RADIANS*15/60, sec.getAngle());
		min = new Hand(Hand.TimeUnit.MINUTE, null, t, sec);
		assert equals(RADIANS*15/60/60, min.getAngle()); 
		hour = new Hand(Hand.TimeUnit.HOUR, null, t, min);
		assert equals(RADIANS*15/60/60/12 , hour.getAngle()):hour.getAngle();
	}
	
	public static void test_handAngle_noPartial_boundary_oneEighty() {
		//test the angle should return pi/2 for hour hand
		LocalTime t = LocalTime.parse("03:00:00");
		Hand sec = new Hand(Hand.TimeUnit.SECOND, null, t);
		assert 0 == sec.getAngle();
		Hand min = new Hand(Hand.TimeUnit.MINUTE, null, t, sec);
		assert 0 == min.getAngle();
		Hand hour = new Hand(Hand.TimeUnit.HOUR, null, t, min);
		assert equals(RADIANS/12*3, hour.getAngle()): hour.getAngle();
		
		//test angle should return pi/2 for minute hand
		t = LocalTime.parse("12:15:00");
		sec = new Hand(Hand.TimeUnit.SECOND, null, t);
		assert 0 == sec.getAngle();
		min = new Hand(Hand.TimeUnit.MINUTE, null, t, sec);
		assert equals(RADIANS/60*15, min.getAngle());
		hour = new Hand(Hand.TimeUnit.HOUR, null, t, min);
		assert equals(RADIANS/60/12*15, hour.getAngle()): hour.getAngle();
		
		//test angle should return pi/2 for the second hand
		t = LocalTime.parse("12:00:15");
		sec = new Hand(Hand.TimeUnit.SECOND, null, t);
		assert equals(PI/2, sec.getAngle());
		min = new Hand(Hand.TimeUnit.MINUTE, null, t, sec);
		assert equals(RADIANS/4/60, min.getAngle());
		hour = new Hand(Hand.TimeUnit.HOUR, null, t, min);
		assert equals(RADIANS/4/60/12, hour.getAngle());
	}
	
	public static void test_handAngle_noPartial_boundary_twoSeventy() {
		//test the angle should return 3pi/2 for hour hand
		LocalTime t = LocalTime.parse("09:00:00");
		Hand sec = new Hand(Hand.TimeUnit.SECOND, null, t);
		assert 0 == sec.getAngle();
		Hand min = new Hand(Hand.TimeUnit.MINUTE, null, t, sec);
		assert 0 == min.getAngle();
		Hand hour = new Hand(Hand.TimeUnit.HOUR, null, t, min);
		assert equals(PI/2*3, hour.getAngle()): hour.getAngle();
		
		//test minute hand should return 3pi/2
		t = LocalTime.parse("12:45:00");
		sec = new Hand(Hand.TimeUnit.SECOND, null, t);
		assert 0 == sec.getAngle();
		min = new Hand(Hand.TimeUnit.MINUTE, null, t, sec);
		assert equals(RADIANS*45/60, min.getAngle());
		hour = new Hand(Hand.TimeUnit.HOUR, null, t, min);
		assert equals(RADIANS*45/60/12, hour.getAngle()): hour.getAngle();
		
		//second hand should return 3PI/2
		t = LocalTime.parse("12:00:45");
		sec = new Hand(Hand.TimeUnit.SECOND, null, t);
		assert equals(RADIANS*45/60, sec.getAngle());
		min = new Hand(Hand.TimeUnit.MINUTE, null, t, sec);
		assert equals(3*PI/2/60, min.getAngle());
		hour = new Hand(Hand.TimeUnit.HOUR, null, t, min);
		assert equals(3*PI/2/60/12, hour.getAngle());
	}
	
	public static void test_handAngle_partial_typical_twelveToThree() {
		//test minute hand for partial value
		LocalTime t = LocalTime.parse("12:12:15");
		Hand sec = new Hand(Hand.TimeUnit.SECOND, null, t);
		assert equals(RADIANS*15/60 , sec.getAngle()); 
		Hand min = new Hand(Hand.TimeUnit.MINUTE, null, t, sec);
		assert equals((RADIANS*12/60 + RADIANS*15/60/60), min.getAngle());
		Hand hour = new Hand(Hand.TimeUnit.HOUR, null, t, min);
		assert equals((RADIANS*12/60/12 + RADIANS*15/60/60/12), hour.getAngle());
		
		//test the hour hand for partial value
		t = LocalTime.parse("01:12"); 
		min = new Hand(Hand.TimeUnit.MINUTE, null, t);
		assert equals(RADIANS*12/60, min.getAngle()): min.getAngle();
		hour = new Hand(Hand.TimeUnit.HOUR, null, t, min);
		assert equals((RADIANS*1/12 + RADIANS*12/60/12), hour.getAngle()); 
	}
	
	
	public static void test_handAngle_partial_typical_threeToSix() {
		//test minute hand angle to ensure it outputs partial value
		LocalTime t = LocalTime.parse("12:26:20");
		Hand sec = new Hand(Hand.TimeUnit.SECOND, null, t);
		assert equals(RADIANS*20/60 , sec.getAngle());
		Hand min = new Hand(Hand.TimeUnit.MINUTE, null, t, sec);
		assert equals((RADIANS*26/60 + RADIANS*20/60/60), min.getAngle());
		Hand hour = new Hand(Hand.TimeUnit.HOUR, null, t, min);
		assert equals((RADIANS*26/60/12 + RADIANS*20/60/60/12), hour.getAngle());
		
		//test hour hand for partial value
		t = LocalTime.parse("04:45"); 
		min = new Hand(Hand.TimeUnit.MINUTE, null, t);
		assert equals((RADIANS*45/60), min.getAngle());
		hour = new Hand(Hand.TimeUnit.HOUR, null, t, min);
		assert equals((RADIANS*4/12 + RADIANS*45/60/12), hour.getAngle());
	}
	
	public static void test_handAngle_partial_typical_sizeToNine() {
		//test minute hand angle to ensure it outputs partial value
		LocalTime t = LocalTime.parse("12:40:25");
		Hand sec = new Hand(Hand.TimeUnit.SECOND, null, t);
		assert equals(RADIANS*25/60 , sec.getAngle());
		Hand min = new Hand(Hand.TimeUnit.MINUTE, null, t, sec);
		assert equals((RADIANS*40/60 + RADIANS*25/60/60), min.getAngle());
		Hand hour = new Hand(Hand.TimeUnit.HOUR, null, t, min);
		assert equals((RADIANS*40/60/12 + RADIANS*25/60/60/12), hour.getAngle());
		
		//test hour hand for partial value
		t = LocalTime.parse("07:45"); 
		min = new Hand(Hand.TimeUnit.MINUTE, null, t);
		assert equals((RADIANS*45/60), min.getAngle());
		hour = new Hand(Hand.TimeUnit.HOUR, null, t, min);
		assert equals((RADIANS*7/12 + RADIANS*45/60/12), hour.getAngle());
	}
	
	public static void test_handAngle_partial_typical_nineToTwelve() {
		//test minute hand angle to ensure it outputs partial value
		LocalTime t = LocalTime.parse("12:50:20");
		Hand sec = new Hand(Hand.TimeUnit.SECOND, null, t);
		assert equals(RADIANS*20/60 , sec.getAngle());
		Hand min = new Hand(Hand.TimeUnit.MINUTE, null, t, sec);
		assert equals((RADIANS*50/60 + RADIANS*20/60/60), min.getAngle());
		Hand hour = new Hand(Hand.TimeUnit.HOUR, null, t, min);
		assert equals((RADIANS*50/60/12 + RADIANS*20/60/60/12), hour.getAngle());
		
		//test hour hand for partial value
		t = LocalTime.parse("10:45"); 
		min = new Hand(Hand.TimeUnit.MINUTE, null, t);
		assert equals((RADIANS*45/60), min.getAngle());
		hour = new Hand(Hand.TimeUnit.HOUR, null, t, min);
		assert equals((RADIANS*10/12 + RADIANS*45/60/12), hour.getAngle());
	}
	
	public static void test_get_hands_boundary_null() {
		//test when the clock is made with no arguements in the command line
		//this is when time is null
		//don't know what the local time is, so cannot check if it's correct
		//but can check to make sure that there are indeed 4 objects in array
		Clock c = new Clock(null);
		Hand[] hands = c.getHands();
		assert hands.length == 4;
		assert hands != null;		
	}
	
	public static void test_get_hands_one_arguement() {
		//test when the clock is made with 1 arguements in the commnad line
		
		//boundary case, when time is 00:00:00
		LocalTime t = LocalTime.parse("00:00:00");
		Clock c = new Clock(t);
		Hand[] hands = c.getHands();
		
		//check for the second
		assert equals(0, hands[1].getAngle());
		//check the minute hand
		assert equals(0, hands[2].getAngle());
		//check the hour hand
		assert equals(0, hands[3].getAngle());
		
		//typical case, time at 9:29:31
		t = LocalTime.parse("09:29:31");
		c = new Clock(t);
		hands = c.getHands();
		
		//check for second
		assert equals(RADIANS*31/60, hands[1].getAngle());
		//check for minute
		assert equals((RADIANS*29/60 + RADIANS*31./60/60), hands[2].getAngle());
		//check the hour hand
		assert equals((RADIANS*9/12 + (RADIANS*29/60 + RADIANS*31./60/60)/12), hands[3].getAngle());
	}
	
	public static void test_get_hands_three_arguements() {
		//test when the clock is made with 3 arguements in the commnad line
		
		//boundary case, when time is 00:00:00
		Clock c = new Clock(LocalTime.of(00,00,00));
		Hand[] hands = c.getHands();
		
		//check for the second
		assert equals(0, hands[1].getAngle());
		//check the minute hand
		assert equals(0, hands[2].getAngle());
		//check the hour hand
		assert equals(0, hands[3].getAngle());
		
		//typical case, time at 9:29:31
		c = new Clock(LocalTime.of(9,29,31));
		hands = c.getHands();
		
		//check for second
		assert equals(RADIANS*31/60, hands[1].getAngle());
		//check for minute
		assert equals((RADIANS*29/60 + RADIANS*31./60/60), hands[2].getAngle());
		//check the hour hand
		assert equals((RADIANS*9/12 + (RADIANS*29/60 + RADIANS*31./60/60)/12), hands[3].getAngle());
	}
}	
