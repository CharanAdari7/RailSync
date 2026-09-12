package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import application.ApplicationController;
import data.IndianRailApiClient;

public class SettingsPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private final ApplicationController controller;
    private final IndianRailApiClient apiClient;
    private JTextField apiUrlField;
    private JLabel apiStatusLabel;

    public SettingsPanel(ApplicationController controller) {
        if (controller == null) {
            throw new IllegalArgumentException(
                    "ApplicationController cannot be null.");
        }

        this.controller = controller;
        this.apiClient = new IndianRailApiClient();

        setLayout(new BorderLayout(0, 12));
        setBackground(new Color(245, 247, 250));
        setBorder(BorderFactory.createEmptyBorder(22, 28, 22, 28));

        build();
        controller.setStatusMessage("Settings selected");
    }

    private void build() {
        JLabel title = new JLabel("Settings");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        add(title, BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout(0, 18));
        content.setOpaque(false);

        JTextArea information = new JTextArea(
                "RailSync Configuration\n\n"
                + "Application Version: 1.0.0\n"
                + "Data Mode: In-Memory\n"
                + "Interface: Desktop\n\n"
                + "Paste the complete Indian Rail API HTTP URL supplied "
                + "by the provider. RailSync stores it locally on this "
                + "computer and uses it for online railway requests.\n\n"
                + "Example format:\n"
                + "http://indianrailapi.com/api/v2/StationCodeOrName/"
                + "apikey/<apikey>/SearchText/<SearchText>/");
        information.setEditable(false);
        information.setLineWrap(true);
        information.setWrapStyleWord(true);
        information.setFont(new Font("SansSerif", Font.PLAIN, 14));
        information.setBackground(new Color(245, 247, 250));
        information.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        content.add(new JScrollPane(information), BorderLayout.NORTH);

        JPanel apiPanel = new JPanel(new GridBagLayout());
        apiPanel.setBackground(Color.WHITE);
        apiPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(205, 211, 220)),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(7, 7, 7, 7);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;

        JLabel section = new JLabel("Indian Rail API");
        section.setFont(new Font("SansSerif", Font.BOLD, 18));
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        apiPanel.add(section, c);

        JLabel urlLabel = new JLabel("HTTP API URL");
        c.gridy = 1;
        c.gridwidth = 1;
        c.weightx = 0;
        apiPanel.add(urlLabel, c);

        apiUrlField = new JTextField();
        apiUrlField.setPreferredSize(new Dimension(760, 34));
        c.gridx = 1;
        c.weightx = 1;
        apiPanel.add(apiUrlField, c);

        JLabel note = new JLabel(
                "Paste the complete URL exactly as supplied. Do not add another URL inside the /apikey/ segment.");
        note.setFont(new Font("SansSerif", Font.PLAIN, 12));
        note.setForeground(new Color(100, 108, 120));
        c.gridx = 1;
        c.gridy = 2;
        apiPanel.add(note, c);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        actions.setOpaque(false);

        JButton save = new JButton("Save HTTP API");
        JButton clear = new JButton("Clear HTTP API");
        JButton test = new JButton("Test Connection");

        save.setFocusPainted(false);
        clear.setFocusPainted(false);
        test.setFocusPainted(false);

        save.addActionListener(e -> saveApiUrl());
        clear.addActionListener(e -> clearApiUrl());
        test.addActionListener(e -> testConnection());

        actions.add(save);
        actions.add(clear);
        actions.add(test);

        c.gridx = 1;
        c.gridy = 3;
        apiPanel.add(actions, c);

        apiStatusLabel = new JLabel();
        apiStatusLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        c.gridy = 4;
        apiPanel.add(apiStatusLabel, c);

        content.add(apiPanel, BorderLayout.CENTER);
        add(content, BorderLayout.CENTER);

        updateStatus();
    }

    private void saveApiUrl() {
        String url = apiUrlField.getText().trim();

        if (url.isEmpty()) {
            showWarning("Paste the Indian Rail API HTTP URL first.");
            return;
        }

        try {
            apiClient.setApiHttpUrl(url);
            apiUrlField.setText("");
            updateStatus();
            controller.setStatusMessage("Indian Rail API URL saved");

            String message;
            if (url.contains("<apikey>") || url.contains("{apikey}")) {
                message = "HTTP API URL saved locally.\n\n"
                        + "The URL still contains an API-key placeholder. "
                        + "It cannot make live requests until the provider "
                        + "supplies a credentialed URL.";
            } else {
                message = "HTTP API URL saved locally. You can now test "
                        + "the Indian Rail API connection.";
            }

            JOptionPane.showMessageDialog(
                    this,
                    message,
                    "RailSync",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void clearApiUrl() {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Remove the locally stored Indian Rail API URL?",
                "Confirm",
                JOptionPane.YES_NO_OPTION);

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        apiClient.clearApiHttpUrl();
        apiUrlField.setText("");
        updateStatus();
        controller.setStatusMessage("Indian Rail API URL cleared");
    }

    private void testConnection() {
        if (!apiClient.isConfigured()) {
            showError(
                    "A live HTTP API URL is not configured.\n\n"
                    + "If your URL contains <apikey>, it is the provider's "
                    + "documentation template rather than a credentialed URL.");
            return;
        }

        try {
            apiClient.searchStation("HJP");
            updateStatus();
            controller.setStatusMessage(
                    "Indian Rail API connection successful");
            JOptionPane.showMessageDialog(
                    this,
                    "Connection successful. The API returned a response for HJP.",
                    "RailSync",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Connection test failed.\n\n" + ex.getMessage(),
                    "RailSync",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateStatus() {
        String url = apiClient.getApiHttpUrl();

        if (apiClient.isConfigured()) {
            apiStatusLabel.setText("HTTP API: Configured");
            apiStatusLabel.setForeground(new Color(25, 125, 70));
        } else if (url != null
                && !url.trim().isEmpty()
                && (url.contains("<apikey>") || url.contains("{apikey}"))) {
            apiStatusLabel.setText(
                    "HTTP API: Template saved — credential required");
            apiStatusLabel.setForeground(new Color(170, 110, 35));
        } else {
            apiStatusLabel.setText("HTTP API: Not configured");
            apiStatusLabel.setForeground(new Color(170, 75, 50));
        }
    }

    private void showWarning(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "RailSync",
                JOptionPane.WARNING_MESSAGE);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(
                this,
                message == null ? "Operation failed." : message,
                "RailSync",
                JOptionPane.ERROR_MESSAGE);
    }
}
