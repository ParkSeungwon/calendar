import java.util.*;
import java.util.Calendar;
import java.io.*;

class Model extends GregorianCalendar implements Comparator<int[]>
{
	private TreeMap<int[], String> scheduleNmemo = new TreeMap<int[], String>(this);
	private TreeMap<int[], String> tmap;//hashmap is not sorted automatically

	public Model()
	{
		try {
			FileInputStream fis = new FileInputStream("schedule.txt");
			ObjectInputStream ois = new ObjectInputStream(fis);
			scheduleNmemo = (TreeMap)ois.readObject();
			ois.close();
			fis.close();
			print();
		} catch(IOException e) {
			e.printStackTrace();
			save();
		} catch(ClassNotFoundException e) { e.printStackTrace(); }
	}

	private int compare(int[] a, int[] b, int n)
	{
		if(n == a.length) return 0;
		if(a[n] == b[n]) return compare(a, b, n+1);
		return a[n] - b[n];
	}

	public int compare(int[] a , int[] b)
	{//comparator implementation
		return compare(a, b, 0);
	}

	int getMaxDays() { return getActualMaximum(Calendar.DAY_OF_MONTH); }
	int getWeekDay() { return get(Calendar.DAY_OF_WEEK); }
	int year() { return get(Calendar.YEAR); }
	int month() { return get(Calendar.MONTH); }
	int day() { return get(Calendar.DAY_OF_MONTH); }

	synchronized TreeMap<int[], String> get_today_schedule()
	{
		tmap = new TreeMap<int[], String>(this);
		int[] this_day = {year(), month(), day()};
		for(Map.Entry<int[], String> entry : scheduleNmemo.entrySet()) {
			int[] key = entry.getKey();
			String memo = entry.getValue();
			boolean ok = true;
			for(int i=0; i<3; i++) if(key[i] != this_day[i]) ok = false;
			if(ok) tmap.put(key, memo);
		}
		return tmap;
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

	synchronized private void print() 
	{
		for(Map.Entry<int[], String> entry : scheduleNmemo.entrySet()) {
			int[] key = entry.getKey();
			String memo = entry.getValue();
			for(int s : key) System.out.println(s + " "); 
			System.out.println(memo);	
		}
	}
}
