import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;
import java.awt.Color;

class Calendar extends JTable
{
	Controller cinterface;//daily schedule
	static String[] day = {"S", "M", "T", "W", "T", "F", "S"};
	static Object[][] date = new Object[6][7];//6 lines with 7 day is needed

	public Calendar(Controller ci) 
	{
		super(date, day);
		cinterface = ci;
		int n = 0;
		addMouseListener(new MouseClicked());
		setCellSelectionEnabled(true);
		setSelectionBackground(Color.cyan);
		setDefaultEditor(Object.class, null);//disable cell edit
		setSize(500, 500);
	}

	void setDate(String[][] date)
	{
		for(int j=0; j<6; j++) for(int i=0; i<7; i++) setValueAt(date[j][i], j, i);
	}

	class MouseClicked extends MouseAdapter
	{//mouse event
		public void mouseClicked(MouseEvent e)
		{
			int row = rowAtPoint(e.getPoint());
			int col = columnAtPoint(e.getPoint());
			cinterface.date_click((String)getValueAt(row, col));
		}
	}
}
