import java.util.Calendar;

class Controller
{
	MainFrame main;
	Model model = new Model();
	
	public Controller()
	{
	}

	void date_click(String date)
	{
		System.out.println(date);
	}

	void schedule_click(int n)
	{
		System.out.println("schedule " + n + "th is clicked");
	}

	void prev()
	{
		model.prev_month();
		setDate();
		main.year_month.setText(model.get(Calendar.YEAR) + " " + 
				(1 + model.get(Calendar.MONTH)));
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
	}


	void next()
	{
		model.next_month();
		setDate();
		main.year_month.setText(model.get(Calendar.YEAR) + " " + 
				(1 + model.get(Calendar.MONTH)));
	}

	void add_schedule(int a, int b, String memo)
	{
	}
}
