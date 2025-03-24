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
    private final int HOURS_IN_DAY = 24;
    private final int LEFT_MARGIN = 50;
    private final int RIGHT_MARGIN = 50;

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

        List<Event> events = day.getEvents();
        List<EventSlot> slots = computeEventSlots(events);

        for (EventSlot slot : slots) {
            Event event = slot.event;
            double startHour = event.getStartTime().getHour();
            double endHour = event.getEndTime().getHour();
            int startY = (int) (startHour * hourHeight);
            int endY = (int) (endHour * hourHeight);
            int eventHeight = endY - startY;
            int availableWidth = panelWidth - LEFT_MARGIN - RIGHT_MARGIN;
            int columnWidth;

            if (slot.totalColumns == 0) {
                columnWidth = availableWidth;
            } else {
                columnWidth = availableWidth / slot.totalColumns;
            }
            int rectX = LEFT_MARGIN + slot.column * columnWidth;

            g2.setColor(new Color(0, 102, 204, 180));
            g2.fillRect(rectX, startY, columnWidth, eventHeight);

            g2.setColor(Color.BLACK);
            g2.drawRect(rectX, startY, columnWidth, eventHeight);
            g2.drawString(event.getName(), rectX + 5, startY + 15);
        }
    }

    // REQUIRES: events is not null
    // EFFECTS: computes what part and how much of the panel the events will take up
    private List<EventSlot> computeEventSlots(List<Event> events) {
        List<EventSlot> slots = new ArrayList<>();
        List<EventSlot> active = new ArrayList<>();

        for (Event event : events) {
            double eventStart = event.getStartTime().getHour();
            active.removeIf(slot -> slot.event.getEndTime().getHour() <= eventStart);

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

            EventSlot newSlot = new EventSlot(event, columnIndex);
            active.add(newSlot);
            slots.add(newSlot);
        }

        for (EventSlot slot : slots) {
            int overlapCount = 0;
            double s1 = slot.event.getStartTime().getHour();
            double e1 = slot.event.getEndTime().getHour();
            for (EventSlot other : slots) {
                double s2 = other.event.getStartTime().getHour();
                double e2 = other.event.getEndTime().getHour();

                if (s1 < e2 && s2 < e1) {
                    overlapCount++;
                }
            }
            slot.totalColumns = overlapCount;
        }

        return slots;
    }

    // a custom class to keep track of how much space and event holds
    private class EventSlot {
        Event event;
        int column;
        int totalColumns;

        // REQUIRES: column >= 0
        // EFFECTS: initializes an EventSLot with a given event and column
        EventSlot(Event event, int column) {
            this.event = event;
            this.column = column;
        }
    }
}
