package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import analytics.AnalyticsService;
import application.ApplicationController;

public class AnalyticsPanel extends JPanel {

    private final ApplicationController controller;

    public AnalyticsPanel(ApplicationController controller) {
        this.controller = controller;

        setLayout(new BorderLayout());
        setBackground(new Color(245, 246, 248));

        JLabel title = new JLabel("Analytics");
        title.setFont(
                new Font("SansSerif", Font.BOLD, 28));

        title.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 28, 10, 28));

        add(title, BorderLayout.NORTH);

        JTextArea report = new JTextArea();
        report.setEditable(false);
        report.setFont(
                new Font("Monospaced", Font.PLAIN, 13));

        try {

            AnalyticsService analytics =
                    controller.getAnalyticsService();

            report.setText(
                    analytics.generateReport()
                            .generateSummary()
            );

        } catch (Exception ex) {

            report.setText(
                    "Analytics are not available yet.\n\n"
                    + "Add railway records and refresh this section."
            );
        }

        report.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 25, 20, 25));

        add(
                new JScrollPane(report),
                BorderLayout.CENTER);

        controller.setStatusMessage(
                "Analytics selected");
    }
}