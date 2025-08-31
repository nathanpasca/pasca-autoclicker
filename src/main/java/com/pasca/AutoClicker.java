// Checked and Fixed AutoClicker - All errors resolved
package com.pasca;

import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.*;
import java.util.logging.*;
import javax.swing.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseListener;

public class AutoClicker extends JFrame implements NativeMouseListener {
    
    // Core components - Optimized
    private Robot robot;
    private ExecutorService clickerThread;
    private ScheduledExecutorService uiUpdateThread;
    
    // Performance tracking - hapus yang tidak perlu
    private volatile long totalClicks = 0;
    private volatile long sessionStartTime = 0;
    private volatile double currentRealCPS = 0;
    
    // Settings
    private volatile int minCPS = 8;
    private volatile int maxCPS = 12;
    private volatile boolean jitterEnabled = false;
    private volatile int jitterRange = 2;
    
    // State - Optimized flags
    private volatile boolean isEnabled = false;
    private volatile boolean shouldClick = false;
    private volatile boolean isRobotClicking = false;
    
    // UI Components - Modern Design (hapus performance bar)
    private JToggleButton enableButton;
    private JSlider cpsSlider;
    private JLabel cpsValueLabel;
    private JCheckBox jitterCheckbox;
    private JSlider jitterSlider;
    private JLabel statusLabel;
    private JLabel statsLabel;
    private JLabel realTimeStatsLabel;
    private JPanel mainCard;
    private JPanel statsCard;
    
    // Colors - Modern Dark Theme (Compatible)
    private final Color PRIMARY_COLOR = new Color(64, 158, 255);
    private final Color SECONDARY_COLOR = new Color(40, 44, 52);
    private final Color ACCENT_COLOR = new Color(92, 207, 133);
    private final Color DANGER_COLOR = new Color(224, 86, 86);
    private final Color TEXT_PRIMARY = new Color(220, 221, 222);
    private final Color TEXT_SECONDARY = new Color(171, 178, 191);
    private final Color CARD_BACKGROUND = new Color(33, 37, 43);
    private final Color MAIN_BACKGROUND = new Color(22, 27, 34);
    
    public AutoClicker() {
        super("Pasca AutoClicker");
        
        // Set look and feel with maximum compatibility
        setLookAndFeelSafely();
        setupModernTheme();
        
        initializeCore();
        initializeModernUI();
        setupEventHandlers();
        startOptimizedClickerLoop();
        startUIUpdateLoop();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setMinimumSize(new Dimension(420, 580));
        pack();
        setLocationRelativeTo(null);
        
        // Fixed shutdown hook reference
        final AutoClicker self = this;
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                self.cleanup();
            }
        }));
    }
    
    private void setLookAndFeelSafely() {
        try {
            // Try different Look and Feel options in order of preference
            UIManager.LookAndFeelInfo[] availableLAFs = UIManager.getInstalledLookAndFeels();
            boolean lafSet = false;
            
            // 1. Try Nimbus (modern look)
            for (UIManager.LookAndFeelInfo info : availableLAFs) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    lafSet = true;
                    System.out.println("Using Nimbus Look and Feel");
                    break;
                }
            }
            
            // 2. Try Windows if on Windows
            if (!lafSet) {
                for (UIManager.LookAndFeelInfo info : availableLAFs) {
                    if (info.getName().contains("Windows")) {
                        UIManager.setLookAndFeel(info.getClassName());
                        lafSet = true;
                        System.out.println("Using Windows Look and Feel");
                        break;
                    }
                }
            }
            
            // 3. Try GTK if on Linux
            if (!lafSet) {
                for (UIManager.LookAndFeelInfo info : availableLAFs) {
                    if (info.getName().contains("GTK")) {
                        UIManager.setLookAndFeel(info.getClassName());
                        lafSet = true;
                        System.out.println("Using GTK Look and Feel");
                        break;
                    }
                }
            }
            
            // 4. Final fallback to Metal (always available)
            if (!lafSet) {
                UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                System.out.println("Using Metal Look and Feel (fallback)");
            }
            
        } catch (Exception e) {
            System.err.println("Could not set Look and Feel: " + e.getMessage());
            // Continue with default
        }
    }
    
    private void setupModernTheme() {
        // Set global UI properties for modern look (compatible way)
        try {
            UIManager.put("Panel.background", MAIN_BACKGROUND);
            UIManager.put("Label.foreground", TEXT_PRIMARY);
            UIManager.put("CheckBox.foreground", TEXT_PRIMARY);
            UIManager.put("CheckBox.background", CARD_BACKGROUND);
            UIManager.put("Slider.background", CARD_BACKGROUND);
        } catch (Exception e) {
            // If theming fails, continue without custom colors
            System.err.println("Custom theming failed: " + e.getMessage());
        }
    }
    
    private void initializeCore() {
        try {
            // Optimized Robot settings
            robot = new Robot();
            robot.setAutoDelay(0);
            robot.setAutoWaitForIdle(false);
            
            // Use thread pools for better performance (Java 8+ compatible)
            clickerThread = Executors.newSingleThreadExecutor(new ThreadFactory() {
                public Thread newThread(Runnable r) {
                    Thread t = new Thread(r, "AutoClicker-Main");
                    t.setDaemon(true);
                    t.setPriority(Thread.MAX_PRIORITY);
                    return t;
                }
            });
            
            uiUpdateThread = Executors.newScheduledThreadPool(1, new ThreadFactory() {
                public Thread newThread(Runnable r) {
                    Thread t = new Thread(r, "AutoClicker-UI");
                    t.setDaemon(true);
                    t.setPriority(Thread.NORM_PRIORITY);
                    return t;
                }
            });
            
            // JNativeHook setup
            LogManager.getLogManager().reset();
            Logger.getLogger(GlobalScreen.class.getPackage().getName()).setLevel(Level.OFF);
            
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeMouseListener(this);
            
            System.out.println("AutoClicker berhasil diinisialisasi!");
            
        } catch (AWTException e) {
            showError("Error saat membuat Robot: " + e.getMessage());
        } catch (NativeHookException e) {
            showError("Error saat register mouse hook: " + e.getMessage());
        }
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }
    
    private void initializeModernUI() {
        setBackground(MAIN_BACKGROUND);
        setLayout(new BorderLayout(0, 0));
        
        // Main container with padding
        JPanel container = new JPanel(new BorderLayout(15, 15));
        container.setBackground(MAIN_BACKGROUND);
        container.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header Panel
        JPanel headerPanel = createHeaderPanel();
        container.add(headerPanel, BorderLayout.NORTH);
        
        // Main Card
        mainCard = createMainCard();
        container.add(mainCard, BorderLayout.CENTER);
        
        // Stats Card
        statsCard = createStatsCard();
        container.add(statsCard, BorderLayout.SOUTH);
        
        add(container);
    }
    
    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(MAIN_BACKGROUND);
        
        // Title
        JLabel titleLabel = new JLabel("Pasca AutoClicker");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        header.add(titleLabel, BorderLayout.CENTER);
        
        return header;
    }
    
    private JPanel createMainCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(CARD_BACKGROUND);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY_COLOR, 2),
            BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));
        
        // Instructions Panel - Tambahkan di atas
        JPanel instructionsPanel = createInstructionsPanel();
        card.add(instructionsPanel);
        card.add(Box.createVerticalStrut(20));
        
        // Enable Button - Diperbaiki layout
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(CARD_BACKGROUND);
        enableButton = createModernToggleButton();
        buttonPanel.add(enableButton);
        card.add(buttonPanel);
        card.add(Box.createVerticalStrut(20));
        
        // Status Panel
        JPanel statusPanel = createStatusPanel();
        card.add(statusPanel);
        card.add(Box.createVerticalStrut(20));
        
        // CPS Control Panel
        JPanel cpsPanel = createCPSPanel();
        card.add(cpsPanel);
        card.add(Box.createVerticalStrut(15));
        
        // Jitter Control Panel
        JPanel jitterPanel = createJitterPanel();
        card.add(jitterPanel);
        
        return card;
    }
    
    private JPanel createInstructionsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CARD_BACKGROUND);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        
        JLabel titleLabel = new JLabel("💡 CARA PENGGUNAAN");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JTextArea instructionsText = new JTextArea(
            "1. Klik tombol 'AKTIFKAN AUTOCLICKER' untuk mengaktifkan\n" +
            "2. Atur kecepatan klik (CPS) sesuai kebutuhan\n" +
            "3. TAHAN tombol kiri mouse untuk mulai auto-click\n" +
            "4. LEPAS tombol kiri mouse untuk berhenti\n" +
            "5. Aktifkan jitter untuk gerakan lebih alami"
        );
        instructionsText.setFont(new Font("SansSerif", Font.PLAIN, 11));
        instructionsText.setForeground(TEXT_PRIMARY);
        instructionsText.setBackground(CARD_BACKGROUND);
        instructionsText.setEditable(false);
        instructionsText.setFocusable(false);
        instructionsText.setLineWrap(true);
        instructionsText.setWrapStyleWord(true);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(Box.createVerticalStrut(8));
        
        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setBackground(CARD_BACKGROUND);
        textPanel.add(instructionsText, BorderLayout.CENTER);
        panel.add(textPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JToggleButton createModernToggleButton() {
        JToggleButton button = new JToggleButton("AKTIFKAN AUTOCLICKER") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Background
                Color bgColor = isSelected() ? DANGER_COLOR : ACCENT_COLOR;
                g2.setColor(bgColor);
                g2.fillRect(2, 2, getWidth() - 4, getHeight() - 4);
                
                // Border with hover effect
                Color borderColor = bgColor.brighter();
                g2.setStroke(new BasicStroke(2));
                g2.setColor(borderColor);
                g2.drawRect(1, 1, getWidth() - 3, getHeight() - 3);
                
                // Text
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("SansSerif", Font.BOLD, 14));
                FontMetrics fm = g2.getFontMetrics();
                String text = getText();
                int textWidth = fm.stringWidth(text);
                int textHeight = fm.getAscent();
                g2.drawString(text, (getWidth() - textWidth) / 2, (getHeight() + textHeight) / 2 - 2);
                
                g2.dispose();
            }
        };
        
        button.setPreferredSize(new Dimension(280, 45));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return button;
    }
    
    private JPanel createStatusPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CARD_BACKGROUND);
        
        statusLabel = new JLabel("Status: Nonaktif", SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        statusLabel.setForeground(TEXT_SECONDARY);
        
        panel.add(statusLabel);
        return panel;
    }
    
    private JPanel createCPSPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(CARD_BACKGROUND);
        
        // Title
        JLabel title = new JLabel("Kecepatan Klik (CPS)");
        title.setFont(new Font("SansSerif", Font.BOLD, 14));
        title.setForeground(TEXT_PRIMARY);
        
        // CPS Slider
        cpsSlider = new JSlider(1, 50, maxCPS);
        cpsSlider.setBackground(CARD_BACKGROUND);
        cpsSlider.setForeground(PRIMARY_COLOR);
        cpsSlider.setPaintTicks(true);
        cpsSlider.setPaintLabels(true);
        cpsSlider.setMajorTickSpacing(10);
        cpsSlider.setMinorTickSpacing(5);
        
        // CPS Value Label
        cpsValueLabel = new JLabel(maxCPS + " CPS", SwingConstants.CENTER);
        cpsValueLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        cpsValueLabel.setForeground(PRIMARY_COLOR);
        
        panel.add(title, BorderLayout.NORTH);
        panel.add(cpsSlider, BorderLayout.CENTER);
        panel.add(cpsValueLabel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createJitterPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(CARD_BACKGROUND);
        
        // Jitter checkbox
        jitterCheckbox = new JCheckBox("Aktifkan Gerakan Acak (Jitter)");
        jitterCheckbox.setFont(new Font("SansSerif", Font.PLAIN, 13));
        jitterCheckbox.setForeground(TEXT_PRIMARY);
        jitterCheckbox.setBackground(CARD_BACKGROUND);
        jitterCheckbox.setFocusPainted(false);
        
        // Jitter slider
        jitterSlider = new JSlider(0, 10, jitterRange);
        jitterSlider.setBackground(CARD_BACKGROUND);
        jitterSlider.setForeground(ACCENT_COLOR);
        jitterSlider.setPaintTicks(true);
        jitterSlider.setMajorTickSpacing(2);
        jitterSlider.setMinorTickSpacing(1);
        jitterSlider.setEnabled(false);
        
        panel.add(jitterCheckbox, BorderLayout.NORTH);
        panel.add(jitterSlider, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createStatsCard() {
        JPanel card = new JPanel(new GridLayout(1, 2, 15, 10));
        card.setBackground(CARD_BACKGROUND);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY_COLOR, 2),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        
        // Stats labels
        statsLabel = createStatLabel("Total Klik: 0", "Sesi dimulai: -");
        realTimeStatsLabel = createStatLabel("CPS Real-time: 0.0", "Durasi: 00:00");
        
        card.add(createStatPanel("STATISTIK SESI", statsLabel));
        card.add(createStatPanel("REAL-TIME", realTimeStatsLabel));
        
        return card;
    }
    
    private JLabel createStatLabel(String mainText, String subText) {
        String html = "<html><div style='text-align: center;'>" +
            "<span style='color: #DCDDDE; font-size: 14px; font-weight: bold;'>" + mainText + "</span><br>" +
            "<span style='color: #ABB2BF; font-size: 11px;'>" + subText + "</span>" +
            "</div></html>";
        
        JLabel label = new JLabel(html);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }
    
    private JPanel createStatPanel(String title, JLabel contentLabel) {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setBackground(CARD_BACKGROUND);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 10));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(contentLabel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void setupEventHandlers() {
        // Fixed reference to outer class
        final AutoClicker self = this;
        
        // Enable button
        enableButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isEnabled = enableButton.isSelected();
                if (!isEnabled) {
                    shouldClick = false;
                    self.resetStats();
                } else {
                    sessionStartTime = System.currentTimeMillis();
                }
                self.updateUI();
                self.updateButtonText();
            }
        });
        
        // CPS Slider
        cpsSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                if (!cpsSlider.getValueIsAdjusting()) {
                    int value = cpsSlider.getValue();
                    minCPS = Math.max(1, value - 2);
                    maxCPS = value;
                    self.updateCPSLabel();
                }
            }
        });
        
        // Jitter controls
        jitterCheckbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jitterEnabled = jitterCheckbox.isSelected();
                jitterSlider.setEnabled(jitterEnabled);
            }
        });
        
        jitterSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                if (!jitterSlider.getValueIsAdjusting()) {
                    jitterRange = jitterSlider.getValue();
                }
            }
        });
        
        // Window events
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                self.cleanup();
                System.exit(0);
            }
        });
    }
    
    // Mouse listeners (compatible)
    public void nativeMousePressed(NativeMouseEvent e) {
        if (e.getButton() == NativeMouseEvent.BUTTON1 && isEnabled && !isRobotClicking) {
            shouldClick = true;
        }
    }
    
    public void nativeMouseReleased(NativeMouseEvent e) {
        if (e.getButton() == NativeMouseEvent.BUTTON1 && !isRobotClicking) {
            shouldClick = false;
        }
    }
    
    public void nativeMouseClicked(NativeMouseEvent e) {
        // Not needed
    }
    
    // Optimized clicking loop
    private void startOptimizedClickerLoop() {
        clickerThread.submit(new Runnable() {
            public void run() {
                long lastClickTime = 0;
                
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        if (isEnabled && shouldClick) {
                            long currentTime = System.nanoTime();
                            long targetDelay = calculateOptimizedDelay() * 1000000L; // Convert to nanoseconds
                            
                            if (currentTime - lastClickTime >= targetDelay) {
                                performOptimizedClick();
                                lastClickTime = currentTime;
                                totalClicks++;
                            }
                            
                            Thread.sleep(1); // 1ms precision
                            
                        } else {
                            Thread.sleep(5); // Longer sleep when not clicking
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    } catch (Exception e) {
                        System.err.println("Error di clicker loop: " + e.getMessage());
                    }
                }
            }
        });
    }
    
    private void performOptimizedClick() {
        try {
            isRobotClicking = true;
            
            Point mousePos = MouseInfo.getPointerInfo().getLocation();
            
            // Optimized jitter
            if (jitterEnabled && jitterRange > 0) {
                int offsetX = (int) (Math.random() * (2 * jitterRange + 1)) - jitterRange;
                int offsetY = (int) (Math.random() * (2 * jitterRange + 1)) - jitterRange;
                
                robot.mouseMove(mousePos.x + offsetX, mousePos.y + offsetY);
                robot.mouseMove(mousePos.x, mousePos.y);
            }
            
            // Perform click
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            
        } finally {
            isRobotClicking = false;
        }
    }
    
    private long calculateOptimizedDelay() {
        if (minCPS == maxCPS) {
            return 1000 / maxCPS;
        }
        int minDelay = 1000 / maxCPS;
        int maxDelay = 1000 / minCPS;
        return (long) (Math.random() * (maxDelay - minDelay + 1)) + minDelay;
    }
    
    // UI updates
    private void startUIUpdateLoop() {
        final AutoClicker self = this;
        uiUpdateThread.scheduleAtFixedRate(new Runnable() {
            public void run() {
                if (isEnabled) {
                    self.updateRealTimeStats();
                }
            }
        }, 0, 500, TimeUnit.MILLISECONDS);
    }
    
    private void updateRealTimeStats() {
        final AutoClicker self = this;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                long currentTime = System.currentTimeMillis();
                long duration = currentTime - sessionStartTime;
                
                if (duration > 0) {
                    currentRealCPS = (totalClicks * 1000.0) / duration;
                    
                    String durationStr = self.formatDuration(duration);
                    DecimalFormat df = new DecimalFormat("#.#");
                    
                    String statsText = "Total Klik: " + totalClicks;
                    String subStatsText = "Sesi dimulai: " + new SimpleDateFormat("HH:mm").format(new Date());
                    
                    String realTimeText = "CPS Real-time: " + df.format(currentRealCPS);
                    String realTimeSubText = "Durasi: " + durationStr;
                    
                    self.updateStatLabel(statsLabel, statsText, subStatsText);
                    self.updateStatLabel(realTimeStatsLabel, realTimeText, realTimeSubText);
                    
                    // Update status
                    if (shouldClick) {
                        statusLabel.setText("Status: Aktif - Melakukan klik otomatis");
                        statusLabel.setForeground(ACCENT_COLOR);
                    } else if (isEnabled) {
                        statusLabel.setText("Status: Siap - Tahan tombol kiri mouse");
                        statusLabel.setForeground(PRIMARY_COLOR);
                    }
                }
            }
        });
    }
    
    private void updateStatLabel(JLabel label, String mainText, String subText) {
        String html = "<html><div style='text-align: center;'>" +
            "<span style='color: #DCDDDE; font-size: 14px; font-weight: bold;'>" + mainText + "</span><br>" +
            "<span style='color: #ABB2BF; font-size: 11px;'>" + subText + "</span>" +
            "</div></html>";
        label.setText(html);
    }
    
    private void updateUI() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (isEnabled) {
                    statusLabel.setText("Status: Siap - Tahan tombol kiri mouse");
                    statusLabel.setForeground(PRIMARY_COLOR);
                } else {
                    statusLabel.setText("Status: Nonaktif");
                    statusLabel.setForeground(TEXT_SECONDARY);
                    updateStatLabel(statsLabel, "Total Klik: 0", "Sesi dimulai: -");
                    updateStatLabel(realTimeStatsLabel, "CPS Real-time: 0.0", "Durasi: 00:00");
                }
            }
        });
    }
    
    private void updateButtonText() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (enableButton.isSelected()) {
                    enableButton.setText("MATIKAN AUTOCLICKER");
                } else {
                    enableButton.setText("AKTIFKAN AUTOCLICKER");
                }
                enableButton.repaint();
            }
        });
    }
    
    private void updateCPSLabel() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                cpsValueLabel.setText(maxCPS + " CPS");
            }
        });
    }
    
    private void resetStats() {
        totalClicks = 0;
        sessionStartTime = 0;
        currentRealCPS = 0;
    }
    
    private String formatDuration(long millis) {
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
    
    private void cleanup() {
        try {
            shouldClick = false;
            if (clickerThread != null && !clickerThread.isShutdown()) {
                clickerThread.shutdownNow();
            }
            if (uiUpdateThread != null && !uiUpdateThread.isShutdown()) {
                uiUpdateThread.shutdownNow();
            }
            GlobalScreen.unregisterNativeHook();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        // Set system properties for better performance and compatibility
        System.setProperty("sun.java2d.d3d", "true");
        System.setProperty("sun.java2d.opengl", "true");
        System.setProperty("swing.aatext", "true");
        System.setProperty("awt.useSystemAAFontSettings", "on");
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new AutoClicker().setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, 
                        "Error saat memulai aplikasi: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}