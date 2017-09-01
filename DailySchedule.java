import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;

class DailySchedule extends JPanel implements ActionListener
{
	JButton[] buttons;
	Controller cInterface;
	final int vlength = 800, hlength = 200;
	int sz = 0;

	public DailySchedule(Controller ci) 
	{
		cInterface = ci;
		setSize(hlength, vlength);
		setLayout(null);
	}

	void set(String[] schedule, int[] ratio) 
	{
		sz = schedule.length;
		assert(sz == ratio.length);
		removeAll();
		int sum = 0, vpos = 0;
		for(int r : ratio) sum += r;
		buttons = new JButton[sz];
		for(int i=0; i<sz; i++) {
			int lng = ratio[i] * vlength / sum;
			buttons[i] = new JButton(schedule[i]);
			buttons[i].setBounds(0, vpos, hlength, lng);
			buttons[i].addActionListener(this);
			add(buttons[i]);
			vpos += lng;
		}
	}

	public void actionPerformed(ActionEvent e)
	{
		JButton bt = (JButton)e.getSource();
		for(int i=0; i<sz; i++) 
			if(bt == buttons[i]) cInterface.schedule_click(i);
	}
}
