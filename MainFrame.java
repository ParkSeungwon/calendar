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
	JButton	today = new JButton("today");
	JLabel year_month = new JLabel("");

	public MainFrame(Controller ci)
	{
		cInterface = ci;
		ci.main= this;
		left.addActionListener(this);
		right.addActionListener(this);
		today.addActionListener(this);
		cal = new Calendar(ci);
		schedule = new DailySchedule(ci);

		add(hbox);
		hbox.add(vbox);
		vbox.add(command);
		command.add(left); command.add(year_month); 
		command.add(right); command.add(today);
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
		else if(bt == today) cInterface.today();
	}
}

