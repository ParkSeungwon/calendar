import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;
import javax.swing.*;

class MainFrame extends JFrame implements ActionListener
{
	Calendar cal;
	Controller cInterface;
	DailySchedule schedule;
	Box hbox = new Box(BoxLayout.LINE_AXIS);
	Box command = new Box(BoxLayout.LINE_AXIS);
	Box vbox = new Box(BoxLayout.PAGE_AXIS);
	JButton left = new JButton("<"), right = new JButton(">");
	JLabel year_month = new JLabel("");

	public MainFrame(Controller ci)
	{
		cInterface = ci;
		ci.mainInterface = this;
		left.addActionListener(this);
		right.addActionListener(this);
		cal = new Calendar(ci);
		schedule = new DailySchedule(ci);

		add(hbox);
		hbox.add(vbox);
		vbox.add(command);
		command.add(left); command.add(year_month); command.add(right);
		String[][] s = new String[6][7];
		s[4][5] = "21";
		cal.setDate(s);
		int[] ratio = {1,2,3};
		String[] s2 = {"fnalksd", "fjlsk", "fdk"};
		schedule.set(s2, ratio);
		vbox.add(new JScrollPane(cal));//JScrollPane make column header visible
		hbox.add(schedule);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Calendar");
		setSize(1100, 800);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) 
	{
		JButton bt = (JButton)e.getSource();
		if(bt == left) cInterface.prev();
		else if(bt == right) cInterface.next();
	}
}

