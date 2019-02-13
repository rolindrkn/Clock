import java.awt.*;
import javax.swing.JComponent;
import javax.swing.Timer;
import java.time.LocalTime;

/** Displays a clock 
	@author Jeremy H for CPSC 1181 201730 at Langara
	@author Rosalind Ng
	@version 2017-10-06
*/
public class ClockComponent extends JComponent {

	private final LocalTime time;

	public ClockComponent() {
		this(null);
		// create a timer to update our clock 60 times per second
		new Timer(1000/60, (a) -> {update();}).start();
		
	}

	public ClockComponent(LocalTime aTime) {
		time = aTime;
	}

	private void update() {
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		// the component's dimensions
		final int w = getWidth();  // width
		final int h = getHeight(); // height
		// clear the specified rectangle by filling it with the background colour
		// useful when resizing because we want to erase what there was before
		g.clearRect(0, 0, w, h);

		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(
			RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);

		//move the canvas to the center of the screen
		g2.translate(w/2, h/2);


		//scale the canvas to be the size of the clock
		int min = Math.min(w,h);
		double scaleFactor = min/(double)Clock.SCALE;
		g2.scale(scaleFactor,scaleFactor);

		//move canvas back to the top left of square of the clock
		g2.translate(-Clock.CENTRE, -Clock.CENTRE);
		
		//make a new clock
		//bug is coming from here
		Clock c = new Clock(time);
		
		//draw the clock 
		c.draw(g2);
		g2.dispose();
	}
}
