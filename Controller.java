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
	
	void date_click(String date)
	{//canlendat table date click event
		int day = Integer.parseInt(date);
		model.set(Calendar.DAY_OF_MONTH, day);
		
		schedule.removeAllElements();
		ratio.removeAllElements();
		int prev = 0;
		TreeMap<int[], String> hmap = model.get_today_schedule();
		for(Map.Entry<int[], String> entry : hmap.entrySet()) {
			int[] key = entry.getKey();
			String memo = entry.getValue();
			if(key[3] != prev) {//blank schedule button
				schedule.add("");
				ratio.add(key[3] - prev);
			}
			schedule.add(memo);
			ratio.add(key[4] - key[3]);
			prev = key[4];
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
		main.year_month.setText(model.year() + " " + month_name[model.month()]);
	}

	public void today()
	{//go to today
		int prev_year = model.year();
		int prev_month = model.month();
		model.today();
		int target_year = model.year();
		int target_month = model.month();
		model.set(Calendar.YEAR, prev_year);
		model.set(Calendar.MONTH, prev_month);
		while(target_year > model.year()) next();
		while(target_year < model.year()) prev();
		while(target_month > model.month()) next();
		while(target_month < model.month()) prev();

		model.today();
		date_click(Integer.toString(model.day()));
		main.cal.select(model.getWeekDay()-1, model.get(Calendar.WEEK_OF_MONTH)-1);
	}

	void add_schedule(int a, int b, String memo)
	{//add a new schedule and repaint, Popup ok click event
		int[] time = { model.year(), model.month(), model.day(), a, b };
		model.add(time, memo);
		date_click(Integer.toString(model.day()));
	}
}
