package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import application.ApplicationController;

public class TicketPanel extends JPanel {

    private final ApplicationController controller;

    public TicketPanel(ApplicationController controller) {
        this.controller = controller;

        setLayout(new BorderLayout());
        setBackground(new Color(245, 246, 248));

        JLabel title = new JLabel("Tickets");
        title.setFont(
                new Font("SansSerif", Font.BOLD, 28));

        title.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 28, 10, 28));

        add(title, BorderLayout.NORTH);

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(
                new Font("SansSerif", Font.PLAIN, 14));

        area.setText(
                "Ticket Management\n\n"
                + "Issue, update, search and manage railway tickets.\n\n"
                + "Ticket records will appear here when tickets are created."
        );

        area.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 30, 25, 30));

        add(
                new JScrollPane(area),
                BorderLayout.CENTER);

        controller.setStatusMessage(
                "Tickets selected");
    }
}