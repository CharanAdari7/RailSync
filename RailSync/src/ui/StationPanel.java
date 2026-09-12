package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import application.ApplicationController;
import domain.ServiceStatus;
import domain.Station;
import domain.StationSearchResult;

public class StationPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private final ApplicationController controller;
    private JTable table;
    private DefaultTableModel model;
    private JTextField searchField;

    public StationPanel(ApplicationController controller) {
        if (controller == null) {
            throw new IllegalArgumentException("ApplicationController cannot be null.");
        }
        this.controller = controller;
        setLayout(new BorderLayout(0, 12));
        setBackground(new Color(245, 247, 250));
        setBorder(BorderFactory.createEmptyBorder(18, 22, 18, 22));
        build();
        refreshTable();
    }

    private void build() {
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);

        JPanel title = new JPanel(new GridBagLayout());
        title.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;

        JLabel heading = new JLabel("Stations");
        heading.setFont(new Font("SansSerif", Font.BOLD, 27));
        title.add(heading, c);

        c.gridy = 1;
        c.insets = new Insets(4, 0, 0, 0);
        JLabel subtitle = new JLabel(
                "Manage stations stored in RailSync or import from the public station master");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitle.setForeground(new Color(105, 112, 123));
        title.add(subtitle, c);
        top.add(title, BorderLayout.WEST);

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 7, 2));
        controls.setOpaque(false);

        searchField = new JTextField(17);
        searchField.setPreferredSize(new Dimension(180, 34));

        JButton search = button("Search Stored");
        JButton clear = button("Clear");
        JButton catalog = button("Station Catalog");
        JButton online = button("Search Online");
        JButton add = button("Add Station");
        JButton edit = button("Edit");
        JButton del = button("Delete");
        JButton refresh = button("Refresh");

        search.addActionListener(e -> searchLocal());
        clear.addActionListener(e -> {
            searchField.setText("");
            refreshTable();
        });
        catalog.addActionListener(e -> searchPublicCatalog());
        online.addActionListener(e -> searchOnline());
        add.addActionListener(e -> showDialog(null));
        edit.addActionListener(e -> editSelected());
        del.addActionListener(e -> deleteSelected());
        refresh.addActionListener(e -> refreshTable());
        searchField.addActionListener(e -> searchLocal());

        controls.add(searchField);
        controls.add(search);
        controls.add(clear);
        controls.add(catalog);
        controls.add(online);
        controls.add(add);
        controls.add(edit);
        controls.add(del);
        controls.add(refresh);
        top.add(controls, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        model = new DefaultTableModel(
                new Object[]{"Code", "Station Name", "City", "State",
                        "Latitude", "Longitude", "Platforms", "Status"}, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(32);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        table.setShowVerticalLines(false);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private JButton button(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        return button;
    }

    private void refreshTable() {
        model.setRowCount(0);
        List<Station> stations = controller.getStationService().getAllStations();
        for (Station station : stations) {
            addRow(station);
        }
        controller.setStatusMessage(stations.size() + " station record(s)");
    }

    private void addRow(Station station) {
        model.addRow(new Object[]{
                station.getStationCode(),
                station.getStationName(),
                station.getCity(),
                station.getState(),
                station.getLatitude(),
                station.getLongitude(),
                station.getPlatformCount(),
                station.getStatus()
        });
    }

    private void searchLocal() {
        String query = searchField.getText().trim();
        if (query.isEmpty()) {
            refreshTable();
            return;
        }

        model.setRowCount(0);
        List<Station> stations = controller.getStationService().search(query);
        for (Station station : stations) {
            addRow(station);
        }
        controller.setStatusMessage(stations.size() + " matching stored station record(s)");
    }

    private void searchPublicCatalog() {
        String query = searchField.getText().trim();
        if (query.isEmpty()) {
            showError("Enter a station name or station code first.");
            return;
        }

        setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.WAIT_CURSOR));

        new SwingWorker<List<StationSearchResult>, Void>() {
            @Override
            protected List<StationSearchResult> doInBackground() throws Exception {
                return controller.getStationService().searchPublicCatalog(query);
            }

            @Override
            protected void done() {
                setCursor(java.awt.Cursor.getDefaultCursor());
                try {
                    showCatalogResults(get());
                } catch (Exception ex) {
                    Throwable cause = ex.getCause() == null ? ex : ex.getCause();
                    showError("Station catalog search failed.\n\n" + cause.getMessage());
                }
            }
        }.execute();
    }

    private void showCatalogResults(List<StationSearchResult> results) {
        if (results == null || results.isEmpty()) {
            showError("No matching stations were found in the public station master.");
            return;
        }

        JDialog dialog = new JDialog(
                javax.swing.SwingUtilities.getWindowAncestor(this),
                "Station Catalog",
                java.awt.Dialog.ModalityType.APPLICATION_MODAL);

        DefaultTableModel resultModel = new DefaultTableModel(
                new Object[]{"Code", "Station Name", "City"}, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable resultTable = new JTable(resultModel);
        resultTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        resultTable.setRowHeight(30);

        for (StationSearchResult result : results) {
            resultModel.addRow(new Object[]{
                    result.getStationCode(),
                    result.getStationName(),
                    result.getCity()
            });
        }

        JButton importButton = button("Import Selected");
        JButton closeButton = button("Close");

        importButton.addActionListener(e -> {
            int row = resultTable.getSelectedRow();
            if (row < 0) {
                showError("Select a station first.");
                return;
            }

            try {
                StationSearchResult result =
                        results.get(resultTable.convertRowIndexToModel(row));
                Station imported =
                        controller.getStationService().importPublicCatalogStation(result);

                dialog.dispose();
                refreshTable();

                JOptionPane.showMessageDialog(
                        this,
                        "Station " + imported.getStationCode()
                                + " is now available in RailSync.",
                        "Station Imported",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                showError(ex.getMessage());
            }
        });

        closeButton.addActionListener(e -> dialog.dispose());

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(closeButton);
        buttons.add(importButton);

        dialog.add(new JScrollPane(resultTable), BorderLayout.CENTER);
        dialog.add(buttons, BorderLayout.SOUTH);
        dialog.setSize(720, 430);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showDialog(Station existing) {
        JDialog dialog = new JDialog(
                javax.swing.SwingUtilities.getWindowAncestor(this),
                existing == null ? "Add Station" : "Edit Station",
                java.awt.Dialog.ModalityType.APPLICATION_MODAL);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(16, 16, 8, 16));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;

        JTextField code = new JTextField();
        JTextField name = new JTextField();
        JTextField city = new JTextField();
        JTextField state = new JTextField();
        JTextField latitude = new JTextField("0");
        JTextField longitude = new JTextField("0");
        JTextField platforms = new JTextField("0");
        JComboBox<ServiceStatus> status = new JComboBox<>(ServiceStatus.values());

        if (existing != null) {
            code.setText(existing.getStationCode());
            name.setText(existing.getStationName());
            city.setText(existing.getCity());
            state.setText(existing.getState());
            latitude.setText(String.valueOf(existing.getLatitude()));
            longitude.setText(String.valueOf(existing.getLongitude()));
            platforms.setText(String.valueOf(existing.getPlatformCount()));
            status.setSelectedItem(existing.getStatus());
            code.setEnabled(false);
        }

        addField(form, c, "Station Code", code, 0);
        addField(form, c, "Station Name", name, 1);
        addField(form, c, "City", city, 2);
        addField(form, c, "State", state, 3);
        addField(form, c, "Latitude", latitude, 4);
        addField(form, c, "Longitude", longitude, 5);
        addField(form, c, "Platforms", platforms, 6);
        addField(form, c, "Status", status, 7);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancel = button("Cancel");
        JButton save = button(existing == null ? "Create" : "Save");

        cancel.addActionListener(e -> dialog.dispose());
        save.addActionListener(e -> {
            try {
                String stationCode = code.getText().trim();
                String stationName = name.getText().trim();

                if (stationCode.isEmpty() || stationName.isEmpty()) {
                    throw new IllegalArgumentException(
                            "Station code and station name are required.");
                }

                double lat = Double.parseDouble(latitude.getText().trim());
                double lon = Double.parseDouble(longitude.getText().trim());
                int platformCount = Integer.parseInt(platforms.getText().trim());

                if (existing == null) {
                    Station station = new Station(
                            stationCode,
                            stationName,
                            city.getText().trim(),
                            state.getText().trim(),
                            lat,
                            lon,
                            platformCount);
                    station.setStatus((ServiceStatus) status.getSelectedItem());
                    controller.getStationService().addStation(station);
                } else {
                    existing.setStationName(stationName);
                    existing.setCity(city.getText());
                    existing.setState(state.getText());
                    existing.setLatitude(lat);
                    existing.setLongitude(lon);
                    existing.setPlatformCount(platformCount);
                    existing.setStatus((ServiceStatus) status.getSelectedItem());
                    controller.getStationService().updateStation(existing);
                }

                dialog.dispose();
                refreshTable();
            } catch (Exception ex) {
                showError(ex.getMessage());
            }
        });

        buttons.add(cancel);
        buttons.add(save);
        dialog.add(form, BorderLayout.CENTER);
        dialog.add(buttons, BorderLayout.SOUTH);
        dialog.pack();
        dialog.setMinimumSize(new Dimension(520, 520));
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void addField(
            JPanel panel,
            GridBagConstraints c,
            String label,
            java.awt.Component component,
            int row) {
        c.gridx = 0;
        c.gridy = row;
        c.weightx = .32;
        panel.add(new JLabel(label), c);
        c.gridx = 1;
        c.weightx = .68;
        if (component instanceof JTextField) {
            ((JTextField) component).setPreferredSize(new Dimension(270, 32));
        }
        panel.add(component, c);
    }

    private void editSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            showError("Select a station to edit.");
            return;
        }

        String code = String.valueOf(
                model.getValueAt(table.convertRowIndexToModel(row), 0));
        Station station = controller.getStationService().findByCode(code);
        if (station != null) {
            showDialog(station);
        }
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            showError("Select a station to delete.");
            return;
        }

        String code = String.valueOf(
                model.getValueAt(table.convertRowIndexToModel(row), 0));

        if (JOptionPane.showConfirmDialog(
                this,
                "Delete station " + code + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                controller.getStationService().deleteStation(code);
                refreshTable();
            } catch (Exception ex) {
                showError(ex.getMessage());
            }
        }
    }

    private void searchOnline() {
        String query = searchField.getText().trim();
        if (query.isEmpty()) {
            showError("Enter a station name or station code first.");
            return;
        }

        try {
            List<StationSearchResult> results =
                    controller.getStationService().searchOnline(query);
            if (results.isEmpty()) {
                showError("No stations were returned by the configured railway API.");
                return;
            }
            showOnlineResults(results);
        } catch (Exception ex) {
            showError("Online station search failed.\n\n" + ex.getMessage());
        }
    }

    private void showOnlineResults(List<StationSearchResult> results) {
        JDialog dialog = new JDialog(
                javax.swing.SwingUtilities.getWindowAncestor(this),
                "Online Station Results",
                java.awt.Dialog.ModalityType.APPLICATION_MODAL);

        DefaultTableModel resultModel = new DefaultTableModel(
                new Object[]{"Code", "Station Name", "Latitude", "Longitude"}, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable resultTable = new JTable(resultModel);
        for (StationSearchResult result : results) {
            resultModel.addRow(new Object[]{
                    result.getStationCode(),
                    result.getStationName(),
                    result.getLatitude(),
                    result.getLongitude()
            });
        }
        resultTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JButton importButton = button("Import Selected");
        JButton close = button("Close");

        importButton.addActionListener(e -> {
            int row = resultTable.getSelectedRow();
            if (row < 0) {
                showError("Select an online station first.");
                return;
            }
            try {
                StationSearchResult result =
                        results.get(resultTable.convertRowIndexToModel(row));
                Station imported =
                        controller.getStationService().importOnlineStation(result);
                dialog.dispose();
                refreshTable();
                JOptionPane.showMessageDialog(
                        this,
                        "Station " + imported.getStationCode()
                                + " is now available in RailSync.",
                        "Station Imported",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                showError(ex.getMessage());
            }
        });

        close.addActionListener(e -> dialog.dispose());

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(close);
        buttons.add(importButton);
        dialog.add(new JScrollPane(resultTable), BorderLayout.CENTER);
        dialog.add(buttons, BorderLayout.SOUTH);
        dialog.setSize(650, 380);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(
                this,
                message == null ? "Operation failed." : message,
                "RailSync",
                JOptionPane.ERROR_MESSAGE);
    }
}
