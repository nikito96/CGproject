/*
 * DrawView.java
 */

package draw.GUI;

import org.jdesktop.application.Action;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import org.jdesktop.application.ResourceMap;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import draw.Processors.DialogProcessor;
import java.awt.Color;
import javax.swing.JColorChooser;

/**
 * The application's main frame.
 */
public class DrawView extends FrameView {
   
    /**
     * Агрегирания диалогов процесор. Улеснява манипулацията на модела.
     */
    private DialogProcessor dialogProcessor;

    /**
     * Достъп до доалоговия процесор.
     * @return Инстанцията на диалоговия процесор
     */
    public DialogProcessor getDialogProcessor() {
        return dialogProcessor;
    }
 
    public DrawView(SingleFrameApplication app) {
        super(app);

        initComponents();

        // Създава се инстанция от класа на диалоговия процесор.
        dialogProcessor = new DialogProcessor();
        
        // Създаваме поле за рисуване и го правим главен компонент в изгледа.
        DrawViewPortPaint drawViewPortPaint = new draw.GUI.DrawViewPortPaint(this);
        setComponent(drawViewPortPaint);
        // Прихващане на събитията на мишката.
        drawViewPortPaint.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if (DragToggleButton.isSelected()) {
                    
                    draw.Model.Shape sel = dialogProcessor.ContainsPoint(evt.getX(), evt.getY());
                    if(sel != null){
                        if(dialogProcessor.getSelection().contains(sel)){
                            dialogProcessor.getSelection().remove(sel);
                        }else{
                            dialogProcessor.getSelection().add(sel);
                        }
                    }
                        statusMessageLabel.setText("Последно действие: Селекция на примитив");
                        //dialogProcessor.setIsDragging(true);
                        dialogProcessor.setLastLocation(evt.getX(), evt.getY());
                        dialogProcessor.repaint();
                }
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                //dialogProcessor.setIsDragging(false);
            }
        });
        drawViewPortPaint.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                //if (dialogProcessor.getIsDragging()) {
                    if (dialogProcessor.getSelection() != null){
                        statusMessageLabel.setText("Последно действие: Влачене");
                        dialogProcessor.TranslateTo(evt.getPoint());
                        dialogProcessor.repaint();
                    }
                //}
            }
        });
        
        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);
        
        // Икона на главния прозорец
        ImageIcon icon = resourceMap.getImageIcon("DrawIcon");
        getFrame().setIconImage(icon.getImage()); 

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String) (evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer) (evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    /**
     * Показва диалогова кутия с информация за програмата.
     */
    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = DrawApp.getApplication().getMainFrame();
            aboutBox = new DrawAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        DrawApp.getApplication().show(aboutBox);
    }

    /**
     * Бутон, който поставя на произволно място правоъгълник със зададените размери.
     * Променя се лентата със състоянието и се инвалидира контрола, в който визуализираме.
     */
    @Action
    public void drawRectangle() {
        dialogProcessor.AddRandomRectangle();
        statusMessageLabel.setText("Последно действие: Рисуване на правоъгълник");
        dialogProcessor.repaint();
    }

    /** 
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        imageMenu = new javax.swing.JMenu();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        toolBar = new javax.swing.JToolBar();
        DrawRectangleButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        DragToggleButton = new javax.swing.JToggleButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        viewPort = new javax.swing.JPanel();

        menuBar.setName("menuBar"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(draw.GUI.DrawApp.class).getContext().getResourceMap(DrawView.class);
        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(draw.GUI.DrawApp.class).getContext().getActionMap(DrawView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        editMenu.setText(resourceMap.getString("editMenu.text")); // NOI18N
        editMenu.setName("editMenu"); // NOI18N
        menuBar.add(editMenu);

        imageMenu.setText(resourceMap.getString("imageMenu.text")); // NOI18N
        imageMenu.setName("imageMenu"); // NOI18N
        menuBar.add(imageMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 591, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 421, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        toolBar.setRollover(true);
        toolBar.setName("toolBar"); // NOI18N

        DrawRectangleButton.setAction(actionMap.get("drawRectangle")); // NOI18N
        DrawRectangleButton.setIcon(resourceMap.getIcon("DrawRectangleButton.icon")); // NOI18N
        DrawRectangleButton.setText(resourceMap.getString("DrawRectangleButton.text")); // NOI18N
        DrawRectangleButton.setFocusable(false);
        DrawRectangleButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        DrawRectangleButton.setName("DrawRectangleButton"); // NOI18N
        DrawRectangleButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBar.add(DrawRectangleButton);

        jButton1.setAction(actionMap.get("drawEllipse")); // NOI18N
        jButton1.setIcon(resourceMap.getIcon("jButton1.icon")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setName("jButton1"); // NOI18N
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBar.add(jButton1);

        jButton2.setAction(actionMap.get("DrawSquare")); // NOI18N
        jButton2.setIcon(resourceMap.getIcon("jButton2.icon")); // NOI18N
        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setName("jButton2"); // NOI18N
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBar.add(jButton2);

        DragToggleButton.setIcon(resourceMap.getIcon("DragToggleButton.icon")); // NOI18N
        DragToggleButton.setText(resourceMap.getString("DragToggleButton.text")); // NOI18N
        DragToggleButton.setFocusable(false);
        DragToggleButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        DragToggleButton.setName("DragToggleButton"); // NOI18N
        DragToggleButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBar.add(DragToggleButton);

        jButton3.setAction(actionMap.get("pickColor")); // NOI18N
        jButton3.setIcon(resourceMap.getIcon("jButton3.icon")); // NOI18N
        jButton3.setText(resourceMap.getString("jButton3.text")); // NOI18N
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setName("jButton3"); // NOI18N
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBar.add(jButton3);

        jButton4.setAction(actionMap.get("deleteShape")); // NOI18N
        jButton4.setIcon(resourceMap.getIcon("jButton4.icon")); // NOI18N
        jButton4.setText(resourceMap.getString("jButton4.text")); // NOI18N
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setName("jButton4"); // NOI18N
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBar.add(jButton4);

        viewPort.setName("viewPort"); // NOI18N

        javax.swing.GroupLayout viewPortLayout = new javax.swing.GroupLayout(viewPort);
        viewPort.setLayout(viewPortLayout);
        viewPortLayout.setHorizontalGroup(
            viewPortLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 591, Short.MAX_VALUE)
        );
        viewPortLayout.setVerticalGroup(
            viewPortLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 399, Short.MAX_VALUE)
        );

        setComponent(viewPort);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
        setToolBar(toolBar);
    }// </editor-fold>//GEN-END:initComponents

    @Action
    public void drawEllipse() {
        dialogProcessor.AddRandomEllipse();
        statusMessageLabel.setText("Последно действие: Рисуване на елипса");
        dialogProcessor.repaint();
    }

    @Action
    public void DrawSquare() {
        dialogProcessor.AddRandomSquare();
        statusMessageLabel.setText("Последно действие: Рисуване на квадрат");
        dialogProcessor.repaint();
    }

    @Action
    public void pickColor() {
        JColorChooser jcc = new JColorChooser();
        Color c = jcc.showDialog(null, "Pease select a color!", Color.BLACK);
        if(dialogProcessor.getSelection() != null){
            dialogProcessor.setFillColor(c);
            dialogProcessor.repaint();
        }
    }

    @Action
    public void deleteShape() {
        if(dialogProcessor.getSelection() != null){
            dialogProcessor.deleteShape();
            dialogProcessor.repaint();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton DragToggleButton;
    private javax.swing.JButton DrawRectangleButton;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenu imageMenu;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JToolBar toolBar;
    private javax.swing.JPanel viewPort;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;
}
