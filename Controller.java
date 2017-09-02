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
	private int selected_day = 1;
	
	public Controller()
	{
	}

	void date_click(String date)
	{//canlendat table date click event
		int day = Integer.parseInt(date);
		selected_day = day;
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
				for(int k : key)System.out.println(k);
				System.out.println(memo);	
				if(key[3] != prev) {//blank schedule button
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

		int sz = schedule.size();
		String[] s = new String[sz];
		int[] l = new int[sz];
		for(int i=0; i<sz; i++) s[i] = schedule.elementAt(i);
		for(int i=0; i<sz; i++) l[i] = ratio.elementAt(i);
		main.schedule.set(s, l);
	}

	void schedule_click(int n)
	{//Schedule button click event
		int start = 0;
		for(int i=0; i<n; i++) start += ratio.elementAt(i);
		int end = start + ratio.elementAt(n);
		Popup popup = new Popup(start, end, schedule.elementAt(n), this, main);
	}

	void prev()
	{//previous button event
		main.cal.select(model.prev_month() - 1, 0);
		setDate();
		date_click("1");
	}
	void next()
	{//show next month
		main.cal.select(model.next_month() - 1, 0);
		setDate();
		date_click("1");
	}

	private void setDate()
	{//populate table with current month
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


	void add_schedule(int a, int b, String memo)
	{//add a new schedule and repaint, Popup ok click event
		int[] time = { model.get(Calendar.YEAR), model.get(Calendar.MONTH), 
			selected_day, a, b };
		model.scheduleNmemo.put(time, memo);
		model.save();
		date_click(Integer.toString(selected_day));
	}
}
