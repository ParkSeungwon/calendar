import java.util.*;
import java.util.Calendar;
import java.io.*;

class Model extends GregorianCalendar
{
	private HashMap<int[], String> scheduleNmemo = new HashMap<int[], String>();

	public Model()
	{
		try {
			FileInputStream fis = new FileInputStream("schedule.txt");
			ObjectInputStream ois = new ObjectInputStream(fis);
			scheduleNmemo = (HashMap)ois.readObject();
			ois.close();
			fis.close();
		} catch(IOException e) {
			e.printStackTrace();
			save();
		} catch(ClassNotFoundException e) { e.printStackTrace(); }
	}

	synchronized HashMap<int[], String> get_today_schedule()
	{
		HashMap<int[], String> hmap = new HashMap<int[], String>();
		int[] this_day = {year(), month(), day()};
		for(Map.Entry<int[], String> entry : scheduleNmemo.entrySet()) {
			int[] key = entry.getKey();
			String memo = entry.getValue();
			boolean ok = true;
			for(int i=0; i<3; i++) if(key[i] != this_day[i]) ok = false;
			if(ok) hmap.put(key, memo);
		}
		return hmap;
	}
	
	synchronized void add(int[] time, String memo)
	{
		del(time);
		scheduleNmemo.put(time, memo);
		scheduleNmemo.values().remove("");
		save();
	}

	private void del(int[] time)
	{
		Vector<int[]> v = new Vector<int[]>();
		for(int[] key : scheduleNmemo.keySet()) //cannot remove inside iteration
			if(key[0] == time[0] && key[1] == time[1] && key[2] == time[2] && 
				key[3] <= time[3] && time[4] <= key[4]) v.add(key);
		for(int[] key : v) scheduleNmemo.remove(key);
	}

	private void save()
	{
		try {
			FileOutputStream fos = new FileOutputStream("schedule.txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(scheduleNmemo);
			oos.close();
			fos.close();
		} catch(IOException e) { e.printStackTrace(); }
	}

	int getMaxDays() { return getActualMaximum(Calendar.DAY_OF_MONTH); }
	int getWeekDay() { return get(Calendar.DAY_OF_WEEK); }
	int year() { return get(Calendar.YEAR); }
	int month() { return get(Calendar.MONTH); }
	int day() { return get(Calendar.DAY_OF_MONTH); }

	void today()
	{
		GregorianCalendar cal = new GregorianCalendar();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		set(year, month, day);
	}

	int prev_month()
	{
		if(get(Calendar.MONTH) == 0) {
			roll(Calendar.YEAR, false);
			set(Calendar.MONTH, 11);
		} else roll(Calendar.MONTH, false);
		set(Calendar.DAY_OF_MONTH, 1);
		return getWeekDay();
	}

	int next_month()
	{//return weekday of 1st day
		if(get(Calendar.MONTH) == 11) {
			roll(Calendar.YEAR, true);
			set(Calendar.MONTH, 0);
		} else roll(Calendar.MONTH, true);
		set(Calendar.DAY_OF_MONTH, 1);
		return getWeekDay();
	}
}
