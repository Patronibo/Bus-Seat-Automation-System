package Admin;

import javax.swing.*;

import Helper.DatabaseConnection;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SeferDuzenle extends JPanel {
    private JComboBox<String> seferComboBox, plakaComboBox, soforComboBox;
    private JLabel guzergahLabel;
    private JTextField tarihField, saatField;
    private JButton kaydetButton, geriButton;
    private MainAdmin mainAdmin;
    
    ImageIcon foto = new ImageIcon("C://Users//ilhan//Desktop//beyazcıkısSon.png"); // Buton Arka Planı
    ImageIcon icon = new ImageIcon("C://Users//ilhan//Desktop//cıkıs8.png"); // Geri Tuşu Arka Planı 
    ImageIcon arka = new ImageIcon("C://Users//ilhan//Desktop//bgg.jpg");	 // Ana Tablo Arka Planı
    ImageIcon tablo = new ImageIcon("C://Users//ilhan//Desktop//seferDuzenle.png"); // Panel Arka Planı

    public SeferDuzenle(MainAdmin mainAdmin) {
        this.mainAdmin = mainAdmin;


        setLayout(null);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Ana Katman için JLayeredPane
        JLayeredPane mainLayeredPane = new JLayeredPane();
        mainLayeredPane.setBounds(0, 0, 480, 380);

        // Ana Arka Plan Resmi
        JLabel mainBackground = new JLabel(arka); // tablo, ana arka plan resmi
        mainBackground.setBounds(0, 0, 480, 380);
        mainLayeredPane.add(mainBackground, Integer.valueOf(0)); // En alt katman
        

        // Düzenleme Formu Katmanlı Yapı
        JLayeredPane formPanelLayeredPane = new JLayeredPane();
        formPanelLayeredPane.setBounds(10, 20, 460, 320);

        JLabel formPanelBackground = new JLabel(tablo); // panelTablo, form paneli arka plan resmi
        formPanelBackground.setBounds(0, 0, 460, 320);
        formPanelLayeredPane.add(formPanelBackground, Integer.valueOf(0));

        JPanel formPanel = new JPanel(null);
        formPanel.setBounds(0, 0, 460, 320);
        formPanel.setOpaque(false); // Arka planı şeffaf yap

        JLabel seferLabel = new JLabel("Düzenlenecek Sefer:");
        seferLabel.setBounds(20, 20, 150, 25);
        seferLabel.setForeground(Color.WHITE);
        formPanel.add(seferLabel);

        seferComboBox = new JComboBox<>();
        seferComboBox.setBounds(170, 20, 280, 25);
        formPanel.add(seferComboBox);


        JLabel plakaLabel = new JLabel("Plaka:");
        plakaLabel.setBounds(20, 60, 150, 25);
        plakaLabel.setForeground(Color.WHITE);
        formPanel.add(plakaLabel);

        plakaComboBox = new JComboBox<>();
        plakaComboBox.setBounds(170, 60, 250, 25);
        formPanel.add(plakaComboBox);

        JLabel soforLabel = new JLabel("Şoför:");
        soforLabel.setBounds(20, 100, 150, 25);
        soforLabel.setForeground(Color.WHITE);
        formPanel.add(soforLabel);

        soforComboBox = new JComboBox<>();
        soforComboBox.setBounds(170, 100, 250, 25);
        formPanel.add(soforComboBox);

        JLabel guzergahLabelYazi = new JLabel("Güzergah:");
        guzergahLabelYazi.setBounds(20, 130, 150, 25);
        guzergahLabelYazi.setForeground(Color.WHITE);
        formPanel.add(guzergahLabelYazi);

        guzergahLabel = new JLabel();
        guzergahLabel.setBounds(170, 130, 250, 25);
        guzergahLabel.setForeground(Color.WHITE);
        formPanel.add(guzergahLabel);

        JLabel tarihLabel = new JLabel("Sefer Tarihi:");
        tarihLabel.setBounds(20, 170, 150, 25);
        tarihLabel.setForeground(Color.WHITE);
        formPanel.add(tarihLabel);

        tarihField = new JTextField();
        tarihField.setBounds(170, 170, 250, 25);
        formPanel.add(tarihField);

        JLabel saatLabel = new JLabel("Sefer Saati:");
        saatLabel.setBounds(20, 210, 150, 25);
        saatLabel.setForeground(Color.WHITE);
        formPanel.add(saatLabel);

        saatField = new JTextField();
        saatField.setBounds(170, 210, 250, 25);
        formPanel.add(saatField);
        
        kaydetButton = new JButton("Kaydet",foto);
        kaydetButton.setBounds(230, 250, 150, 35);
        butonDegistir(kaydetButton);
        kaydetButton.addActionListener(e -> kaydet());
        formPanel.add(kaydetButton);

        geriButton = new JButton(icon);
        geriButton.setBounds(80, 240, 60, 60);
        butonDegistir(geriButton);
        formPanel.add(geriButton);


        formPanelLayeredPane.add(formPanel, Integer.valueOf(1)); // Üst katmana ekle
        mainLayeredPane.add(formPanelLayeredPane, Integer.valueOf(1)); // Ana katmana ekle


        // Ana katmanı ekle
        add(mainLayeredPane);

        // Verileri Yükle
        seferleriYukle();
        plakaYukle();
        soforYukle();
        
        
        seferComboBox.addActionListener(e -> seferDetayYukle());
        geriButton.addActionListener(e -> mainAdmin.PanelGöster("AdminPanel"));
    }

    
    public void seferleriYukle() {
        seferComboBox.removeAllItems();
        try (Connection connect = DatabaseConnection.getConnection()) {
            String sql = "SELECT id, CONCAT(plaka, ' - ', güzergah, ' (', sefer_tarihi, ')') AS sefer_bilgisi FROM seferler ORDER BY id DESC";
            PreparedStatement state = connect.prepareStatement(sql);
            ResultSet rs = state.executeQuery();

            while (rs.next()) {
                seferComboBox.addItem(rs.getString("sefer_bilgisi"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Seferler yüklenirken hata oluştu: " + e.getMessage());
        }
    }

    private void plakaYukle() {
        plakaComboBox.removeAllItems();
        try (Connection connect = DatabaseConnection.getConnection()) {
           String sql = "SELECT plaka FROM otobusler";
            PreparedStatement state = connect.prepareStatement(sql);
            ResultSet rs = state.executeQuery();

            while (rs.next()) {
                plakaComboBox.addItem(rs.getString("plaka"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Plakalar yüklenirken hata oluştu: " + e.getMessage());
        }
    }

    private void soforYukle() {
        soforComboBox.removeAllItems();
        try (Connection connect = DatabaseConnection.getConnection()) {
            String sql = "SELECT CONCAT(sofor_isim, ' ', sofor_soyisim) AS sofor_full_name FROM soforler";
            PreparedStatement state = connect.prepareStatement(sql);
            ResultSet rs = state.executeQuery();

            while (rs.next()) {
                soforComboBox.addItem(rs.getString("sofor_full_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Şoförler yüklenirken hata oluştu: " + e.getMessage());
        }
    }

    private void seferDetayYukle() {
        String selectedSefer = (String) seferComboBox.getSelectedItem();
        if (selectedSefer == null) return;

        try (Connection connect = DatabaseConnection.getConnection()) {
            String sql = "SELECT plaka, Şoför, güzergah, sefer_tarihi, sefer_saati FROM seferler WHERE CONCAT(plaka, ' - ', güzergah, ' (', sefer_tarihi, ')') = ?";
            PreparedStatement state = connect.prepareStatement(sql);
            state.setString(1, selectedSefer);
            ResultSet rs = state.executeQuery();

            if (rs.next()) {
                plakaComboBox.setSelectedItem(rs.getString("plaka"));
                soforComboBox.setSelectedItem(rs.getString("Şoför"));
                guzergahLabel.setText(rs.getString("güzergah"));
                tarihField.setText(rs.getString("sefer_tarihi"));
                saatField.setText(rs.getString("sefer_saati"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Sefer bilgileri yüklenirken hata oluştu: " + e.getMessage());
        }
    }

    private void kaydet() {
        String selectedSefer = (String) seferComboBox.getSelectedItem();
        if (selectedSefer == null) {
            JOptionPane.showMessageDialog(this, "Lütfen düzenlenecek bir sefer seçin!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String yeniTarih = tarihField.getText().trim();
        String yeniSaat = saatField.getText().trim();
        if (yeniTarih.isEmpty() || yeniSaat.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lütfen tarih ve saat alanlarını doldurun!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection connect = DatabaseConnection.getConnection()) {
            String sql = "UPDATE seferler SET plaka = ?, Şoför = ?, sefer_tarihi = ?, sefer_saati = ? WHERE CONCAT(plaka, ' - ', güzergah, ' (', sefer_tarihi, ')') = ?";
            PreparedStatement state = connect.prepareStatement(sql);
            state.setString(1, (String) plakaComboBox.getSelectedItem());
            state.setString(2, (String) soforComboBox.getSelectedItem());
            state.setString(3, yeniTarih);
            state.setString(4, yeniSaat);
            state.setString(5, selectedSefer);

            int rows = state.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Sefer başarıyla güncellendi!");
                mainAdmin.seferDuzenleGuncelle();
            } else {
                JOptionPane.showMessageDialog(this, "Güncelleme başarısız oldu.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Veritabanı hatası: " + e.getMessage());
        }
    }

	private static void butonDegistir(JButton button) {
		
		button.setHorizontalTextPosition(SwingConstants.CENTER); // Yazıyı merkeze hizala
		button.setVerticalTextPosition(SwingConstants.CENTER); // Yazıyı merkeze hizala
		button.setForeground(Color.BLACK); // Yazıyı beyaz yap
		button.setFont(new Font("Arial", Font.BOLD, 12)); // Yazı tipini ayarla
		button.setOpaque(false); // Şeffaflık
		button.setContentAreaFilled(false); // Dolgu olmadan fotoğraf kullan
		button.setFocusPainted(false); // Tıklama efekti kaldır
		button.setBorderPainted(false); // Kenarlıkları kaldır
	    button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // El imleci
		
	}

}
