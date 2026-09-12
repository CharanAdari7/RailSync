package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import analytics.AnalyticsService;
import analytics.OperationalReport;
import application.ApplicationController;

public class DashboardPanel extends JPanel {

    private static final Color BACKGROUND =
            new Color(245, 246, 248);

    private static final Color CARD =
            Color.WHITE;

    private static final Color TEXT =
            new Color(35, 39, 47);

    private static final Color MUTED =
            new Color(110, 116, 126);

    private final ApplicationController controller;

    public DashboardPanel(
            ApplicationController controller) {

        if (controller == null) {
            throw new IllegalArgumentException(
                    "Application controller cannot be null.");
        }

        this.controller = controller;

        setBackground(BACKGROUND);

        setLayout(
                new BorderLayout(
                        0,
                        20
                )
        );

        setBorder(
                BorderFactory.createEmptyBorder(
                        25, 28, 25, 28
                )
        );

        build();
    }

    private void build() {

        add(
                createOverviewCards(),
                BorderLayout.NORTH
        );

        add(
                createReportArea(),
                BorderLayout.CENTER
        );
    }

    private JPanel createOverviewCards() {

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                1,
                                6,
                                14,
                                0
                        )
                );

        panel.setOpaque(false);

        AnalyticsService analytics =
                controller
                        .getAnalyticsService();

        panel.add(
                createCard(
                        "TRAINS",
                        String.valueOf(
                                analytics
                                        .getTrainAnalytics()
                                        .getTotalTrains()
                        )
                )
        );

        panel.add(
                createCard(
                        "STATIONS",
                        String.valueOf(
                                analytics
                                        .getStationAnalytics()
                                        .getTotalStations()
                        )
                )
        );

        panel.add(
                createCard(
                        "ROUTES",
                        String.valueOf(
                                controller
                                        .getDataStore()
                                        .getRouteCount()
                        )
                )
        );

        panel.add(
                createCard(
                        "PASSENGERS",
                        String.valueOf(
                                analytics
                                        .getPassengerAnalytics()
                                        .getTotalPassengers()
                        )
                )
        );

        panel.add(
                createCard(
                        "BOOKINGS",
                        String.valueOf(
                                controller
                                        .getDataStore()
                                        .getBookingCount()
                        )
                )
        );

        panel.add(
                createCard(
                        "ALERTS",
                        String.valueOf(
                                controller
                                        .getAlertService()
                                        .getActiveAlerts()
                                        .size()
                        )
                )
        );

        return panel;
    }

    private JPanel createCard(
            String title,
            String value) {

        JPanel card =
                new JPanel(
                        new BorderLayout()
                );

        card.setBackground(CARD);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(225, 227, 231)
                        ),
                        BorderFactory.createEmptyBorder(
                                17, 17, 17, 17
                        )
                )
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        10
                )
        );

        titleLabel.setForeground(MUTED);

        JLabel valueLabel =
                new JLabel(
                        value,
                        SwingConstants.LEFT
                );

        valueLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        27
                )
        );

        valueLabel.setForeground(TEXT);

        card.add(
                titleLabel,
                BorderLayout.NORTH
        );

        card.add(
                valueLabel,
                BorderLayout.CENTER
        );

        return card;
    }

    private JPanel createReportArea() {

        JPanel panel =
                new JPanel(
                        new BorderLayout()
                );

        panel.setBackground(CARD);

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(225, 227, 231)
                        ),
                        BorderFactory.createEmptyBorder(
                                20, 22, 20, 22
                        )
                )
        );

        JLabel heading =
                new JLabel(
                        "Operational Overview"
                );

        heading.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        18
                )
        );

        heading.setForeground(TEXT);

        panel.add(
                heading,
                BorderLayout.NORTH
        );

        OperationalReport report =
                controller
                        .getAnalyticsService()
                        .generateReport();

        JTextArea textArea =
                new JTextArea(
                        report.generateSummary()
                );

        textArea.setEditable(false);

        textArea.setBackground(CARD);

        textArea.setForeground(
                new Color(70, 75, 84)
        );

        textArea.setFont(
                new Font(
                        "Monospaced",
                        Font.PLAIN,
                        13
                )
        );

        textArea.setLineWrap(false);

        JScrollPane scrollPane =
                new JScrollPane(textArea);

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder(
                        18, 0, 0, 0
                )
        );

        scrollPane.setPreferredSize(
                new Dimension(0, 400)
        );

        panel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        return panel;
    }
}