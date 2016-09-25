package Generate_Random;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

public class Generate {

	public static int Between(int start, int end) {
		return start + (int) Math.round(Math.random() * (end - start));
	}

	public static float BetweenFloat(int start, int end) {
		DecimalFormat df = new DecimalFormat() ;
		df.setMaximumFractionDigits(1);
		return Float.parseFloat(df.format(start +  Math.random() * (end - start)).replace(",","."));
	}
	
	
	public static LinkedList<String> getTooManyDate(int nombre_de_date , int startDate , int endDate) {
		GregorianCalendar gc = new GregorianCalendar();
		LinkedList<String> li = new LinkedList<String>();
		while (nombre_de_date-- > 0) {
			int year = Between(startDate, endDate );

			gc.set(gc.YEAR, year);

			int dayOfYear = Between(1, gc.getActualMaximum(gc.DAY_OF_YEAR));

			gc.set(gc.DAY_OF_YEAR, dayOfYear);


			String month = new Integer((gc.get(gc.MONTH) + 1)).toString();
			String str_month = month.toString().length() == 1 ? "0" + month
					: month.toString();

			String day = new Integer(gc.get(gc.DAY_OF_MONTH)).toString();
			String str_day = day.toString().length() == 1 ? "0" + day : day
					.toString();
                        
                        Integer hours = Generate.Between(8,20);
	        String str_hours = hours.toString().length() == 1 ? "0" + hours : hours.toString() ; 
	        Integer minute = Generate.Between(0,59);
	        String str_minute = minute.toString().length() == 1 ? "0" + minute : minute.toString() ; 
	        Integer seconde = Generate.Between(0,59);
	        String str_seconde = seconde.toString().length() == 1 ? "0" + seconde : seconde.toString() ; 
  
                
                
			li.add(str_day +"/" + str_month + "/" + gc.get(gc.YEAR) + " "+str_hours+ ":" +str_minute+ ":" +str_seconde);
		}

		return li;
	}
	
        public static LinkedList<String> getTooManyDateFormatDateTooSql(int nombre_de_date , int startDate , int endDate) {
		GregorianCalendar gc = new GregorianCalendar();
		LinkedList<String> li = new LinkedList<String>();
		while (nombre_de_date-- > 0) {
			int year = Between(startDate, endDate );

			gc.set(gc.YEAR, year);

			int dayOfYear = Between(1, gc.getActualMaximum(gc.DAY_OF_YEAR));

			gc.set(gc.DAY_OF_YEAR, dayOfYear);


			String month = new Integer((gc.get(gc.MONTH) + 1)).toString();
			String str_month = month.toString().length() == 1 ? "0" + month
					: month.toString();

			String day = new Integer(gc.get(gc.DAY_OF_MONTH)).toString();
			String str_day = day.toString().length() == 1 ? "0" + day : day
					.toString();
                          Integer hours = Generate.Between(8,20);
	        String str_hours = hours.toString().length() == 1 ? "0" + hours : hours.toString() ; 
	        Integer minute = Generate.Between(0,59);
	        String str_minute = minute.toString().length() == 1 ? "0" + minute : minute.toString() ; 
	        Integer seconde = Generate.Between(0,59);
	        String str_seconde = seconde.toString().length() == 1 ? "0" + seconde : seconde.toString() ; 
                       
                
                gc.set(year, Integer.parseInt(str_month), dayOfYear , Integer.parseInt(str_hours), Integer.parseInt(str_minute), Integer.parseInt(str_seconde));
			li.add(new SimpleDateFormat("YYYY-MM-dd HH:MM:ss").format(gc.getTime()));
		}

		return li;
	}
	public static float TVA(int start, int end) {
		DecimalFormat df = new DecimalFormat() ;
		df.setMaximumFractionDigits(1);
		return Float.parseFloat(df.format(start +  Math.random() * (end - start)).replace(",","."));
	}
}
