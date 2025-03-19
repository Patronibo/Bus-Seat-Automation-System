package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.*;

public class GuzergahSeferSecim extends JFrame {

    private JComboBox<String> seferTarihiComboBox;
    private JComboBox<String> baslangicComboBox;
    private JComboBox<String> bitisComboBox;
    private JComboBox<String> seferlerComboBox;
    private JButton tamamlaButton;

    public GuzergahSeferSecim() {
        setTitle("Güzergah ve Sefer Seçim Ekranı");
        setSize(600, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        // Ana panel: Arka plan resmi ve bileşenlerin bulunduğu katman
        JPanel mainPanel = new JPanel(null) {
            private final Image backgroundImage = new ImageIcon("C://Users//ilhan//Desktop//guzergahBg.png").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setBounds(0, 0, 600, 650);
        setContentPane(mainPanel);

        // Bileşenler
        JLabel seferYazi = new JLabel("Sefer Seçimi");
        seferYazi.setFont(new Font("Arial", Font.BOLD, 20));
        seferYazi.setForeground(Color.WHITE);
        seferYazi.setBounds(230, 100, 200, 30);
        mainPanel.add(seferYazi);

        JLabel seferTarihiLabel = new JLabel("Sefer Tarihi:");
        seferTarihiLabel.setFont(new Font("Arial", Font.BOLD, 16));
        seferTarihiLabel.setForeground(Color.WHITE);
        seferTarihiLabel.setBounds(110, 200, 120, 25);
        mainPanel.add(seferTarihiLabel);

        seferTarihiComboBox = new JComboBox<>();
        seferTarihiComboBox.setBounds(360, 200, 150, 30);
        mainPanel.add(seferTarihiComboBox);

        JLabel baslangicLabel = new JLabel("Başlangıç Güzergahı:");
        baslangicLabel.setFont(new Font("Arial", Font.BOLD, 16));
        baslangicLabel.setForeground(Color.WHITE);
        baslangicLabel.setBounds(110, 260, 170, 25);
        mainPanel.add(baslangicLabel);

        baslangicComboBox = new JComboBox<>(new String[]{"Diyarbakır", "Adıyaman"});
        baslangicComboBox.setBounds(360, 260, 150, 30);
        mainPanel.add(baslangicComboBox);

        JLabel bitisLabel = new JLabel("Bitiş Güzergahı:");
        bitisLabel.setFont(new Font("Arial", Font.BOLD, 16));
        bitisLabel.setForeground(Color.WHITE);
        bitisLabel.setBounds(110, 320, 170, 25);
        mainPanel.add(bitisLabel);

        bitisComboBox = new JComboBox<>();
        bitisComboBox.setBounds(360, 320, 150, 30);
        mainPanel.add(bitisComboBox);

        JLabel seferlerLabel = new JLabel("Seferler:");
        seferlerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        seferlerLabel.setForeground(Color.WHITE);
        seferlerLabel.setBounds(110, 380, 150, 25);
        mainPanel.add(seferlerLabel);

        seferlerComboBox = new JComboBox<>();
        seferlerComboBox.setBounds(230, 380, 280, 30);
        mainPanel.add(seferlerComboBox);

        tamamlaButton = new JButton("Tamamla");
        tamamlaButton.setBounds(220, 500, 150, 30);
        mainPanel.add(tamamlaButton);

        // Veritabanından Sefer Tarihlerini Çek
        loadSeferTarihleri();

        // Başlangıç Güzergahı Dinamik Ayar
        baslangicComboBox.addActionListener(e -> updateBitisGuzergahi());

        // Seferler Dinamik Ayar
        ActionListener seferleriYukleListener = e -> loadSeferler();
        seferTarihiComboBox.addActionListener(seferleriYukleListener);
        baslangicComboBox.addActionListener(seferleriYukleListener);
        bitisComboBox.addActionListener(seferleriYukleListener);

        // Tamamla Butonu
        tamamlaButton.addActionListener(e -> tamamla());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadSeferTarihleri() {
        try (Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/seferyönetim", "root", "ilhancan21");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT DISTINCT sefer_tarihi FROM seferler")) {

            while (rs.next()) {
                seferTarihiComboBox.addItem(rs.getString("sefer_tarihi"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Sefer tarihleri yüklenirken hata oluştu!", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateBitisGuzergahi() {
        String baslangic = (String) baslangicComboBox.getSelectedItem();
        bitisComboBox.removeAllItems();
        if ("Diyarbakır".equals(baslangic)) {
            bitisComboBox.addItem("Adıyaman");
        } else {
            bitisComboBox.addItem("Diyarbakır");
        }
    }

    private void loadSeferler() {
        String seferTarihi = (String) seferTarihiComboBox.getSelectedItem();
        String baslangic = (String) baslangicComboBox.getSelectedItem();
        String bitis = (String) bitisComboBox.getSelectedItem();

        if (seferTarihi == null || baslangic == null || bitis == null) {
            return;
        }

        seferlerComboBox.removeAllItems();

        try (Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:****/seferyönetim", "****", "****");
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT id, sefer_saati, güzergah, plaka FROM seferler WHERE sefer_tarihi = ? AND güzergah = ?")) {

            String guzergah = baslangic + " - " + bitis;

            pstmt.setDate(1, Date.valueOf(seferTarihi));
            pstmt.setString(2, guzergah);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                	int seferID = rs.getInt("id");
                    String seferSaati = rs.getString("sefer_saati");
                    String guzergahSonuc = rs.getString("güzergah");
                    String plaka = rs.getString("plaka");
                    
                    seferlerComboBox.addItem(seferSaati + " - " + guzergahSonuc + " - " + plaka);
                    seferlerComboBox.setToolTipText(String.valueOf(seferID));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Seferler yüklenirken hata oluştu!", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void tamamla() {
        int selectedIndex = seferlerComboBox.getSelectedIndex();

        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen bir sefer seçin!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // ToolTip'ten Sefer ID'yi alın
        String seferIdStr = seferlerComboBox.getToolTipText();
        int seferId = Integer.parseInt(seferIdStr);

        JOptionPane.showMessageDialog(this, "Seçilen sefer ID: " + seferId, "Bilgi", JOptionPane.INFORMATION_MESSAGE);

        // OtobusKoltukSistemi ekranına seferId'yi aktarın
        new OtobusKoltukSistemi(seferId); // Constructor ile seferId'yi gönderiyoruz
        dispose();
    }


    public static void main(String[] args) {
        new GuzergahSeferSecim();
    }
}
