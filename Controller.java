import java.util.Calendar;
import java.util.*;

class Controller
{
	MainFrame main;
	private Model model = new Model();
	private final String[] month_name = {
		"JAN", "Feb", "Mar", "Apr", "May", "Jun", 
		"Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
	};
	private	Vector<String> schedule = new Vector<String>();
	private	Vector<Integer> ratio = new Vector<Integer>();
	
	public Controller()
	{
	}

	void date_click(String date)
	{
		int day = Integer.parseInt(date);
		int month = model.get(Calendar.MONTH);
		int year = model.get(Calendar.YEAR);
		int[] this_day = {year, month, day};
		schedule.removeAllElements();
		ratio.removeAllElements();
		int prev = 0;
		for(Map.Entry<int[], String> entry : model.scheduleNmemo.entrySet()) {
			int[] key = entry.getKey();
			String memo = entry.getValue();
			boolean ok = true;
			for(int i=0; i<3; i++) if(key[i] != this_day[i]) ok = false;
			if(ok) {
				if(prev != 0 && key[3] != prev) {
					schedule.add("");
					ratio.add(key[3] - prev);
				}
				schedule.add(memo);
				ratio.add(key[4] - key[3]);
				prev = key[4];
			}
		}
		if(prev != 24 * 60) {
			schedule.add("");
			ratio.add(24 * 60 - prev);
		}
		String[] s = new String[schedule.size()];
		int[] l = new int[ratio.size()];
		for(int i=0; i<s.length; i++) s[i] = schedule.elementAt(i);
		for(int i=0; i<l.length; i++) l[i] = ratio.elementAt(i);
		main.schedule.set(s, l);
	}

	void schedule_click(int n)
	{
//		System.out.println("schedule " + n + "th is clicked");
		int start = 0;
		for(int i=0; i<n-1; i++) start += ratio.elementAt(i);
		int end = ratio.elementAt(n);
		Popup popup = new Popup(start, end, schedule.elementAt(n), this, main);
	}

	void prev()
	{
		model.prev_month();
		setDate();
	}

	private void setDate()
	{
		String[][] s = new String[6][7];
		int days = model.getMaxDays();
		int weekday = model.getWeekDay();
		int n = 1;
		for(int i=0; i<6; i++) for(int j=0; j<7; j++) {
			s[i][j] = n >= weekday && n < days + weekday ?
				Integer.toString(n - weekday + 1) : "";
			n++;
		}
		main.cal.setDate(s);
		main.year_month.setText(model.get(Calendar.YEAR) + " " + 
				month_name[model.get(Calendar.MONTH)]);
	}

	void next()
	{
		model.next_month();
		setDate();
	}

	void add_schedule(int a, int b, String memo)
	{
	}
}
