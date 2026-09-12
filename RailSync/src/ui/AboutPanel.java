package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import application.ApplicationController;

public class AboutPanel extends JPanel {

    private final ApplicationController controller;

    public AboutPanel(
            ApplicationController controller) {

        this.controller = controller;

        setLayout(new BorderLayout());
        setBackground(
                new Color(245, 246, 248));

        JLabel title =
                new JLabel("About RailSync");

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        28));

        title.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 28, 10, 28));

        add(title, BorderLayout.NORTH);

        JTextArea area =
                new JTextArea();

        area.setEditable(false);

        area.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14));

        area.setText(
                "RAILSYNC\n"
                + "Railway Operations Management System\n\n"
                + "Version 1.0.0\n\n"
                + "RailSync provides a unified environment for "
                + "managing railway infrastructure, trains, routes, "
                + "schedules, passengers, tickets, bookings, "
                + "maintenance and operational analytics.\n\n"
                + "Current Phase\n"
                + "Desktop application with in-memory data management.\n\n"
                + "Persistence and backend integration can be added "
                + "after the desktop application is completed."
        );

        area.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 30, 25, 30));

        add(area, BorderLayout.CENTER);

        controller.setStatusMessage(
                "About selected");
    }
}