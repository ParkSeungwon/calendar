import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;

class DailySchedule extends Box implements ActionListener
{
	JButton[] buttons;
	JPanel[] panels;
	Controller cInterface;
	final int vlength = 800, hlength = 200;
	int sz = 0;

	public DailySchedule(Controller ci) 
	{
		super(BoxLayout.PAGE_AXIS);
		cInterface = ci;
		setSize(hlength, vlength);
	}

	void set(String[] schedule, int[] ratio) 
	{
		sz = schedule.length;
		assert(sz == ratio.length);
		removeAll();
		int sum = 0;
		for(int r : ratio) sum += r;
		buttons = new JButton[sz];
		panels = new JPanel[sz];
		for(int i=0; i<sz; i++) {
			buttons[i] = new JButton(schedule[i]);
			panels[i] = new JPanel();
			panels[i].add(buttons[i]);
			panels[i].setSize(hlength, ratio[i] / sum * vlength);
			add(panels[i]);
//			buttons[i].setSize(hlength, ratio[i] / sum * vlength);
			buttons[i].addActionListener(this);
		}
	}

	public void actionPerformed(ActionEvent e)
	{
		JButton bt = (JButton)e.getSource();
		for(int i=0; i<sz; i++) 
			if(bt == buttons[i]) cInterface.schedule_click(i);
	}
}
