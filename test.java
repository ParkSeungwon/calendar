import java.io.*;
import java.util.Calendar;
import java.util.*;

class test
{
	public test()
	{
	}

	public static void main(String[] s)
	{
		int[] k = {1,2,3,4,5};
		Model model = new Model();
		TreeMap<int[], String> scheduleNmemo = new TreeMap<int[], String>(model);
		scheduleNmemo.put(k, "fd");
	}
}

