package ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import application.ApplicationController;
import config.AppConfig;
import config.SystemConstants;

public class MainFrame extends JFrame {

    private final ApplicationController controller;

    private final CardLayout cardLayout;
    private final JPanel contentPanel;

    private final NavigationPanel navigationPanel;
    private final StatusPanel statusPanel;

    public MainFrame(ApplicationController controller) {

        if (controller == null) {
            throw new IllegalArgumentException(
                    "ApplicationController cannot be null.");
        }

        this.controller = controller;

        setTitle(
                AppConfig.APPLICATION_NAME
                + " "
                + AppConfig.APPLICATION_VERSION);

        setSize(
                AppConfig.WINDOW_WIDTH,
                AppConfig.WINDOW_HEIGHT);

        setMinimumSize(
                new Dimension(
                        AppConfig.MIN_WINDOW_WIDTH,
                        AppConfig.MIN_WINDOW_HEIGHT));

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                WindowConstants.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        /*
         * Main content layout
         */
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        /*
         * Navigation
         */
        navigationPanel =
                new NavigationPanel(
                        controller,
                        this::showSection);

        /*
         * Status bar
         */
        statusPanel =
                new StatusPanel(controller);

        /*
         * Header
         */
        JPanel header = createHeader();

        /*
         * Register application screens
         */
        registerPanels();

        add(
                navigationPanel,
                BorderLayout.WEST);

        JPanel center =
                new JPanel(new BorderLayout());

        center.add(
                header,
                BorderLayout.NORTH);

        center.add(
                contentPanel,
                BorderLayout.CENTER);

        center.add(
                statusPanel,
                BorderLayout.SOUTH);

        add(
                center,
                BorderLayout.CENTER);

        showSection(
                SystemConstants.DASHBOARD);
    }

    private JPanel createHeader() {

        JPanel header =
                new JPanel(
                        new BorderLayout());

        header.setBackground(
                Color.WHITE);

        header.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(
                                0, 0, 1, 0,
                                new Color(225, 227, 230)),
                        BorderFactory.createEmptyBorder(
                                22, 32, 22, 32)));

        JPanel titlePanel =
                new JPanel(
                        new BorderLayout());

        titlePanel.setOpaque(false);

        JLabel title =
                new JLabel("Railway Operations Management");

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        28));

        title.setForeground(
                new Color(24, 30, 40));

        JLabel subtitle =
                new JLabel("RailSync");

        subtitle.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14));

        subtitle.setForeground(
                new Color(105, 112, 123));

        titlePanel.add(
                title,
                BorderLayout.NORTH);

        titlePanel.add(
                subtitle,
                BorderLayout.SOUTH);

        JLabel systemStatus =
                new JLabel("●  SYSTEM READY");

        systemStatus.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12));

        systemStatus.setForeground(
                new Color(30, 125, 78));

        systemStatus.setHorizontalAlignment(
                SwingConstants.RIGHT);

        header.add(
                titlePanel,
                BorderLayout.WEST);

        header.add(
                systemStatus,
                BorderLayout.EAST);

        return header;
    }

    private void registerPanels() {

        /*
         * Dashboard
         */
        contentPanel.add(
                new DashboardPanel(controller),
                SystemConstants.DASHBOARD);

        /*
         * Railway infrastructure
         */
        contentPanel.add(
                new TrainPanel(controller),
                SystemConstants.TRAINS);

        contentPanel.add(
                new StationPanel(controller),
                SystemConstants.STATIONS);

        contentPanel.add(
                new RoutePanel(controller),
                SystemConstants.ROUTES);

        /*
         * Railway operations
         */
        contentPanel.add(
                new ServicePanel(controller),
                SystemConstants.SERVICES);

        contentPanel.add(
                new SchedulePanel(controller),
                SystemConstants.SCHEDULES);

        contentPanel.add(
                new PassengerPanel(controller),
                SystemConstants.PASSENGERS);

        contentPanel.add(
                new TicketPanel(controller),
                SystemConstants.TICKETS);

        contentPanel.add(
                new BookingPanel(controller),
                SystemConstants.BOOKINGS);

        contentPanel.add(
                new OperationPanel(controller),
                SystemConstants.OPERATIONS);

        contentPanel.add(
                new MaintenancePanel(controller),
                SystemConstants.MAINTENANCE);

        contentPanel.add(
                new AlertPanel(controller),
                SystemConstants.ALERTS);

        /*
         * Intelligence / information
         */
        contentPanel.add(
                new AnalyticsPanel(controller),
                SystemConstants.ANALYTICS);

        contentPanel.add(
                new GlobalSearchPanel(controller),
                SystemConstants.SEARCH);

        contentPanel.add(
                new AlgorithmsPanel(controller),
                SystemConstants.ALGORITHMS);

        /*
         * Application information
         */
        contentPanel.add(
                new SettingsPanel(controller),
                SystemConstants.SETTINGS);

        contentPanel.add(
                new AboutPanel(controller),
                SystemConstants.ABOUT);
    }

    public void showSection(String section) {

        if (section == null ||
                section.trim().isEmpty()) {

            return;
        }

        controller.navigateTo(section);

        cardLayout.show(
                contentPanel,
                section);

        if (statusPanel != null) {
            statusPanel.refresh();
        }

        revalidate();
        repaint();
    }

    public ApplicationController getController() {
        return controller;
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }

    public void refreshCurrentSection() {

        String current =
                controller.getCurrentSection();

        if (current != null) {
            showSection(current);
        }
    }
}