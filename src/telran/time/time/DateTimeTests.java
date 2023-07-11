package telran.time.time;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.UnsupportedTemporalTypeException;
import telran.time.*;
class DateTimeTests {

	@Test
	void test() {
		
		LocalDate birthAS = LocalDate.of(1799, 6, 6);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM dd, YYYY EEEE");
		
		System.out.println(birthAS.format(dtf));
		LocalDate barMizva =  birthAS.plusYears(13);
		assertEquals(barMizva, birthAS.with(new BarMizvaAdjuster()));
		assertThrowsExactly(UnsupportedTemporalTypeException.class,
				() -> LocalTime.now().with(new BarMizvaAdjuster()));
		
	}
	@Test
	void nextFriday13Test() {
		TemporalAdjuster fr13 = new NextFriday13();
		ZonedDateTime zdt = ZonedDateTime.now();
		ZonedDateTime fr13Expected = ZonedDateTime.of(2023, 10, 13, 0, 0, 0, 0, ZoneId.systemDefault());
		assertEquals(fr13Expected, zdt.with(fr13));
		LocalDate fr13Expected2 = LocalDate.of(2024, 9, 13);
		LocalDate ld = LocalDate.of(2023, 10, 13);
		assertEquals(fr13Expected2, ld.with(fr13));
		
	}
	@Test
	void canadaCurrentTime() {
		//displayCurrentTime("Europe/London");
		//display current date & time in all time zones related to Canada
		//Date / Time (HH:mm) / Time Zone name
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY / HH:mm / zzzz");
		for(String zoneName: ZoneId.getAvailableZoneIds()) {
			if (zoneName.contains("Canada")) {
				displayCurrentTime(zoneName, dtf) ;
			}
		}
		
	}
	void displayCurrentTime(String zoneName, DateTimeFormatter dtf) {

		System.out.println(ZonedDateTime.ofInstant(Instant.now(),
				ZoneId.of(zoneName)).format(dtf));
	}

}