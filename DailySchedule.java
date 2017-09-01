import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;
import java.awt.Color;


class DailySchedule extends JPanel implements ActionListener
{
	final int vlength = 800, hlength = 200, labelwidth = 100;
	JButton[] buttons = null;
	Controller cInterface;//controller interface
	int sz = 0;//number of buttons

	public DailySchedule(Controller ci) 
	{
		cInterface = ci;
		setSize(hlength + labelwidth, vlength);
		setLayout(null);
		JLabel[] timetable = new JLabel[24];
		for(int i=0; i<24; i++) {
			timetable[i] = new JLabel(i + " ~ " + (i+1), SwingConstants.CENTER); 
			timetable[i].setBounds(0, i * vlength / 24, labelwidth, vlength / 24);
			timetable[i].setBorder(BorderFactory.createLineBorder(Color.black));
			add(timetable[i]);
		}
	}

	void set(String[] schedule, int[] ratio) 
	{//populate panel with size ratio and text
		sz = schedule.length;
		assert(sz == ratio.length);
		if(buttons != null) for(JButton jb : buttons) remove(jb);
		int sum = 0, vpos = 0;
		for(int r : ratio) sum += r;
		buttons = new JButton[sz];
		for(int i=0; i<sz; i++) {
			int lng = ratio[i] * vlength / sum;
			buttons[i] = new JButton(schedule[i]);
			buttons[i].setBounds(labelwidth, vpos, hlength, lng); vpos += lng;
			buttons[i].addActionListener(this);
			add(buttons[i]);
		}
	}

	public void actionPerformed(ActionEvent e)
	{//click nth button
		JButton bt = (JButton)e.getSource();
		for(int i=0; i<sz; i++) 
			if(bt == buttons[i]) cInterface.schedule_click(i);
	}
}
