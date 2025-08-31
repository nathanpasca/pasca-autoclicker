package com.pasca.ui;

import com.pasca.core.ClickerEngine;
import com.pasca.core.ClickerSettings;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClickerMainFrame extends JFrame {
    private ClickerEngine clickerEngine;
    private ClickerSettings settings;
    
    private JToggleButton enableButton;
    private JSpinner minCpsSpinner;
    private JSpinner maxCpsSpinner;
    private JCheckBox jitterCheckbox;
    private JSpinner jitterRangeSpinner;
    private JLabel statusLabel;
    
    public ClickerMainFrame(ClickerEngine clickerEngine) {
        this.clickerEngine = clickerEngine;
        this.settings = clickerEngine.getSettings();
        
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        
        setTitle("Modern AutoClicker v2.0");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }
    
    private void initializeComponents() {
        // Enable/Disable button
        enableButton = new JToggleButton("Enable AutoClicker");
        enableButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        enableButton.setPreferredSize(new Dimension(200, 40));
        
        // CPS settings
        minCpsSpinner = new JSpinner(new SpinnerNumberModel(settings.getMinCps(), 1, 100, 1));
        maxCpsSpinner = new JSpinner(new SpinnerNumberModel(settings.getMaxCps(), 1, 100, 1));
        
        // Jitter settings
        jitterCheckbox = new JCheckBox("Enable Jitter");
        jitterRangeSpinner = new JSpinner(new SpinnerNumberModel(settings.getJitterRange(), 0, 10, 1));
        jitterRangeSpinner.setEnabled(false);
        
        // Status label
        statusLabel = new JLabel("Status: Disabled");
        statusLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        
        // Main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Enable button
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 15, 0);
        mainPanel.add(enableButton, gbc);
        
        // CPS Settings Panel
        JPanel cpsPanel = new JPanel(new GridBagLayout());
        cpsPanel.setBorder(new TitledBorder("Click Speed (CPS)"));
        GridBagConstraints cpsGbc = new GridBagConstraints();
        
        cpsGbc.gridx = 0; cpsGbc.gridy = 0;
        cpsGbc.insets = new Insets(5, 5, 5, 5);
        cpsPanel.add(new JLabel("Min CPS:"), cpsGbc);
        
        cpsGbc.gridx = 1;
        cpsPanel.add(minCpsSpinner, cpsGbc);
        
        cpsGbc.gridx = 2;
        cpsPanel.add(new JLabel("Max CPS:"), cpsGbc);
        
        cpsGbc.gridx = 3;
        cpsPanel.add(maxCpsSpinner, cpsGbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 10, 0);
        mainPanel.add(cpsPanel, gbc);
        
        // Jitter Settings Panel
        JPanel jitterPanel = new JPanel(new FlowLayout());
        jitterPanel.setBorder(new TitledBorder("Jitter Settings"));
        jitterPanel.add(jitterCheckbox);
        jitterPanel.add(new JLabel("Range:"));
        jitterPanel.add(jitterRangeSpinner);
        
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 10, 0);
        mainPanel.add(jitterPanel, gbc);
        
        // Status panel
        JPanel statusPanel = new JPanel(new FlowLayout());
        statusPanel.add(statusLabel);
        
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 0, 0);
        mainPanel.add(statusPanel, gbc);
        
        // Instructions panel
        JPanel instructionsPanel = new JPanel(new BorderLayout());
        instructionsPanel.setBorder(new TitledBorder("Instructions"));
        JTextArea instructions = new JTextArea(
            "1. Click 'Enable AutoClicker' to activate\n" +
            "2. Hold down LEFT MOUSE BUTTON to start auto-clicking\n" +
            "3. Release LEFT MOUSE BUTTON to stop auto-clicking\n" +
            "4. Adjust CPS settings as needed\n" +
            "5. Enable jitter to add small random movements"
        );
        instructions.setEditable(false);
        instructions.setOpaque(false);
        instructions.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));
        instructionsPanel.add(instructions);
        
        add(mainPanel, BorderLayout.CENTER);
        add(instructionsPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        // Enable button
        enableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean enabled = enableButton.isSelected();
                clickerEngine.setEnabled(enabled);
                updateStatus();
                updateButtonAppearance();
            }
        });
        
        // CPS spinners
        minCpsSpinner.addChangeListener(e -> {
            int minCps = (Integer) minCpsSpinner.getValue();
            int maxCps = (Integer) maxCpsSpinner.getValue();
            if (minCps > maxCps) {
                maxCpsSpinner.setValue(minCps);
            }
            settings.setMinCps(minCps);
            settings.setMaxCps((Integer) maxCpsSpinner.getValue());
        });
        
        maxCpsSpinner.addChangeListener(e -> {
            int maxCps = (Integer) maxCpsSpinner.getValue();
            int minCps = (Integer) minCpsSpinner.getValue();
            if (maxCps < minCps) {
                minCpsSpinner.setValue(maxCps);
            }
            settings.setMaxCps(maxCps);
            settings.setMinCps((Integer) minCpsSpinner.getValue());
        });
        
        // Jitter checkbox
        jitterCheckbox.addActionListener(e -> {
            boolean enabled = jitterCheckbox.isSelected();
            settings.setJitterEnabled(enabled);
            jitterRangeSpinner.setEnabled(enabled);
        });
        
        // Jitter range spinner
        jitterRangeSpinner.addChangeListener(e -> {
            settings.setJitterRange((Integer) jitterRangeSpinner.getValue());
        });
        
        // Window closing
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                clickerEngine.stop();
                System.exit(0);
            }
        });
    }
    
    private void updateStatus() {
        if (clickerEngine.isEnabled()) {
            statusLabel.setText("Status: Enabled - Hold LEFT MOUSE to auto-click");
            statusLabel.setForeground(new Color(0, 150, 0));
        } else {
            statusLabel.setText("Status: Disabled");
            statusLabel.setForeground(Color.BLACK);
        }
    }
    
    private void updateButtonAppearance() {
        if (enableButton.isSelected()) {
            enableButton.setText("Disable AutoClicker");
            enableButton.setBackground(new Color(220, 80, 80));
        } else {
            enableButton.setText("Enable AutoClicker");
            enableButton.setBackground(null);
        }
    }
}
