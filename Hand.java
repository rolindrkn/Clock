import java.awt.*;
import java.awt.geom.Line2D;
import java.time.LocalTime;
import java.time.temporal.ChronoField;

/** A hand on the face of a clock.  Each hand is responsible for drawing itself
	For the givien time-unit, time, with the given graphical properties.
	Hand may move smoothly of jump between ticks depending on the arguments
	to the constructor.
	@author Jeremy H for CPSC 1181 201730 at Langara
	@author Rosalind Ng
	@version 2017-10-06
*/
public class Hand {

	private final static double RADIANS = Clock.RADIANS;
	private final static double CENTRE = Clock.CENTRE;
	private final TimeUnit unit;
	private final GraphicsInfo gInfo;
	private final double angle; // the rotation of the hand in radians from 12 o'clock
	private final LocalTime time; //the time 

	/** Creates a hand of a clock. This hand does not reflect partial changes
		in time; it leaps from one tick to the next.
		@param aUnit the time unit that this hand represents
		@param aGinfo the graphical parameters with which to draw this hand
		@param aTime the time value that this hand should 
	*/
	public Hand(TimeUnit aUnit, GraphicsInfo aGinfo, LocalTime aTime) {
		this(aUnit, aGinfo, aTime, null);
	}
	
	/** Creates a hand of a clock. This hand does reflect partial changes
		in time; it moves smoothly from one tick to the next.
		@param aUnit the time unit that this hand represents
		@param aGinfo the graphical parameters with which to draw this hand
		@param aTime the time value that this hand should 
		@param prevHand the hand of the previous, lower, time unit.
			eg: second hand is passed to the minute hand
	*/
	public Hand(TimeUnit aUnit, GraphicsInfo aGinfo, LocalTime aTime, Hand prevHand) {
		// TODO use TDD on me!
		unit = aUnit;
		gInfo = aGinfo;

		time = aTime; 		
		//the angle = radians divided by the of counts the hand makes to make 1 rotation
		// * the current time for that hand + the previous hand's angle / the counts the 
		// current hand makes to make 1 rotation
		//ie. for the minute hand at (00:44:59)
		// 2PI/60 * 44 + (2PI/60 *59 /60) <-- since it takes 60 seconds to make a minute
		// like a ratio of the seconds to minutes 
		//since have to take the prevHand into account, have to have case if the prevHand
		//is null 
		
		if(prevHand == null) {
			angle = RADIANS / unit.maxValue * unit.getValue(time);
		}
		else {
			//System.out.println(prevHand.getAngle());
			angle= (RADIANS / unit.maxValue * unit.getValue(time)) + (prevHand.getAngle()/unit.maxValue);
		}
	}

	/** Gets the angle, clockwise from 12 o'clock, in radians, for this hand.
		@return the angle of this hand clockwise from to 12 o'clock.
			eg: Hour hand at 3 o'clock is +PI/2
	*/
	public double getAngle() {
		return angle;
	}
	
	/** Gets the hand info for this hand.
		@return the hand info for this field.
	*/
	public TimeUnit getHandInfo() {
		return unit;
	}
	
	/** Draws this hand of the clock at the current cnavas position */
	public void draw(Graphics2D g) {
		
 		//set the color 
		g.setColor(gInfo.color);
		//set the stroke style 
		g.setStroke(gInfo.stroke);
		//rotate the canvas at the center
		g.rotate(angle, CENTRE, CENTRE);
		//draw the hand
		g.draw(new Line2D.Double(CENTRE, CENTRE, CENTRE, CENTRE - gInfo.length));
		//rotate the canvas back to original
		g.rotate(-angle, CENTRE, CENTRE); 
		
	}

	
	/**	Describes the temporal attributes of a hand. */
	public enum TimeUnit {
		MILLS(ChronoField.MILLI_OF_SECOND),
		SECOND(ChronoField.SECOND_OF_MINUTE),
		MINUTE(ChronoField.MINUTE_OF_HOUR),
		HOUR(ChronoField.HOUR_OF_AMPM);

		/** The ChonoField of this hand. eg: MINUTE_OF_HOUR */
		private final ChronoField cf;
		/** The maximum value that this ChronoField can have.
			eg: 60 for MINUTE_OF_HOUR */
		public final int maxValue;

		TimeUnit(ChronoField aCF) {
			//aCF is the chronofile value passed in
			cf = aCF;
			//range() gives the range of valid values in this 
			//chronofield
			//getMaximum() gets the maximum value that the this
			//chronofield value can take (ie. MILLI_OF_SECOND 
			// will return 999. since it counts from 0, so want to
			//add 1 so it is 1000
			maxValue = (int) cf.range().getMaximum() + 1;
		}

		/** @return the value of the time field reported by this hand. 
			eg: 6 for the hour hand at 06:45:30pm
		*/
		public int getValue(LocalTime aTime) {
			return aTime.get(cf);
		}
	}

	/**	Describes the graphical attributes of a hand. */
	public static class GraphicsInfo {

		/** The graphical length of this hand
			expressed as a percentage of the readius. */
		public final int length;
		/** The brush stroke used to draw this hand. */
		public final BasicStroke stroke;
		/** The color of the hand. */
		public final Color color;

		/**
			@param aLength the length of the hand as a percentage of the radius
				of the clock face.
			@param aWidth the width of the hand
		*/
		public GraphicsInfo(int aLength, int aWidth) {
			this(aLength, aWidth, Color.BLACK);
		}

		/**
			@param aLength the length of the hand as a percentage of the radius
				of the clock face.
			@param aWidth the width of the hand
			@param Color the color of the hand
		*/
		public GraphicsInfo(int aLength, float aWidth, Color aColor) {
			length = (Clock.SCALE * aLength) / (100 * 2);
			stroke = new BasicStroke(aWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
			color = aColor;
		}
	}
}
