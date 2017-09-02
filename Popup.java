import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.BoxLayout;

class Popup extends JDialog implements ActionListener, ChangeListener
{
	Controller cInterface;
	JSlider from = new JSlider(), to = new JSlider();
	JLabel lfrom = new JLabel(), lto = new JLabel();
	JButton ok = new JButton("ok"), cancel = new JButton("cancel");
	JTextArea memo = new JTextArea("");
	Box hbox[] = new Box[3];
	Box vbox = new Box(BoxLayout.PAGE_AXIS);

	public void actionPerformed(ActionEvent e)
	{//button event
		JButton bt = (JButton)e.getSource();
		setVisible(false);
		if(bt == ok) {
			int a = from.getValue(), b = to.getValue();
			assert(a != b);
			String s = memo.getText();
			if(a < b) cInterface.add_schedule(a, b, s);
			else cInterface.add_schedule(b, a, s);
		} 
	}

	public void stateChanged(ChangeEvent e)
	{//slider event
		JSlider sl = (JSlider)e.getSource();
		if(sl == from) lfrom.setText(parse(from.getValue()));
		else lto.setText(parse(to.getValue()));
	}

	public Popup(int from_time, int to_time, String contents, Controller ci, JFrame jf)
	{//time is measured by minutes
		super(jf, "Memo", true);
		cInterface = ci;
		lfrom.setText(parse(from_time));
		lto.setText(parse(to_time));
		from.setMinimum(from_time);
		from.setMaximum(to_time);
		from.setValue(from_time);
		from.addChangeListener(this);
		to.setMinimum(from_time);
		to.setMaximum(to_time);
		to.setValue(to_time);
		to.addChangeListener(this);
		ok.addActionListener(this);
		cancel.addActionListener(this);
		memo.setText(contents);
		for(int i=0; i<3; i++) hbox[i] = new Box(BoxLayout.LINE_AXIS);
		hbox[0].add(lfrom); hbox[0].add(from);
		hbox[1].add(lto); 	hbox[1].add(to);
		hbox[2].add(ok); 	hbox[2].add(cancel);
		vbox.add(hbox[0]);
		vbox.add(hbox[1]); 
		vbox.add(memo); 
		vbox.add(hbox[2]);
		add(vbox);
		setSize(400, 400);
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	private String parse(int time)
	{//int to 13:00
		int hour = time / 60;
		int minute = time % 60;
		return String.format("%02d : %02d", hour, minute);
	}
}

