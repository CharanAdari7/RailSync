package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import application.ApplicationController;

public class GlobalSearchPanel extends JPanel {

    private final ApplicationController controller;

    private JTextField searchField;
    private JTextArea results;

    public GlobalSearchPanel(
            ApplicationController controller) {

        this.controller = controller;

        setLayout(new BorderLayout());
        setBackground(new Color(245, 246, 248));

        JLabel title =
                new JLabel("Global Search");

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        28));

        title.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 28, 10, 28));

        add(title, BorderLayout.NORTH);

        JPanel top =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT));

        top.setBackground(Color.WHITE);

        searchField =
                new JTextField(30);

        JButton search =
                new JButton("Search");

        JButton clear =
                new JButton("Clear");

        search.addActionListener(
                e -> performSearch());

        clear.addActionListener(e -> {
            searchField.setText("");
            results.setText("");
        });

        top.add(searchField);
        top.add(search);
        top.add(clear);

        results =
                new JTextArea();

        results.setEditable(false);
        results.setFont(
                new Font(
                        "Monospaced",
                        Font.PLAIN,
                        13));

        results.setText(
                "Enter a search term to search railway records."
        );

        JPanel center =
                new JPanel(new BorderLayout());

        center.setBackground(
                Color.WHITE);

        center.add(
                top,
                BorderLayout.NORTH);

        center.add(
                new JScrollPane(results),
                BorderLayout.CENTER);

        center.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 28, 28, 28));

        add(center, BorderLayout.CENTER);

        controller.setStatusMessage(
                "Search selected");
    }

    private void performSearch() {

        String query =
                searchField.getText()
                        .trim();

        if (query.isEmpty()) {

            results.setText(
                    "Enter a search term.");
            return;
        }

        /*
         * Detailed cross-module search will be connected
         * after all domain modules are active.
         */

        results.setText(
                "Search request: "
                + query
                + "\n\n"
                + "Cross-module search will inspect "
                + "the records currently stored in RailSync."
        );

        controller.setStatusMessage(
                "Search performed: " + query);
    }
}