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
public class EventLogTest {
	private CalendarEvent e1;
	private CalendarEvent e2;
	private CalendarEvent e3;
	
	@BeforeEach
	public void loadEvents() {
		e1 = new CalendarEvent("A1");
		e2 = new CalendarEvent("A2");
		e3 = new CalendarEvent("A3");
		CalendarEventLog el = CalendarEventLog.getInstance();
		el.logEvent(e1);
		el.logEvent(e2);
		el.logEvent(e3);
	}
	
	@Test
	public void testLogEvent() {	
		List<CalendarEvent> l = new ArrayList<CalendarEvent>();
		
		CalendarEventLog el = CalendarEventLog.getInstance();
		for (CalendarEvent next : el) {
			l.add(next);
		}
		
		assertTrue(l.contains(e1));
		assertTrue(l.contains(e2));
		assertTrue(l.contains(e3));
	}

	@Test
	public void testClear() {
		CalendarEventLog el = CalendarEventLog.getInstance();
		el.clear();
		Iterator<CalendarEvent> itr = el.iterator();
		assertTrue(itr.hasNext());   // After log is cleared, the clear log event is added
		assertEquals("Event log cleared.", itr.next().getDescription());
		assertFalse(itr.hasNext());
	}
}