package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the CalendarEventLog class
 */
public class CalendarEventLogTest {
	private CalendarEvent e1;
	private CalendarEvent e2;
	private CalendarEvent e3;
	
	@BeforeEach
	public void loadEvents() {
		e1 = new CalendarEvent("Y1");
		e2 = new CalendarEvent("Y1");
		e3 = new CalendarEvent("Y1");
		CalendarEventLog cel = CalendarEventLog.getInstance();
		cel.logEvent(e1);
		cel.logEvent(e2);
		cel.logEvent(e3);
	}
	
	@Test
	public void testLogCalendarEvent() {	
		List<CalendarEvent> l = new ArrayList<CalendarEvent>();
		
		CalendarEventLog cel = CalendarEventLog.getInstance();
		for (CalendarEvent next : cel) {
			l.add(next);
		}
		
		assertTrue(l.contains(e1));
		assertTrue(l.contains(e2));
		assertTrue(l.contains(e3));
	}

	@Test
	public void testClear() {
		CalendarEventLog cel = CalendarEventLog.getInstance();
		cel.clear();
		Iterator<CalendarEvent> itr = cel.iterator();
		assertTrue(itr.hasNext());
		assertEquals("Event log cleared.", itr.next().getDescription());
		assertFalse(itr.hasNext());
	}
}