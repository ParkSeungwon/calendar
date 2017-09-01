import java.util.*;
import java.io.*;

class Model
{
	int year, month, day;//current
	HashMap<int[], String> scheduleNmemo = new HashMap<int[], String>();

	public Model()
	{
		GregorianCalendar cal = new GregorianCalendar();
		year = cal.YEAR;
		month = cal.MONTH;
		day = cal.DAY_OF_MONTH;

		try {
			FileInputStream fis = new FileInputStream("schedule.txt");
			ObjectInputStream ois = new ObjectInputStream(fis);
			scheduleNmemo = (HashMap)ois.readObject();
			ois.close();
			fis.close();
		} catch(IOException e) {
			e.printStackTrace();
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

	int getMaxDays(int year, int month)
	{
		GregorianCalendar cal = new GregorianCalendar(year, month, 1);
		return cal.getActualMaximum(cal.DAY_OF_MONTH);
	}

	int getWeekDay(int year, int month, int day)
	{
		GregorianCalendar cal = new GregorianCalendar(year, month, day);
		return cal.DAY_OF_WEEK;
	}

	Date today()
	{
		GregorianCalendar cal = new GregorianCalendar();
		int year = cal.YEAR;
		int month = cal.MONTH;
		int day = cal.DAY_OF_MONTH;
		return new Date(year, month, day);
	}
}
