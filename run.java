
public class run 
{
	public static void main(String[] args) 
	{
		Controller ci = new Controller();
		MainFrame m = new MainFrame(ci);
		ci.main = m;
		ci.next();
		ci.today();
	}
}
