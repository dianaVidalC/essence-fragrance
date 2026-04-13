package com.essencefragrance.app;

import com.essencefragrance.ui.EssenceFragranceUI;

import javax.swing.*;

public class AppLauncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EssenceFragranceUI form = new EssenceFragranceUI();
            form.setVisible(true);
        });
    }
}
