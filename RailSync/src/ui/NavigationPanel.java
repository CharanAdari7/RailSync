package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import application.ApplicationController;
import config.SystemConstants;

public class NavigationPanel extends JPanel {

    private static final Color BACKGROUND =
            new Color(24, 28, 36);

    private static final Color TEXT =
            new Color(235, 238, 243);

    private static final Color MUTED =
            new Color(150, 156, 168);

    private static final Color ACTIVE =
            new Color(45, 105, 175);

    private static final Color HOVER =
            new Color(39, 45, 56);

    private final ApplicationController controller;
    private final Consumer<String> navigator;

    public NavigationPanel(
            ApplicationController controller,
            Consumer<String> navigator) {

        this.controller = controller;
        this.navigator = navigator;

        setPreferredSize(
                new Dimension(235, 0)
        );

        setBackground(BACKGROUND);

        setLayout(
                new BorderLayout()
        );

        build();
    }

    private void build() {

        add(
                createBrand(),
                BorderLayout.NORTH
        );

        add(
                createNavigation(),
                BorderLayout.CENTER
        );

        add(
                createFooter(),
                BorderLayout.SOUTH
        );
    }

    private JPanel createBrand() {

        JPanel panel =
                new JPanel(
                        new BorderLayout()
                );

        panel.setBackground(BACKGROUND);

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 22, 25, 18
                )
        );

        JLabel name =
                new JLabel("RAILSYNC");

        name.setForeground(TEXT);

        name.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        23
                )
        );

        JLabel description =
                new JLabel(
                        "OPERATIONS CONTROL"
                );

        description.setForeground(MUTED);

        description.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        9
                )
        );

        JPanel text =
                new JPanel(
                        new BorderLayout()
                );

        text.setOpaque(false);

        text.add(
                name,
                BorderLayout.NORTH
        );

        text.add(
                description,
                BorderLayout.SOUTH
        );

        panel.add(
                text,
                BorderLayout.CENTER
        );

        return panel;
    }

    private JPanel createNavigation() {

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                0,
                                1,
                                0,
                                3
                        )
                );

        panel.setBackground(BACKGROUND);

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        5, 10, 5, 10
                )
        );

        addButton(
                panel,
                "Dashboard",
                SystemConstants.DASHBOARD
        );

        addButton(
                panel,
                "Trains",
                SystemConstants.TRAINS
        );

        addButton(
                panel,
                "Stations",
                SystemConstants.STATIONS
        );

        addButton(
                panel,
                "Routes",
                SystemConstants.ROUTES
        );

        addButton(
                panel,
                "Services",
                SystemConstants.SERVICES
        );

        addButton(
                panel,
                "Schedules",
                SystemConstants.SCHEDULES
        );

        addButton(
                panel,
                "Passengers",
                SystemConstants.PASSENGERS
        );

        addButton(
                panel,
                "Tickets",
                SystemConstants.TICKETS
        );

        addButton(
                panel,
                "Bookings",
                SystemConstants.BOOKINGS
        );

        addButton(
                panel,
                "Operations",
                SystemConstants.OPERATIONS
        );

        addButton(
                panel,
                "Maintenance",
                SystemConstants.MAINTENANCE
        );

        addButton(
                panel,
                "Alerts",
                SystemConstants.ALERTS
        );

        addButton(
                panel,
                "Analytics",
                SystemConstants.ANALYTICS
        );

        addButton(
                panel,
                "Search",
                SystemConstants.SEARCH
        );

        addButton(
                panel,
                "Algorithms",
                SystemConstants.ALGORITHMS
        );

        addButton(
                panel,
                "Settings",
                SystemConstants.SETTINGS
        );

        addButton(
                panel,
                "About",
                SystemConstants.ABOUT
        );

        return panel;
    }

    private void addButton(
            JPanel parent,
            String text,
            String section) {

        JButton button =
                new JButton(text);

        button.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        button.setForeground(TEXT);

        button.setBackground(BACKGROUND);

        button.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        11, 15, 11, 10
                )
        );

        button.setFocusPainted(false);

        button.setContentAreaFilled(true);

        button.setOpaque(true);

        button.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setMargin(
                new Insets(0, 0, 0, 0)
        );

        button.addActionListener(
                event -> navigator.accept(section)
        );

        button.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            java.awt.event.MouseEvent event) {

                        button.setBackground(HOVER);
                    }

                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent event) {

                        if (!section.equals(
                                controller.getCurrentSection())) {

                            button.setBackground(BACKGROUND);
                        }
                    }
                }
        );

        parent.add(button);
    }

    private JPanel createFooter() {

        JPanel panel =
                new JPanel(
                        new BorderLayout()
                );

        panel.setBackground(BACKGROUND);

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(
                                1, 0, 0, 0,
                                new Color(55, 60, 70)
                        ),
                        BorderFactory.createEmptyBorder(
                                15, 18, 18, 18
                        )
                )
        );

        JLabel version =
                new JLabel(
                        "RailSync 1.0.0"
                );

        version.setForeground(MUTED);

        version.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        11
                )
        );

        panel.add(
                version,
                BorderLayout.WEST
        );

        return panel;
    }
}