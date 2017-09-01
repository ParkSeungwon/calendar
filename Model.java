import java.util.*;
import java.util.Calendar;
import java.io.*;

class Model extends GregorianCalendar
{
	HashMap<int[], String> scheduleNmemo = new HashMap<int[], String>();

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
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	void add(int[] time, String memo)
	{
		scheduleNmemo.put(time, memo);
	}

	void del(int[] time)
	{
		scheduleNmemo.remove(time);
	}
	void save()
	{
		try {
			FileOutputStream fos = new FileOutputStream("schedule.txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(scheduleNmemo);
			oos.close();
			fos.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	int getMaxDays()
	{
		return getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	int getWeekDay()
	{
		return get(Calendar.DAY_OF_WEEK);
	}

	void today()
	{
		GregorianCalendar cal = new GregorianCalendar();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		set(year, month, day);
	}

	void prev_month()
	{
		if(get(Calendar.MONTH) == 0) {
			roll(Calendar.YEAR, false);
			set(Calendar.MONTH, 11);
		} else roll(Calendar.MONTH, false);
		set(Calendar.DAY_OF_MONTH, 1);
	}

	void next_month()
	{
		if(get(Calendar.MONTH) == 11) {
			roll(Calendar.YEAR, true);
			set(Calendar.MONTH, 0);
		} else roll(Calendar.MONTH, true);
		set(Calendar.DAY_OF_MONTH, 1);
	}
}
