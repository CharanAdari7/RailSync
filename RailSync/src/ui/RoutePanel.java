package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
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
import javax.swing.table.DefaultTableModel;

import application.ApplicationController;
import domain.Route;
import domain.ServiceStatus;
import domain.Station;

public class RoutePanel extends JPanel {
    private static final long serialVersionUID=1L;
    private final ApplicationController controller; private JTable table; private DefaultTableModel model; private JTextField searchField;
    public RoutePanel(ApplicationController controller){if(controller==null)throw new IllegalArgumentException("ApplicationController cannot be null.");this.controller=controller;setLayout(new BorderLayout(0,12));setBackground(new Color(245,247,250));setBorder(BorderFactory.createEmptyBorder(18,22,18,22));build();refreshTable();}
    private void build(){JPanel top=new JPanel(new BorderLayout());top.setOpaque(false);JPanel title=new JPanel(new GridBagLayout());title.setOpaque(false);GridBagConstraints c=new GridBagConstraints();c.gridx=0;c.gridy=0;c.anchor=GridBagConstraints.WEST;JLabel h=new JLabel("Routes");h.setFont(new Font("SansSerif",Font.BOLD,27));title.add(h,c);c.gridy=1;c.insets=new Insets(4,0,0,0);JLabel s=new JLabel("Connect existing station codes into operational routes");s.setFont(new Font("SansSerif",Font.PLAIN,13));s.setForeground(new Color(105,112,123));title.add(s,c);top.add(title,BorderLayout.WEST);
        JPanel controls=new JPanel(new FlowLayout(FlowLayout.RIGHT,7,2));controls.setOpaque(false);searchField=new JTextField(18);searchField.setPreferredSize(new Dimension(190,34));JButton search=button("Search"),clear=button("Clear"),add=button("Add Route"),edit=button("Edit"),del=button("Delete"),refresh=button("Refresh");search.addActionListener(e->search());clear.addActionListener(e->{searchField.setText("");refreshTable();});add.addActionListener(e->showDialog(null));edit.addActionListener(e->editSelected());del.addActionListener(e->deleteSelected());refresh.addActionListener(e->refreshTable());searchField.addActionListener(e->search());controls.add(searchField);controls.add(search);controls.add(clear);controls.add(add);controls.add(edit);controls.add(del);controls.add(refresh);top.add(controls,BorderLayout.EAST);add(top,BorderLayout.NORTH);
        model=new DefaultTableModel(new Object[]{"Route","Source","Destination","Distance (km)","Stations","Status"},0){private static final long serialVersionUID=1L;public boolean isCellEditable(int r,int c){return false;}};table=new JTable(model);table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);table.setRowHeight(32);table.setFont(new Font("SansSerif",Font.PLAIN,13));table.getTableHeader().setFont(new Font("SansSerif",Font.BOLD,12));table.setShowVerticalLines(false);add(new JScrollPane(table),BorderLayout.CENTER);
    }
    private JButton button(String t){JButton b=new JButton(t);b.setFocusPainted(false);b.setFont(new Font("SansSerif",Font.BOLD,12));return b;}
    private void refreshTable(){model.setRowCount(0);List<Route> xs=controller.getRouteService().getAllRoutes();for(Route r:xs)addRow(r);controller.setStatusMessage(xs.size()+" route record(s)");}
    private void addRow(Route r){model.addRow(new Object[]{r.getRouteKey(),r.getSourceStationCode(),r.getDestinationStationCode(),r.getDistanceKm(),r.getStationCount(),r.getStatus()});}
    private void search(){String q=searchField.getText().trim();if(q.isEmpty()){refreshTable();return;}model.setRowCount(0);List<Route> xs=controller.getRouteService().search(q);for(Route r:xs)addRow(r);controller.setStatusMessage(xs.size()+" matching route record(s)");}
    private List<String> stationCodes(){List<String> codes=new ArrayList<>();for(Station s:controller.getStationService().getAllStations())codes.add(s.getStationCode());return codes;}
    private void showDialog(Route existing) {
        List<String> codes = stationCodes();
        if (codes.size() < 2) {
            showError("Add at least two stations before creating a route.");
            return;
        }
        final Route original = existing;
        JDialog dialog = new JDialog(javax.swing.SwingUtilities.getWindowAncestor(this),
                original == null ? "Add Route" : "Edit Route",
                java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(16,16,8,16));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6,6,6,6); c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1;
        JTextField name = new JTextField();
        JComboBox<String> from = new JComboBox<>(codes.toArray(new String[0]));
        JComboBox<String> to = new JComboBox<>(codes.toArray(new String[0]));
        JTextField distance = new JTextField("0");
        JTextField count = new JTextField("2");
        JComboBox<ServiceStatus> status = new JComboBox<>(ServiceStatus.values());
        if (original != null) {
            name.setText(original.getRouteName() == null ? "" : original.getRouteName());
            from.setSelectedItem(original.getSourceStationCode());
            to.setSelectedItem(original.getDestinationStationCode());
            distance.setText(String.valueOf(original.getDistanceKm()));
            count.setText(String.valueOf(original.getStationCount()));
            status.setSelectedItem(original.getStatus());
        }
        addField(form,c,"Route Name",name,0);
        addField(form,c,"Source Station",from,1);
        addField(form,c,"Destination Station",to,2);
        addField(form,c,"Distance (km)",distance,3);
        addField(form,c,"Station Count",count,4);
        addField(form,c,"Status",status,5);
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancel = button("Cancel");
        JButton save = button(original == null ? "Create" : "Save");
        cancel.addActionListener(e -> dialog.dispose());
        save.addActionListener(e -> {
            try {
                String source = String.valueOf(from.getSelectedItem());
                String destination = String.valueOf(to.getSelectedItem());
                if (source.equalsIgnoreCase(destination)) throw new IllegalArgumentException("Source and destination cannot be the same.");
                double km = Double.parseDouble(distance.getText().trim());
                int stationCount = Integer.parseInt(count.getText().trim());
                if (km < 0 || stationCount < 2) throw new IllegalArgumentException("Distance must be non-negative and station count must be at least 2.");
                if (original == null) {
                    Route route = new Route(name.getText().trim(),source,destination,km,stationCount);
                    route.setStatus((ServiceStatus) status.getSelectedItem());
                    controller.getRouteService().addRoute(route);
                } else {
                    String oldSource = original.getSourceStationCode();
                    String oldDestination = original.getDestinationStationCode();
                    if (!oldSource.equalsIgnoreCase(source) || !oldDestination.equalsIgnoreCase(destination)) {
                        controller.getRouteService().deleteRoute(oldSource,oldDestination);
                        Route route = new Route(name.getText().trim(),source,destination,km,stationCount);
                        route.setStatus((ServiceStatus) status.getSelectedItem());
                        controller.getRouteService().addRoute(route);
                    } else {
                        original.setRouteName(name.getText().trim());
                        original.setDistanceKm(km);
                        original.setStationCount(stationCount);
                        original.setStatus((ServiceStatus) status.getSelectedItem());
                        controller.getRouteService().updateRoute(original);
                    }
                }
                dialog.dispose();
                refreshTable();
            } catch (Exception ex) { showError(ex.getMessage()); }
        });
        buttons.add(cancel); buttons.add(save);
        dialog.add(form,BorderLayout.CENTER); dialog.add(buttons,BorderLayout.SOUTH);
        dialog.pack(); dialog.setMinimumSize(new Dimension(500,360)); dialog.setLocationRelativeTo(this); dialog.setVisible(true);
    }

    private void addField(JPanel p,GridBagConstraints c,String l,java.awt.Component comp,int row){c.gridx=0;c.gridy=row;c.weightx=.32;p.add(new JLabel(l),c);c.gridx=1;c.weightx=.68;if(comp instanceof JTextField)((JTextField)comp).setPreferredSize(new Dimension(260,32));p.add(comp,c);}
    private void editSelected(){int r=table.getSelectedRow();if(r<0){showError("Select a route to edit.");return;}String f=String.valueOf(model.getValueAt(table.convertRowIndexToModel(r),1)),t=String.valueOf(model.getValueAt(table.convertRowIndexToModel(r),2));Route x=controller.getRouteService().findRoute(f,t);if(x!=null)showDialog(x);}
    private void deleteSelected(){int r=table.getSelectedRow();if(r<0){showError("Select a route to delete.");return;}int mr=table.convertRowIndexToModel(r);String f=String.valueOf(model.getValueAt(mr,1)),t=String.valueOf(model.getValueAt(mr,2));if(JOptionPane.showConfirmDialog(this,"Delete route "+f+" -> "+t+"?","Confirm Delete",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){try{controller.getRouteService().deleteRoute(f,t);refreshTable();}catch(Exception ex){showError(ex.getMessage());}}}
    private void showError(String m){JOptionPane.showMessageDialog(this,m==null?"Operation failed.":m,"RailSync",JOptionPane.ERROR_MESSAGE);}
}
