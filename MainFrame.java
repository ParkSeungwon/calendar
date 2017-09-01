import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;
import javax.swing.*;

class MainFrame extends JFrame
{
	Calendar cal;
	DailySchedule schedule;
	Box hbox = new Box(BoxLayout.LINE_AXIS);

	public MainFrame(Controller ci)
	{
		add(hbox);
		cal = new Calendar(ci);
		schedule = new DailySchedule(ci);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		String[][] s = new String[6][7];
		s[4][5] = "21";
		cal.setDate(s);
		int[] ratio = {1,2,3};
		String[] s2 = {"fnalksd", "fjlsk", "fdk"};
		schedule.set(s2, ratio);
		hbox.add(new JScrollPane(cal));//JScrollPane make column header visible
		hbox.add(schedule);
		setTitle("Calendar");
		setSize(800, 800);
		setVisible(true);
	}
}

