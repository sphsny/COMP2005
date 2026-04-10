package org.example;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                PatientLookUp frame = new PatientLookUp();
                frame.setVisible(true);
            }
        });
    }
}