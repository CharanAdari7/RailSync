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
import javax.swing.table.DefaultTableModel;

import application.ApplicationController;
import domain.ServiceStatus;
import domain.Station;
import domain.Train;

public class TrainPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private final ApplicationController controller;
    private JTable table;
    private DefaultTableModel model;
    private JTextField searchField;

    public TrainPanel(ApplicationController controller) {
        if (controller == null) throw new IllegalArgumentException("ApplicationController cannot be null.");
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
        c.gridx = 0; c.gridy = 0; c.anchor = GridBagConstraints.WEST;
        JLabel h = new JLabel("Trains"); h.setFont(new Font("SansSerif", Font.BOLD, 27));
        h.setForeground(new Color(30, 35, 42)); title.add(h, c);
        c.gridy = 1; c.insets = new Insets(4, 0, 0, 0);
        JLabel sub = new JLabel("Manage trains using train number and real station codes");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 13)); sub.setForeground(new Color(105,112,123)); title.add(sub,c);
        top.add(title, BorderLayout.WEST);

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 7, 2)); controls.setOpaque(false);
        searchField = new JTextField(18); searchField.setPreferredSize(new Dimension(190,34));
        JButton search = button("Search"), clear = button("Clear"), add = button("Add Train"), edit = button("Edit"), del = button("Delete"), refresh = button("Refresh");
        search.addActionListener(e -> search()); clear.addActionListener(e -> {searchField.setText(""); refreshTable();});
        add.addActionListener(e -> showDialog(null)); edit.addActionListener(e -> editSelected()); del.addActionListener(e -> deleteSelected()); refresh.addActionListener(e -> refreshTable());
        searchField.addActionListener(e -> search());
        controls.add(searchField); controls.add(search); controls.add(clear); controls.add(add); controls.add(edit); controls.add(del); controls.add(refresh);
        top.add(controls, BorderLayout.EAST); add(top, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{"Train Number","Train Name","From","To","Type","Status"},0) {
            private static final long serialVersionUID = 1L;
            public boolean isCellEditable(int r,int c){return false;}
        };
        table = new JTable(model); table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); table.setRowHeight(32);
        table.setFont(new Font("SansSerif",Font.PLAIN,13)); table.getTableHeader().setFont(new Font("SansSerif",Font.BOLD,12));
        table.setShowVerticalLines(false); table.setGridColor(new Color(225,229,234));
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private JButton button(String text) { JButton b=new JButton(text); b.setFocusPainted(false); b.setFont(new Font("SansSerif",Font.BOLD,12)); return b; }

    private void refreshTable() {
        model.setRowCount(0);
        List<Train> trains=controller.getTrainService().getAllTrains();
        for(Train t:trains) addRow(t);
        controller.setStatusMessage(trains.size()+" train record(s)");
    }
    private void addRow(Train t) {
        model.addRow(new Object[]{t.getTrainNumber(),t.getTrainName(),t.getSourceStationCode(),t.getDestinationStationCode(),t.getTrainType(),t.getStatus()});
    }
    private void search() {
        String q=searchField.getText().trim().toLowerCase();
        if(q.isEmpty()){refreshTable();return;}
        model.setRowCount(0); int count=0;
        for(Train t:controller.getTrainService().search(q)){addRow(t);count++;}
        controller.setStatusMessage(count+" matching train record(s)");
    }

    private void showDialog(Train existing) {
        JDialog d=new JDialog(javax.swing.SwingUtilities.getWindowAncestor(this), existing==null?"Add Train":"Edit Train", java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        JPanel form=new JPanel(new GridBagLayout()); form.setBorder(BorderFactory.createEmptyBorder(16,16,8,16));
        GridBagConstraints c=new GridBagConstraints(); c.insets=new Insets(6,6,6,6); c.fill=GridBagConstraints.HORIZONTAL; c.weightx=1;
        JTextField number=new JTextField(), name=new JTextField(), type=new JTextField();
        JTextField from=new JTextField(), to=new JTextField();
        JComboBox<ServiceStatus> status=new JComboBox<>(ServiceStatus.values());
        if(existing!=null){number.setText(existing.getTrainNumber()); name.setText(existing.getTrainName()); type.setText(existing.getTrainType()); from.setText(existing.getSourceStationCode()); to.setText(existing.getDestinationStationCode()); status.setSelectedItem(existing.getStatus()); number.setEnabled(false);}
        addField(form,c,"Train Number",number,0); addField(form,c,"Train Name",name,1); addField(form,c,"Source Station Code",from,2); addField(form,c,"Destination Station Code",to,3); addField(form,c,"Train Type",type,4); addField(form,c,"Status",status,5);
        JPanel buttons=new JPanel(new FlowLayout(FlowLayout.RIGHT)); JButton cancel=button("Cancel"), save=button(existing==null?"Create":"Save");
        cancel.addActionListener(e->d.dispose()); save.addActionListener(e->{
            try{
                String n=number.getText().trim(), nm=name.getText().trim(), f=from.getText().trim(), t=to.getText().trim(), ty=type.getText().trim();
                if(n.isEmpty()||nm.isEmpty()||f.isEmpty()||t.isEmpty()||ty.isEmpty()) throw new IllegalArgumentException("All train fields are required.");
                if(existing==null){Train x=new Train(n,nm,f,t,ty); x.setStatus((ServiceStatus)status.getSelectedItem()); controller.getTrainService().addTrain(x);}
                else{existing.setTrainName(nm); existing.setSourceStationCode(f); existing.setDestinationStationCode(t); existing.setTrainType(ty); existing.setStatus((ServiceStatus)status.getSelectedItem()); controller.getTrainService().updateTrain(existing);}
                d.dispose(); refreshTable();
            }catch(Exception ex){showError(ex.getMessage());}
        }); buttons.add(cancel); buttons.add(save); d.add(form,BorderLayout.CENTER); d.add(buttons,BorderLayout.SOUTH); d.pack(); d.setMinimumSize(new Dimension(500,390)); d.setLocationRelativeTo(this); d.setVisible(true);
    }
    private void addField(JPanel p,GridBagConstraints c,String label,java.awt.Component comp,int row){c.gridx=0;c.gridy=row;c.weightx=.3;p.add(new JLabel(label),c);c.gridx=1;c.weightx=.7;if(comp instanceof JTextField)((JTextField)comp).setPreferredSize(new Dimension(260,32));p.add(comp,c);}
    private void editSelected(){int r=table.getSelectedRow();if(r<0){showError("Select a train to edit.");return;}String n=String.valueOf(model.getValueAt(table.convertRowIndexToModel(r),0));Train t=controller.getTrainService().findByNumber(n);if(t!=null)showDialog(t);}
    private void deleteSelected(){int r=table.getSelectedRow();if(r<0){showError("Select a train to delete.");return;}String n=String.valueOf(model.getValueAt(table.convertRowIndexToModel(r),0));if(JOptionPane.showConfirmDialog(this,"Delete train "+n+"?","Confirm Delete",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){try{controller.getTrainService().deleteTrain(n);refreshTable();}catch(Exception ex){showError(ex.getMessage());}}}
    private void showError(String m){JOptionPane.showMessageDialog(this,m==null?"Operation failed.":m,"RailSync",JOptionPane.ERROR_MESSAGE);}
}
