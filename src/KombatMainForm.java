
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author gabriel
 */
public class KombatMainForm extends javax.swing.JFrame implements Runnable {

    /**
     * Creates new form KombatMainForm
     */
    public KombatMainForm() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        player = new Player();
        player.setup();
        getContentPane().add(player);        
        connect();
        repaint();
        gameFlowThread = new Thread(this);
        gameFlowThread.start();
    }//GEN-LAST:event_formWindowOpened

    public void connect(){
        try {
            s   = new Socket("localhost",8880);
            in  = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new PrintWriter(s.getOutputStream(),true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void gameFlow() {
        String command;
        try {
            
            //command = in.readLine();
            int maxWidth = this.getWidth() - player.width;
            int maxHeight = this.getHeight() - player.height;
            out.println(maxWidth+","+maxHeight);
            
            while (true) {
                command = in.readLine();
                String data[] = command.split("\\_");
                player.x = Integer.parseInt(data[0]);
                player.y = Integer.parseInt(data[1]);
                
                if(data[4].equals("L")) {
                    player.setIconLeft();
                } else {
                    player.setIconRight();
                }
                
                player.move();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
            out.println("PR_R");
        }
        if (evt.getKeyCode() == KeyEvent.VK_LEFT) {
            out.println("PR_L");
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            out.println("PR_U");
        }
        if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            out.println("PR_D");
        }
    }//GEN-LAST:event_formKeyPressed

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
            key_r = false;
            out.println("RE_R");
        }
        if (evt.getKeyCode() == KeyEvent.VK_LEFT) {
            key_l = false;
            out.println("RE_L");
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            key_u = false;
            out.println("RE_U");
        }
        if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            key_d = false;
            out.println("RE_D");
        }
    }//GEN-LAST:event_formKeyReleased

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    @Override
    public void run() {
        gameFlow();
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(KombatMainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KombatMainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KombatMainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KombatMainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new KombatMainForm().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    Thread gameFlowThread;
    Player player;
    Socket s;
    BufferedReader in;
    PrintWriter out;
    boolean key_r = false;
    boolean key_l = false;
    boolean key_u = false;
    boolean key_d = false;
}
