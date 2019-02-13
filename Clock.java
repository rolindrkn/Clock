import java.time.LocalTime;
import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.util.concurrent.TimeUnit;
import java.time.format.DateTimeFormatter;

/** A clock that shows the time 
	@author Jeremy Hilliker
	@author Rosalind Ng
	@version 2017-10-06
	*/
	
public class Clock {

	/** Radians in a circle. */
	public final static double RADIANS = 2*Math.PI;
	/** the size of the clock */
	public final static int SCALE = 200;
	/** the centre of the clock */
	public final static int CENTRE = SCALE/2;
	private final static int NUM_TICKS = 12;
	private final static int NUM_FAT_TICKS = 4;

	private final Hand[] hands;
	private final LocalTime time; //time at the current moment
	
	private final GradientPaint grayToLight = new GradientPaint(0,0, new Color(80,80,80), 
		SCALE,SCALE, new Color(70,70,70));
	private final GradientPaint clockColor = new GradientPaint(0, 0, new Color(0,0,0), 
		SCALE-14,SCALE-14, new Color(21,26,11));
	private final GradientPaint rectColor = new GradientPaint(CENTRE+41, CENTRE+58, new Color(0,0,0), 
		CENTRE-38,CENTRE+30, new Color(21,26,11));
	
	/** Creates a new clock set to the indicated time */
	public Clock(LocalTime aTime) {
		//null = use the current time
		
		if(aTime == null) {
			time = LocalTime.now();
		}
		//time given
		else { time = aTime; } 
		
		//get the hands of the clock
		hands = getHands();
	}
	
	/** Gets the hands of the clock 
		@return the hands of this clock are returned in the following order:
		MILLS, SECOND, MINUTE, HOUR. */	
	public Hand[] getHands() {
		Hand[] hands = new Hand[4];
		//don't need the graphic for the mills hand: won't be drawn
		hands[0] = new Hand(Hand.TimeUnit.MILLS, new Hand.GraphicsInfo(0,0), time);
		hands[1] = new Hand(Hand.TimeUnit.SECOND, new Hand.GraphicsInfo(70,1, Color.RED), time, hands[0]);
		hands[2] = new Hand(Hand.TimeUnit.MINUTE, new Hand.GraphicsInfo(60,3*2/3, new Color(100,100,100)), time, hands[1]);
		hands[3] = new Hand(Hand.TimeUnit.HOUR, new Hand.GraphicsInfo(60*2/3, 3, Color.DARK_GRAY), time, hands[2]);
		
		return hands;

	}
	/** Draws the clock at the current canvas position. */
	public void draw(Graphics2D g) {
		//set the stroke for the square
		g.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
		
		//make the square of solid border for the clock
		//set the color to black for now
		g.setColor(Color.BLACK);

		//draw clock frame
		drawClockFrame(g);
		
		//draw fat ticks
		drawFatTicks(g);
		
		//draw reg ticks
		drawRegTicks(g);
		
		//draw other ticks
		drawSmallTicks(g);
		
		//draw digital clock
		drawDigital(g);
		
		//draw hands
		for(Hand hand : hands) {
			hand.draw(g);
		}
		
		//draw circle in the middle
		Ellipse2D.Double circle = new Ellipse2D.Double(CENTRE-2.5, CENTRE-2.5, 5, 5);
		g.fill(circle);
		
		
	}
	
	/** Draws the clock's frame */
	private void drawClockFrame(Graphics2D g) {
		//change the color to fill the square background
		g.setColor(Color.DARK_GRAY);
		
		//fill the rect
		g.fillRect(2, 2, SCALE-4, SCALE-4);
		
		//set the sroke back to black and stroke bigger
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(2,1, 1));
		
		//draw the square for the clock
		g.drawRect(2, 2, SCALE-4, SCALE-4);
		
		//set color back to black to draw the outline
		g.setColor(Color.BLACK);
		
		//change the stroke to be bigger
		g.setStroke(new BasicStroke(4));
		
		//make the circle for the clock
		Ellipse2D.Double circle = new Ellipse2D.Double(7, 7, SCALE-14, SCALE-14);
		g.draw(circle);
		
		//change the color of the inner cirlce
		g.setPaint(clockColor);
		
		//fill the circle
		g.fill(circle);
		
		
	}
	
	/**draw the 4 fat ticks, where at the four cardinal
		directions (0,3,6,9)*/
	private void drawFatTicks(Graphics2D g) {
		g.setStroke(new BasicStroke(3));
		//add pi/2 each time to get the position of the fat ticks
		for(int i = 0; i < NUM_FAT_TICKS; i++) {
			g.setColor(new Color(255, 255, 102));
			g.draw(new Line2D.Double(CENTRE, 12, CENTRE, 22));
			
			//rotate the clock around the centre by pi/2 to draw the fat ticks
			g.rotate(Math.PI/2, CENTRE, CENTRE);
		}
	}
	
	/**draw the regular ticks the position of the 12 ticks*/
	private void drawRegTicks(Graphics2D g) {
		g.setStroke(new BasicStroke(1));
		//add the ticks every pi/6
		for(int i = 0; i < NUM_TICKS; i++) {
			
			g.draw(new Line2D.Double(CENTRE, 12, CENTRE, 18));
			//rotate the clock around the centre by pi/6 to draw the regular ticks
			g.rotate(Math.PI/6, CENTRE, CENTRE);
		}
	}
	
	/**draw the smaller ticks for the minutes*/
	private void drawSmallTicks(Graphics2D g) {
		g.setColor(new Color(255,255,204));
		g.setStroke(new BasicStroke(0.5f));
		//add the ticks every 2pi/60
		for(int i = 0; i < 60; i++ ) {
			g.draw(new Line2D.Double(CENTRE, 12, CENTRE, 15));
			//rotate everything 2pi/60
			g.rotate(Math.PI/30, CENTRE, CENTRE);
		}
	}	
	/**draw the digital clock*/
	private void drawDigital(Graphics2D g) {
		
		//draw a rectangle over it
		g.setPaint(rectColor);
		g.fillRect(CENTRE-38, CENTRE+30, 79, 25);
		
		//draw digital clock
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		g.setColor(new Color(255,255,102,200));
		g.setFont(new Font("Times New Roman", Font.BOLD, 20));
		g.drawString(formatter.format(time), CENTRE-35, CENTRE+50);
	}
}	
