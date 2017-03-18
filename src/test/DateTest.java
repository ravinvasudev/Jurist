package test;

import java.util.Calendar;
import java.util.Locale;

public class DateTest {

	public static void main(String[] args) {
		
		Calendar postedOn = Calendar.getInstance(Locale.getDefault());
		postedOn.set(2015, 05, 12, 11, 12, 00);
		System.out.println(postedOn.getTime());
		
		Calendar now = Calendar.getInstance(Locale.getDefault());
		now.set(2015, 05, 12, 11, 12, 50);
		System.out.println(now.getTime());
		
		if(postedOn.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {			
			if(postedOn.get(Calendar.MONTH) == now.get(Calendar.MONTH)) {				
				if(postedOn.get(Calendar.DAY_OF_MONTH) == now.get(Calendar.DAY_OF_MONTH)) {
					if(postedOn.get(Calendar.HOUR_OF_DAY) == now.get(Calendar.HOUR_OF_DAY)) {					
						if(postedOn.get(Calendar.MINUTE) == now.get(Calendar.MINUTE)) {
							if(postedOn.get(Calendar.SECOND) == now.get(Calendar.SECOND)) {
								
							}
							else {
								int sec = now.get(Calendar.SECOND) - postedOn.get(Calendar.SECOND);
								System.out.println(sec + " seconds ago");
							}
						}
						else {
							int min = now.get(Calendar.HOUR_OF_DAY) - postedOn.get(Calendar.MINUTE);
							System.out.println(min + " minutes ago");
						}
					} else {
						int hour = now.get(Calendar.HOUR_OF_DAY) - postedOn.get(Calendar.HOUR_OF_DAY);
						System.out.println(hour + " hours ago");
					}
				}
				else {
					int day = now.get(Calendar.DAY_OF_MONTH) - postedOn.get(Calendar.DAY_OF_MONTH);
					System.out.println(day + " days ago");
				}
			} else {
				int month = now.get(Calendar.MONTH) - postedOn.get(Calendar.MONTH);
				System.out.println(month + " months ago");
			}
		} else {			
			int year = now.get(Calendar.YEAR) - postedOn.get(Calendar.YEAR);			
			System.out.println(year + " year ago");			
		}
		
		
		/*String date = "2015-02-01";
		SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MM-yyyy");

		try {
			Date formattedInputDate = inputDateFormat.parse(date);

			System.out.println("Unformatted Input Date: " + date);
			System.out.println("Formatted Input Date: " + formattedInputDate);

			String outputDate = outputDateFormat.format(formattedInputDate);
			Date formattedOutputDate = outputDateFormat.parse(outputDate);

			System.out.println("Unformatted Output Date: " + outputDate);
			System.out.println("Formatted Output Date: " + formattedOutputDate);

		} catch (ParseException e) {
			e.printStackTrace();
		}*/

	}

}
