package application;

import javax.swing.SwingUtilities;

import data.DataStore;
import ui.MainFrame;

public class RailSyncApplication {

    private final ApplicationState state;
    private final ApplicationController controller;
    private final DataStore dataStore;

    private MainFrame mainFrame;

    public RailSyncApplication() {
        this.state = new ApplicationState();
        this.dataStore = new DataStore();
        this.controller = new ApplicationController(state, dataStore);
    }

    public void start() {
        state.setRunning(true);
        state.setStatusMessage("RailSync is ready");

        SwingUtilities.invokeLater(() -> {
            mainFrame = new MainFrame(controller);
            mainFrame.setVisible(true);
        });
    }

    public ApplicationState getState() {
        return state;
    }

    public ApplicationController getController() {
        return controller;
    }

    public DataStore getDataStore() {
        return dataStore;
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }
}