package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import application.ApplicationController;

public class StatusPanel extends JPanel {

    private final ApplicationController controller;

    private final JLabel statusLabel;
    private final JLabel recordLabel;

    public StatusPanel(
            ApplicationController controller) {

        if (controller == null) {
            throw new IllegalArgumentException(
                    "Application controller cannot be null.");
        }

        this.controller = controller;

        statusLabel = new JLabel();
        recordLabel = new JLabel();

        setPreferredSize(
                new Dimension(0, 38)
        );

        setBackground(Color.WHITE);

        setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(
                                1, 0, 0, 0,
                                new Color(225, 227, 231)
                        ),
                        BorderFactory.createEmptyBorder(
                                0, 18, 0, 18
                        )
                )
        );

        setLayout(
                new BorderLayout()
        );

        statusLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        12
                )
        );

        statusLabel.setForeground(
                new Color(90, 95, 104)
        );

        recordLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        12
                )
        );

        recordLabel.setForeground(
                new Color(110, 116, 126)
        );

        add(
                statusLabel,
                BorderLayout.WEST
        );

        add(
                recordLabel,
                BorderLayout.EAST
        );

        refresh();
    }

    public void refresh() {

        statusLabel.setText(
                controller.getStatusMessage()
        );

        recordLabel.setText(
                controller.getDataStore()
                        .getTotalRecordCount()
                + " records"
        );
    }
}