public class MiniTwitterDriver {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                AdminControlPanel.getInstance().setVisible(true);
            }
        });
    }
}
