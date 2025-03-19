package ui;

import model.Calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Represents application's main window frame.
 */
class CalendarUI extends JFrame {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 500;
    private JDesktopPane desktop;
    private JPanel topBar;
    private JFrame window;
    private Calendar calendar;
    private JLabel titleLabel;
    private CalendarPanel calendarPanel;
    private TitlePanel titlePanel;
    private int calendarIndex;

    /**
     * Constructor sets up button panel, key pad and visual alarm status window.
     * 
     * @throws IOException
     *                     TODO
     */
    public CalendarUI(Calendar calendar) throws IOException {
        this.calendar = calendar;
        desktop = new JDesktopPane();
        desktop.addMouseListener(new DesktopFocusAction());

        window = new JFrame("Personal Calendar");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(WIDTH, HEIGHT);
        window.setResizable(false);

        setContentPane(desktop);
        setTitle("Personal Calendar");
        setSize(WIDTH, HEIGHT);
        File sourceimage = new File("lib\\calendaricon.png");
        Image icon = ImageIO.read(sourceimage);
        setIconImage(icon);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        centreOnScreen();

        titlePanel = new TitlePanel(calendar, calendar.getCurrentYear().getYearNumber());

        addMenu();
        window.add(titlePanel);
        //window.add(calendarPanel);
        //window.add(categoryPanel);

        window.setVisible(true);
        desktop.setVisible(true);

    }

    private void addMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');
        addMenuItem(fileMenu, new AddSaveAction(), null);
        addMenuItem(fileMenu, new AddLoadAction(), null);
        addMenuItem(fileMenu, new AddExitAction(), null);
        menuBar.add(fileMenu);

        setJMenuBar(menuBar);
    }

    private void updateTitle() {
        titleLabel.setText(calendar.getTitle());
    }

    private void makeButtonTransparent(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setForeground(Color.BLACK);
    }

    /**
     * Action to decrement the year
     */
    private class DecrementYearAction extends AbstractAction {
        DecrementYearAction() {
            super("←");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            calendar.decrementYearIndex();
            updateTitle();
        }
    }

    /**
     * Action to increment the year
     */
    private class IncrementYearAction extends AbstractAction {
        IncrementYearAction() {
            super("→");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            calendar.incrementYearIndex();
            updateTitle();
        }
    }

    /*
     * private void addCalendarDisplay() {
     * JPanel buttonPanel = new JPanel();
     * buttonPanel.setLayout(new GridLayout(4,2));
     * buttonPanel.add(new JButton(new YearAction()));
     * buttonPanel.add(createPrintCombo());
     * 
     * controlPanel.add(buttonPanel, BorderLayout.WEST);
     * }
     */

    /**
     * Adds an item with given handler to the given menu
     * 
     * @param theMenu     menu to which new item is added
     * @param action      handler for new menu item
     * @param accelerator keystroke accelerator for this menu item
     */
    private void addMenuItem(JMenu theMenu, AbstractAction action, KeyStroke accelerator) {
        JMenuItem menuItem = new JMenuItem(action);
        menuItem.setMnemonic(menuItem.getText().charAt(0));
        menuItem.setAccelerator(accelerator);
        theMenu.add(menuItem);
    }

    /**
     * Represents action to be taken when user wants to add a new code
     * to the system.
     */
    private class AddSaveAction extends AbstractAction {

        AddSaveAction() {
            super("Save Code");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {

        }
    }

    /**
     * Represents action to be taken when user wants to add a new code
     * to the system.
     */
    private class AddLoadAction extends AbstractAction {

        AddLoadAction() {
            super("Load Code");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {

        }
    }

    /**
     * Represents action to be taken when user wants to add a new code
     * to the system.
     */
    private class AddExitAction extends AbstractAction {

        AddExitAction() {
            super("Exit Code");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {

        }
    }

    // TODO
    private class DesktopFocusAction extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            CalendarUI.this.requestFocusInWindow();
        }
    }

    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }

}
