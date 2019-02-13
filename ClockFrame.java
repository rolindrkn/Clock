import javax.swing.JFrame;
import java.time.LocalTime;

public class ClockFrame {

	private final static int WIDTH = 500;
	private final static int HEIGHT = 500;

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(WIDTH, HEIGHT);
		frame.setTitle("A Clock");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ClockComponent clockComp;
		switch (args.length) {
			case 0:
				clockComp = new ClockComponent();
				break;
			case 1:
				clockComp = new ClockComponent(LocalTime.parse(args[0]));
				System.out.println(LocalTime.parse(args[0]).toString());
				break;
			case 3:
				clockComp = new ClockComponent(LocalTime.of(
					Integer.parseInt(args[0]),
					Integer.parseInt(args[1]),
					Integer.parseInt(args[2])));
				break;
			default:
				throw new IllegalArgumentException("Invlaid command line args");
		}
		frame.setContentPane(clockComp);
		frame.setVisible(true);
	}
}
