package in.co.jurist.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateFormatter {

	private static final Logger logger = LoggerFactory
			.getLogger(DateFormatter.class);

	public DateFormatter() {

	}

	/**
	 * Change String Date of format[yyyy-MM-dd] to Java.util.Date
	 * 
	 * @param stringDate
	 *            input date in String
	 * @return java.util.Date
	 */
	public static Date changeDateFormat(String stringDate) {
		SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return inputDateFormat.parse(stringDate);
		} catch (ParseException e) {
			logger.debug("Error parsing date. {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Change java.util.Date to String Date of format[dd MMM, yyyy]
	 * 
	 * @param date
	 * 
	 * @return String
	 */
	public static String changeDateFormat(Date date) {
		SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return inputDateFormat.format(date);
	}

	/**
	 * Convert the posted time to displayable format
	 * 
	 * @param postedOn
	 * @return String
	 */
	public static String getQuestionPostDate(Calendar postedOn) {

		String postDate = "";

		Calendar now = Calendar.getInstance(Locale.getDefault());

		if (now.get(Calendar.YEAR) != postedOn.get(Calendar.YEAR)) {
			int year = now.get(Calendar.YEAR) - postedOn.get(Calendar.YEAR);
			postDate = year + Constants.SPACE_DELIMITER
					+ (year > 1 ? "years" : "year");
		} else {
			if (now.get(Calendar.MONTH) != postedOn.get(Calendar.MONTH)) {
				int month = now.get(Calendar.MONTH)
						- postedOn.get(Calendar.MONTH);
				postDate = month + Constants.SPACE_DELIMITER
						+ (month > 1 ? "months" : "month");
			} else {
				if (now.get(Calendar.DAY_OF_MONTH) != postedOn
						.get(Calendar.DAY_OF_MONTH)) {
					int day = now.get(Calendar.DAY_OF_MONTH)
							- postedOn.get(Calendar.DAY_OF_MONTH);
					postDate = day + Constants.SPACE_DELIMITER
							+ (day > 1 ? "days" : "day");
				} else {
					if (now.get(Calendar.HOUR_OF_DAY) != postedOn
							.get(Calendar.HOUR_OF_DAY)) {
						int hour = now.get(Calendar.HOUR_OF_DAY)
								- postedOn.get(Calendar.HOUR_OF_DAY);
						postDate = hour + Constants.SPACE_DELIMITER
								+ (hour > 1 ? "hours" : "hour");
					} else {
						if (now.get(Calendar.MINUTE) != postedOn
								.get(Calendar.MINUTE)) {
							int min = now.get(Calendar.MINUTE)
									- postedOn.get(Calendar.MINUTE);
							postDate = min + Constants.SPACE_DELIMITER
									+ (min > 1 ? "mins" : "min");
						} else {
							if (now.get(Calendar.SECOND) != postedOn
									.get(Calendar.SECOND)) {
								int sec = now.get(Calendar.SECOND)
										- postedOn.get(Calendar.SECOND);
								postDate = sec + Constants.SPACE_DELIMITER
										+ (sec > 1 ? "secs" : "sec");
							}
						}

					}
				}

			}
		}
		postDate += Constants.SPACE_DELIMITER + "ago";

		return postDate;
	}
}
