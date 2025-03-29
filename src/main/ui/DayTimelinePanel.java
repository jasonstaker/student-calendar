package ui;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import model.*;
import model.Event;

// a DayTimelinePanel which displays all the events in a chronological format for a given day
public class DayTimelinePanel extends JPanel {

    // fields
    private Day day;
    private static final int HOURS_IN_DAY = 24;
    private static final int LEFT_MARGIN = 50;
    private static final int RIGHT_MARGIN = 50;
    private static final int MIN_EVENT_HEIGHT = 7;

    // REQUIRES: day is a valid day in the calendar
    // EFFECTS: initializes the day timeline panel with day
    public DayTimelinePanel(Day day) {
        this.day = day;
        setPreferredSize(new Dimension(200, 300));
    }

    // MODIFIES: this
    // EFFECTS: responsible for painting the entire timeline panel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int hourHeight = panelHeight / HOURS_IN_DAY;

        g2.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i <= HOURS_IN_DAY; i++) {
            int y = i * hourHeight;
            g2.drawLine(0, y, panelWidth, y);
            g2.setColor(Color.BLACK);
            g2.drawString(i + ":00", 5, y + 12);
            g2.setColor(Color.LIGHT_GRAY);
        }

        paintEvents(g2, panelWidth, panelHeight, hourHeight);
    }

    // MODIFIES: g2
    // EFFECTS: paints all events from the day onto the timeline panel, accounting
    // for overlapping events
    public void paintEvents(Graphics2D g2, int panelWidth, int panelHeight, int hourHeight) {
        List<Event> events = day.getEvents();
        List<EventSlot> slots = computeEventSlots(events, hourHeight);

        for (EventSlot slot : slots) {
            Event event = slot.event;
            double actualStart = event.getStartTime().getHour()
                    + event.getStartTime().getMinute() / 60.0;
            double actualEnd = event.getEndTime().getHour()
                    + event.getEndTime().getMinute() / 60.0;
            int startY = (int) (actualStart * hourHeight);
            int endY = (int) (actualEnd * hourHeight);
            int eventHeight = endY - startY;
            if (eventHeight < MIN_EVENT_HEIGHT) {
                eventHeight = MIN_EVENT_HEIGHT;
            }
            int availableWidth = panelWidth - LEFT_MARGIN - RIGHT_MARGIN;
            int columnWidth = (slot.totalColumns > 0) ? availableWidth / slot.totalColumns : availableWidth;
            int rectX = LEFT_MARGIN + slot.column * columnWidth;

            g2.setColor(new Color(0, 102, 204, 180));
            g2.fillRect(rectX, startY, columnWidth, eventHeight);

            g2.setColor(Color.BLACK);
            g2.drawRect(rectX, startY, columnWidth, eventHeight);
            g2.drawString(event.getName(), rectX + 5, startY + 15);
        }
    }

    // EFFECTS: computes the slots for events so that overlapping events are
    // displayed side-by-side.
    private List<EventSlot> computeEventSlots(List<Event> events, int hourHeight) {
        List<EventSlot> slots = new ArrayList<>();
        List<EventSlot> active = new ArrayList<>();

        for (Event event : events) {
            double actualStart = event.getStartTime().getHour() + event.getStartTime().getMinute() / 60.0;
            double actualEnd = event.getEndTime().getHour() + event.getEndTime().getMinute() / 60.0;
            double minDuration = (double) MIN_EVENT_HEIGHT / hourHeight;
            double effectiveEnd = (actualEnd - actualStart < minDuration) ? actualStart + minDuration : actualEnd;

            active.removeIf(slot -> slot.effectiveEnd <= actualStart);

            boolean[] used = new boolean[active.size() + 1];
            for (EventSlot slot : active) {
                if (slot.column < used.length) {
                    used[slot.column] = true;
                }
            }
            int columnIndex = 0;
            while (columnIndex < used.length && used[columnIndex]) {
                columnIndex++;
            }

            EventSlot newSlot = new EventSlot(event, columnIndex, actualStart, effectiveEnd);
            addSlot(active, slots, newSlot);
        }

        determineOverlaps(slots);

        return slots;
    }
    
    // MODIFIES: active, slots
    // EFFECTS: adds the newSlot to active and slots
    public void addSlot(List<EventSlot> active, List<EventSlot> slots, EventSlot newSlot) {
        active.add(newSlot);
        slots.add(newSlot);
    }

    // MODIFIES: slots
    // EFFECTS: determines the number of overlapping events and sets totalColumns
    // for each slot accordingly
    public void determineOverlaps(List<EventSlot> slots) {
        for (EventSlot slot : slots) {
            int overlapCount = 0;
            double s1 = slot.effectiveStart;
            double e1 = slot.effectiveEnd;
            for (EventSlot other : slots) {
                double s2 = other.effectiveStart;
                double e2 = other.effectiveEnd;
                if (s1 < e2 && s2 < e1) {
                    overlapCount++;
                }
            }
            slot.totalColumns = overlapCount;
        }
    }

    // a custom class to keep track of how much space an event holds and its
    // position (column) in the timeline
    private class EventSlot {
        Event event;
        int column;
        int totalColumns;
        double effectiveStart;
        double effectiveEnd;

        // REQUIRES: column >= 0
        // EFFECTS: initializes an EventSlot with a given event, its column index, and
        // effective start/end times
        EventSlot(Event event, int column, double effectiveStart, double effectiveEnd) {
            this.event = event;
            this.column = column;
            this.effectiveStart = effectiveStart;
            this.effectiveEnd = effectiveEnd;
        }
    }
}
