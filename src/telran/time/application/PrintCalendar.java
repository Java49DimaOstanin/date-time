package telran.time.application;

import java.time.*;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.util.Arrays;
import java.util.Locale;

public class PrintCalendar {
	private static final int TITLE_OFFSET = 10;
	static DayOfWeek daysOfWeek = LocalDate.now().getDayOfWeek();

	public static void main(String[] args) {
		try {
//			System.out.println(DayOfWeek.valueOf("SUNDAY").getValue());
			RocordArguments recordArguments = getRecordArguments(args);
			printCalendar(recordArguments);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	
	private static void printCalendar(RocordArguments recordArguments) {
		printTitle(recordArguments.month(), recordArguments.year());
		printWeekDays(recordArguments.firstWeekDay());
		printDays(recordArguments.month(), recordArguments.year(), recordArguments.firstWeekDay());
		
	}

	private static void printDays(int month, int year, DayOfWeek weekDayStart) {
		int days = getNumberOfDays(month,year);
		int currentWeekDay = getFirstWeekDay(month,year ,weekDayStart);
		printOffset(currentWeekDay);
		for(int day = 1; day <= days;day++) {
			
			System.out.printf("%4d",day);
			currentWeekDay++;
			if(currentWeekDay == 7) {
				currentWeekDay = 0;
				System.out.println();
			}
		}
	}

	private static void printOffset(int currentWeekDay) {
		System.out.printf("%s" , " ".repeat(4 * currentWeekDay));
		
	}

	private static int getFirstWeekDay(int month, int year ,DayOfWeek weekDayStart) {
		LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
	    int weekDayNumber = firstDayOfMonth.getDayOfWeek().getValue();
	    int startDayNumber = weekDayStart.getValue();
	    int offset = weekDayNumber - startDayNumber;
	    if (offset < 0) {
	        offset += 7;
	    }
	    return offset;
	}

	private static int getNumberOfDays(int month, int year) {
		YearMonth ym = YearMonth.of(year, month);	
	return ym.lengthOfMonth();
	}

	private static void printWeekDays(DayOfWeek weekDayStart) {
		DayOfWeek[] daysOfWeek = getOrderedDaysOfWeek(weekDayStart);
		System.out.print("  ");
		Arrays.stream(daysOfWeek).forEach(dw -> System.out.printf("%s ",dw.getDisplayName(TextStyle.SHORT, Locale.getDefault())));
		System.out.println();
	}

	private static DayOfWeek[] getOrderedDaysOfWeek(DayOfWeek weekDayStart) {
		DayOfWeek[] orderedDaysOfWeek = new DayOfWeek[7];
	    for (int i = 0; i < 7; i++) {
	        orderedDaysOfWeek[i] = weekDayStart.plus(i);
	    }
	    return orderedDaysOfWeek;
	}


	private static void printTitle(int monthNumber, int year) {
		// <year> , <month name>
		Month month = Month.of(monthNumber);
		String monthName = month.getDisplayName(TextStyle.FULL_STANDALONE,Locale.getDefault());
		
		System.out.printf("%s%s, %d\n", " ".repeat(TITLE_OFFSET),monthName,year );
		
		
		
		
	}

	private static RocordArguments getRecordArguments(String[] args) throws Exception {
		LocalDate ld = LocalDate.now();
		int month = args.length == 0 ? ld.get(ChronoField.MONTH_OF_YEAR) : getMonth(args[0]);
		int year = args.length > 1 ? getYear(args[1]): ld.get(ChronoField.YEAR);
		DayOfWeek weekDayStart = args.length > 2 ? getWeekDayStart(args[2]): DayOfWeek.MONDAY;
		
		return new RocordArguments(month,year,weekDayStart);
	}

	private static DayOfWeek getWeekDayStart(String stringWeekStart) throws Exception {
		String message = "";
		DayOfWeek weekDayStart = null;
		try {
			weekDayStart = DayOfWeek.valueOf(stringWeekStart.toUpperCase());
			if(!weekDayStart.isSupported(ChronoField.DAY_OF_WEEK)) {
				message = "Incorect name of Week";
			}
		} catch (NumberFormatException e) {
			message = "Week must be UpperCase String";
		}
		if(!message.isEmpty()) {
			throw new Exception(message);
		}
		return weekDayStart;
	}

	private static int getYear(String yearStr) throws Exception {
		String message = "";
		int year = 0;
		try {
			 year = Integer.parseInt(yearStr);
			if(year < 0) {
				message = "Year must be a positive number";
			}
		} catch (NumberFormatException e) {
			message = "year must be a number";
		}
		if(!message.isEmpty()) {
			throw new Exception(message);
		}
		return year;
	}

	private static int getMonth(String monthstring) throws Exception {
		String message = "";
		int month = 0;
		try {
			month = Integer.parseInt(monthstring);
			if(month < 1 || month > 12) {
				message = "month must be in the range [1-12]";
			}
		} catch (NumberFormatException e) {
			message = "month must be a number";
		}
		if(!message.isEmpty()) {
			throw new Exception(message);
		}
		return month;
	}

}
