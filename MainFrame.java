import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;
import javax.swing.*;

class MainFrame extends JFrame implements ActionListener
{
	Calendar cal;
	Controller cInterface;
	DailySchedule schedule;
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

		add(vbox);
		command.add(left); command.add(year_month); 
		command.add(right); command.add(today);
		vbox.add(command);
		vbox.add(cal.getTableHeader());
		vbox.add(cal);//JScrollPane make column header visible
		vbox.add(schedule);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Calendar");
		setSize(400, 940);
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

