package com.cwrm;

import javax.swing.SwingUtilities;

/**
 * Application entry point for the Community Waste and Recycling
 * Manager (CWRM). Launches the Swing GUI on the Event Dispatch Thread.
 */
public final class Main {

    private Main() {
        // Utility class - no instances
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // TODO (Member responsible for GUI): construct and show MainFrame here
            System.out.println("CWRM starting... replace this with MainFrame launch.");
        });
    }
}
