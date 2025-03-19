package View;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.JobAttributes;
import java.awt.TextField;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import javax.xml.crypto.Data;

import Helper.DatabaseConnection;

import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class OtobusKoltukSistemi extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Connection connection;
	private DatabaseConnection conn = new DatabaseConnection();
	private int seferID;
	
    
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OtobusKoltukSistemi frame = new OtobusKoltukSistemi(1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	
	// Yan yana koltuklar için cinsiyet verilerini tutacak bir Map
    private Map<Integer, String> koltukCinsiyetMap = new HashMap<>();

    // Yan koltukların bağlantılarını tutuyoruz (Örnek olarak 2-3, 4-5 gibi)
    private Map<Integer, Integer> yanKoltuklar = new HashMap<>();
	

    
	
	public OtobusKoltukSistemi(int seferID) { 
		this.seferID=seferID;
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 430, 720);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		 // Koltuk numaralarını ve yan koltukları tanımlıyoruz
        yanKoltuklar.put(2, 3); yanKoltuklar.put(3, 2);
        yanKoltuklar.put(5, 6); yanKoltuklar.put(6, 5);
        yanKoltuklar.put(8, 9); yanKoltuklar.put(9, 8);
        yanKoltuklar.put(11, 12); yanKoltuklar.put(12, 11);
        yanKoltuklar.put(15, 16); yanKoltuklar.put(16, 15);
        yanKoltuklar.put(18, 19); yanKoltuklar.put(19, 18);
        yanKoltuklar.put(21, 22); yanKoltuklar.put(22, 21);
        yanKoltuklar.put(24, 25); yanKoltuklar.put(25, 24);
        yanKoltuklar.put(26, 27); yanKoltuklar.put(27, 26);
        yanKoltuklar.put(27, 28); yanKoltuklar.put(28, 27);
        yanKoltuklar.put(28, 29); yanKoltuklar.put(29, 28);
        yanKoltuklar.put(29, 30); yanKoltuklar.put(30, 29);
				
		String[] Koltuklar2 = {"3", "6", "9", "12", "16", "19", "22", "25", "30"};
		int yoffset2 = 94;		
		for(String koltuk : Koltuklar2) {
			if(koltuk == "12" ) {
				JButton button = createButton2(koltuk, yoffset2);
				contentPane.add(button);
				yoffset2 += 116;
			} else {
				JButton button = createButton2(koltuk, yoffset2);
				contentPane.add(button);
				yoffset2 += 59;
			}
		}
		
		// Burada da yan yana olan butonların dinamiğini oluşturuyoruz
		String[] Koltuklar1 = {"2", "5", "8", "11", "15", "18", "21", "24", "29"};
		int yoffset1 = 94; // 2 numaralı butonun Y kordinatı		
		for(String koltuk : Koltuklar1) {
			if(koltuk == "11" ) {
				JButton button = createButton1(koltuk, yoffset1);
				contentPane.add(button);
				yoffset1 += 116;
			} else {
				JButton button = createButton1(koltuk, yoffset1);
				contentPane.add(button);
				yoffset1 += 59;
			}
		}
		
		// Butonları dinamik oluşturma
        String[] koltuklar = {"1", "4", "7", "10", "13", "14", "17", "20", "23", "26"};
        int yOffset = 94; // İlk butonun Y koordinatı
        for (String koltuk : koltuklar) {
            JButton button = createButton(koltuk, yOffset);
            contentPane.add(button);
            yOffset += 59; // Butonlar arasındaki mesafe
        }  
        
        String[] koltuklar4 = {"27"};
        int yOffset4 = 626;
        int yakinlik = 100;
        for (String koltuk : koltuklar4 ) {
        	JButton button = createButton3(koltuk, yOffset4, yakinlik);
        	contentPane.add(button);
        	yakinlik += 70;
        	
        }
	
        JButton btnNewButton1 = new JButton();
        btnNewButton1.setBorderPainted(false);  // Kenarları gizler
        btnNewButton1.setContentAreaFilled(false);  // Arka plan rengini gizler
        btnNewButton1.setFocusPainted(false); // Odak çerçevesini gizler
        btnNewButton1.setIcon(new ImageIcon("C:\\Users\\ilhan\\Desktop\\sofor.png"));
        btnNewButton1.setHorizontalTextPosition(SwingConstants.CENTER);
        btnNewButton1.setVerticalTextPosition(SwingConstants.CENTER);
        btnNewButton1.setBackground(Color.WHITE);
        btnNewButton1.setBounds(28, 20, 70, 49);
		btnNewButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton1.setBounds(28, 20, 70, 49);
		
        btnNewButton1.setEnabled(false);
        btnNewButton1.setEnabled(true);
		
		contentPane.add(btnNewButton1);
		contentPane.setVisible(true);
		
		try {
            connection = DriverManager.getConnection("jdbc:mariadb://localhost:****/seferyönetim?user=****&password=****"); // DB bağlantısını kurun
            populateSeats(seferID);
       } catch (SQLException e) {
           e.printStackTrace();
           JOptionPane.showMessageDialog(null, "Veritabanına bağlanırken bir hata oluştu.");
       }
		
		setVisible(true);
	}
		
	private void cinsiyet_kayit(String cinsiyet) {
	    String query = "INSERT INTO koltuk_cinsiyet (koltuk_cinsiyet) VALUES (?)";
	    try (PreparedStatement statement = connection.prepareStatement(query)) {
	        statement.setString(1, cinsiyet);
	        statement.executeUpdate(); // Veritabanına ekle
	        JOptionPane.showMessageDialog(null, "Cinsiyet veritabanına kaydedildi: " + cinsiyet);
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Veri kaydedilemedi.");
	    }
	}
	
	//Dinamik buton oluşturma ve olayları bağlama fonksiyonu
	private JButton createButton(String koltukNo, int yOffset) {
	    JButton button = new JButton(koltukNo);
	    button.setFont(new Font("Tahoma", Font.BOLD, 11));
	    button.setBounds(28, yOffset, 57, 42);
	    button.setName(koltukNo);
	    button.setOpaque(false); // Butonu şeffaf yapmak için
	    button.setBorderPainted(false);  // kenarları gizler
	    button.setContentAreaFilled(false);  // Arka plan rengini gizler
	    button.setFocusPainted(false); // Odak çerçevesini gizler
	    button.setOpaque(false);
	    button.setIcon(new ImageIcon("C:\\Users\\ilhan\\Desktop\\bosKoltuk.png"));
	    button.setHorizontalTextPosition(SwingConstants.CENTER);
	    button.setVerticalTextPosition(SwingConstants.CENTER);
	    button.setContentAreaFilled(true);
	        

	    button.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            koltuk_secimi(button);
	            if(isSeatTaken(koltukNo , button)) {
	            	return;
	            } else {
	            	button.setIcon(new ImageIcon("C:\\Users\\ilhan\\Desktop\\bosKoltuk.png"));
	            	}
	        }
	    });
	    return button;
	}
	private JButton createButton3(String koltukNo, int yOffset4, int yakinlik) {
	    JButton button = new JButton(koltukNo);
	    button.setFont(new Font("Tahoma", Font.BOLD, 11));
		button.setBounds(yakinlik, yOffset4, 57, 42);
	    button.setName(koltukNo);
	    button.setOpaque(false); // Butonu şeffaf yapmak için
	    button.setBorderPainted(false);  // kenarları gizler
	    button.setContentAreaFilled(false);  // Arka plan rengini gizler
	    button.setFocusPainted(false); // Odak çerçevesini gizler
	    button.setOpaque(false);
	    button.setIcon(new ImageIcon("C:\\Users\\ilhan\\Desktop\\bosKoltuk.png"));
	    button.setHorizontalTextPosition(SwingConstants.CENTER);
	    button.setVerticalTextPosition(SwingConstants.CENTER);
	    button.setContentAreaFilled(true);
	    button.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            koltuk_secimi(button);
	            if(isSeatTaken(koltukNo , button)) {
	            	return;
	            } else {
	            	button.setIcon(new ImageIcon("C:\\Users\\ilhan\\Desktop\\bosKoltuk.png"));
	            	}
	        }
	    });
	    return button;
	}
	private JButton createButton1(String koltukNo, int yoffset1) {
	    JButton button = new JButton(koltukNo);
	    button.setFont(new Font("Tahoma", Font.BOLD, 11));
	    button.setBounds(254, yoffset1, 57, 42);
	    button.setName(koltukNo);
	    button.setOpaque(false); // Butonu şeffaf yapmak için
	    button.setBorderPainted(false);
	    button.setContentAreaFilled(false);
	    button.setFocusPainted(false);
	    button.setOpaque(false);
	    button.setIcon(new ImageIcon("C:\\Users\\ilhan\\Desktop\\bosKoltuk.png"));
	    button.setHorizontalTextPosition(SwingConstants.CENTER);
	    button.setVerticalTextPosition(SwingConstants.CENTER);
	    button.setContentAreaFilled(true);
	    button.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            koltuk_secimi(button);
	            if(isSeatTaken(koltukNo , button)) {
	            	return;
	            } else {
	            	button.setIcon(new ImageIcon("C:\\Users\\ilhan\\Desktop\\bosKoltuk.png"));
	            	}
	        }
	    });
	    return button;
	}
	
	
	private JButton createButton2(String koltukNo, int yoffset2) {
	    JButton button = new JButton(koltukNo);
	    button.setFont(new Font("Tahoma", Font.BOLD, 11));
	    button.setBounds(322, yoffset2, 57, 42);
	    button.setName(koltukNo);
	    button.setOpaque(false); // Butonu şeffaf yapmak için
	    button.setBorderPainted(false);
	    button.setContentAreaFilled(false);
	    button.setFocusPainted(false);
	    button.setOpaque(false);
	    button.setIcon(new ImageIcon("C:\\Users\\ilhan\\Desktop\\bosKoltuk.png"));
	    button.setHorizontalTextPosition(SwingConstants.CENTER);
	    button.setVerticalTextPosition(SwingConstants.CENTER);
	    button.setContentAreaFilled(true);
	    button.setBackground(Color.WHITE);
	    button.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            koltuk_secimi(button);
	            if(isSeatTaken(koltukNo , button)) {
	            	return;
	            } else {
	            	button.setIcon(new ImageIcon("C:\\Users\\ilhan\\Desktop\\bosKoltuk.png"));
	            	}
	        }
	    });
	    return button;
	}
	
	//Yan koltuğun cinsiyetinin çelişip çelişmediğini kontrol eden fonksiyon
	private boolean cinsiyet_kontrol(String koltukNo, String gender) {
	 int koltukNumarasi = Integer.parseInt(koltukNo);
	 if (yanKoltuklar.containsKey(koltukNumarasi)) {
	     int yanKoltukNo = yanKoltuklar.get(koltukNumarasi);
	     if (koltukCinsiyetMap.containsKey(yanKoltukNo)) {
	         // Eğer yan koltuk zaten dolu ve cinsiyet farklıysa
	         return !koltukCinsiyetMap.get(yanKoltukNo).equals(gender);
	     }
	 }
	 return false;
	}




	
	
	// Koltuk seçimi işlemini gerçekleştiren fonksiyon
private void koltuk_secimi(JButton button) {
	    String koltukNo = button.getText();
	    String[] options = {"Erkek", "Kadın"};
	    Component frame = null;

	    try {
	        if (isSeatTaken(koltukNo, button)) {
	            JOptionPane.showMessageDialog(frame, "Bu koltuk alınmış. Lütfen farklı bir koltuk alınız.");
	            return;
	        }

	        int secim = JOptionPane.showOptionDialog(frame,
	                "Cinsiyetinizi seçin:",
	                "Cinsiyet Seçimi",
	                JOptionPane.DEFAULT_OPTION,
	                JOptionPane.INFORMATION_MESSAGE,
	                null, options, options[0]);

	        if (secim == JOptionPane.CLOSED_OPTION) {
	            return;
	        }

	        String cinsiyet = (secim == 0) ? "Erkek" : "Kadin";

	        if (cinsiyet_kontrol(koltukNo, cinsiyet)) {
	            JOptionPane.showMessageDialog(frame, "Yan koltuk farklı cinsiyetli, oturamazsınız. Lütfen başka bir koltuk seçin.");
	            return;
	        }

	        JOptionPane.showMessageDialog(frame, "Koltuğu bir " + cinsiyet.toLowerCase() + " aldı.");
	        button.setBorderPainted(false);
	        button.setContentAreaFilled(false);
	        button.setFocusPainted(false);
	        button.setIcon(new ImageIcon(cinsiyet.equals("Erkek") ? "C:\\Users\\ilhan\\Desktop\\koltukErkek.png" : "C:\\Users\\ilhan\\Desktop\\koltukKadın.png"));
	        button.setBackground(Color.WHITE);
	        koltukCinsiyetMap.put(Integer.parseInt(koltukNo), cinsiyet);
	        updateSeatStatus(koltukNo, cinsiyet);
	        koltuk_musteriSecimi(button);
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(frame, "Bir hata oluştu: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
	        e.printStackTrace();
	    }
	}


	// Koltuğun alınıp alınmadığını kontrol etme
private boolean isSeatTaken(String koltukNo, JButton button) {
    try {
        Connection con = DatabaseConnection.getConnection();
        // Koltuk numarasına ve sefer_id'ye göre koltuk durumunu sorgulama
        PreparedStatement ps = con.prepareStatement("SELECT koltuk_durumu, koltuk_cinsiyet, sefer_id FROM yolcular WHERE koltuk_numara = ? AND sefer_id = ?");
        ps.setString(1, koltukNo);  // Koltuk numarasını parametre olarak ayarladık
        ps.setInt(2, this.seferID); // Geçerli sefer_id'yi parametre olarak ayarlıyoruz
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            String durum = rs.getString("koltuk_durumu");  // Koltuk durumunu alıyoruz
            String cinsiyet = rs.getString("koltuk_cinsiyet");
            if (durum.equals("alindi")) {
                // Koltuk alındıysa, cinsiyetine göre buton rengini ayarla
                button.setBackground(cinsiyet.equals("Erkek") ? Color.BLUE : Color.PINK);
                return true; // Koltuk alınmış
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false; // Koltuk alınmamışsa false döner
}


	// Koltuk durumunu güncelleme
	private void updateSeatStatus(String koltukNo, String cinsiyet) {
	    try {
	        Connection con = DatabaseConnection.getConnection();
	        // Koltuğun durumunu güncelleyen sorgu
	        PreparedStatement ps = con.prepareStatement("UPDATE yolcular SET koltuk_durumu = 'alindi', koltuk_cinsiyet = ? WHERE koltuk_numara = ?");
	        ps.setString(1, cinsiyet);  // Durum parametresi (alındı veya boş gibi)
	        ps.setString(2, koltukNo);// Koltuk numarasını parametre olarak ayarlıyoruz
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	// Veritabanından koltukları dolduran metod
	private void populateSeats(int seferId) {
	    try {
	        Connection con = DatabaseConnection.getConnection();
	        PreparedStatement ps = con.prepareStatement(
	            "SELECT koltuk_numara, koltuk_durumu, koltuk_cinsiyet FROM yolcular WHERE sefer_id = ?"
	        );
	        ps.setInt(1, seferId); // Sefer ID'yi sorguya bağla

	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            String koltukNo = rs.getString("koltuk_numara");
	            String durum = rs.getString("koltuk_durumu");
	            String cinsiyet = rs.getString("koltuk_cinsiyet");

	            if ("alindi".equals(durum)) {
	                JButton button = findButtonByNumber(koltukNo); // Koltuk numarasına göre butonu bulun
	                if (button != null) {
	                    if ("Erkek".equals(cinsiyet)) {
	                        button.setIcon(new ImageIcon("C:\\Users\\ilhan\\Desktop\\koltukErkek.png"));
	                    } else {
	                        button.setIcon(new ImageIcon("C:\\Users\\ilhan\\Desktop\\koltukKadın.png"));
	                    }
	                    koltukCinsiyetMap.put(Integer.parseInt(koltukNo), cinsiyet); // Cinsiyeti haritaya ekle
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	
	private void eski_koltuk_rengi(JButton button) {
		button.setIcon(new ImageIcon("C:\\Users\\ilhan\\Desktop\\bosKoltuk.png"));
		}
	
	// Koltuk numarasına göre butonu bulan metod
	private JButton findButtonByNumber(String koltukNo) {
	    for (Component component : contentPane.getComponents()) {
	        if (component instanceof JButton && component.getName() != null && component.getName().equals(koltukNo)) {
	            return (JButton) component;
	        }
	    }
	    return null; // Buton bulunamazsa null döner
	}
	
	
	// Koltuk seçimi sonrası müşteriden bilgi almak için profesyonel form
// Koltuk seçimi sonrası müşteriden bilgi almak için profesyonel form
	private void koltuk_musteriSecimi(JButton button) {
	    // Yeni bir JDialog oluştur
	    JDialog dialog = new JDialog();
	    dialog.setTitle("Koltuk Bilgi Girişi");
	    dialog.setSize(500, 400);
	    dialog.setLocationRelativeTo(null); // Ekran ortasına konumlandır
	    dialog.setModal(true); // Ana pencereyi bloke et
	    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

	    // Ana panel
	    JPanel panel = new JPanel(new GridBagLayout());
	    panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Kenar boşlukları
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.insets = new Insets(10, 10, 10, 10); // Bileşenler arasında boşluk
	    gbc.fill = GridBagConstraints.HORIZONTAL;

	    // Cinsiyeti alın
	    String koltukNo = button.getName();
	    String cinsiyet = koltukCinsiyetMap.get(Integer.parseInt(koltukNo)); // Cinsiyet bilgisini al

	    // Bileşenler
	    JLabel titleLabel = new JLabel("Koltuk No: " + koltukNo, JLabel.CENTER);
	    titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

	    JLabel tcLabel = new JLabel("TC Kimlik No:");
	    tcLabel.setFont(new Font("Arial", Font.PLAIN, 16));
	    JTextField tcField = new JTextField();
	    tcField.setFont(new Font("Arial", Font.PLAIN, 16));
	    tcField.setPreferredSize(new Dimension(200, 30));

	    JLabel isimLabel = new JLabel("İsim:");
	    isimLabel.setFont(new Font("Arial", Font.PLAIN, 16));
	    JTextField isimField = new JTextField();
	    isimField.setFont(new Font("Arial", Font.PLAIN, 16));
	    isimField.setPreferredSize(new Dimension(200, 30));

	    JLabel soyisimLabel = new JLabel("Soyisim:");
	    soyisimLabel.setFont(new Font("Arial", Font.PLAIN, 16));
	    JTextField soyisimField = new JTextField();
	    soyisimField.setFont(new Font("Arial", Font.PLAIN, 16));
	    soyisimField.setPreferredSize(new Dimension(200, 30));

	    JButton kaydetButton = new JButton("Kaydet");
	    kaydetButton.setFont(new Font("Arial", Font.PLAIN, 16));
	    JButton iptalButton = new JButton("İptal");
	    iptalButton.setFont(new Font("Arial", Font.PLAIN, 16));

	    // Bileşenleri ekle
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.gridwidth = 2;
	    panel.add(titleLabel, gbc);

	    gbc.gridwidth = 1;
	    gbc.gridy++;
	    gbc.gridx = 0;
	    panel.add(tcLabel, gbc);
	    gbc.gridx = 1;
	    panel.add(tcField, gbc);

	    gbc.gridy++;
	    gbc.gridx = 0;
	    panel.add(isimLabel, gbc);
	    gbc.gridx = 1;
	    panel.add(isimField, gbc);

	    gbc.gridy++;
	    gbc.gridx = 0;
	    panel.add(soyisimLabel, gbc);
	    gbc.gridx = 1;
	    panel.add(soyisimField, gbc);

	    gbc.gridy++;
	    gbc.gridx = 0;
	    gbc.gridwidth = 1;
	    panel.add(kaydetButton, gbc);
	    gbc.gridx = 1;
	    panel.add(iptalButton, gbc);

	    // Dialog'a paneli ekle
	    dialog.getContentPane().add(panel);

	    // Kaydet butonu tıklama işlemi
	    kaydetButton.addActionListener(e -> {
	        String tc_kimlik = tcField.getText();
	        String isim = isimField.getText();
	        String soyisim = soyisimField.getText();

	        // Boş alan kontrolü
	        if (tc_kimlik.isEmpty() || isim.isEmpty() || soyisim.isEmpty()) {
	            JOptionPane.showMessageDialog(dialog, "Lütfen tüm alanları doldurun!", "Hata", JOptionPane.ERROR_MESSAGE);
	        } else {
	            // Bilgileri yazdır veya veritabanına kaydet
	            System.out.println("TC Kimlik No: " + tc_kimlik);
	            System.out.println("İsim: " + isim);
	            System.out.println("Soyisim: " + soyisim);
	            System.out.println("Cinsiyet: " + cinsiyet); // Cinsiyeti yazdır

	            // Veritabanına kaydetme işlemi
	            try (Connection con = DatabaseConnection.getConnection()) {
	                String query = "INSERT INTO yolcular (tc_kimlik, isim, soyisim, koltuk_numara, koltuk_cinsiyet, sefer_id) VALUES (?, ?, ?, ?, ?, ?)";
	                PreparedStatement ps = con.prepareStatement(query);
	                ps.setString(1, tc_kimlik);
	                ps.setString(2, isim);
	                ps.setString(3, soyisim);
	                ps.setString(4, koltukNo); // Koltuk numarasını buton adından al
	                ps.setString(5, cinsiyet); // Cinsiyet bilgisini ekle
	                ps.setInt(6, this.seferID);
	                ps.executeUpdate();
	                JOptionPane.showMessageDialog(dialog, "Bilgiler başarıyla kaydedildi!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	                JOptionPane.showMessageDialog(dialog, "Bilgiler kaydedilirken bir hata oluştu."+ ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
	            }

	            // Dialog'u kapat
	            dialog.dispose();
	        }
	    });

	    // İptal butonu tıklama işlemi
	    iptalButton.addActionListener(e -> {
	        eski_koltuk_rengi(button);
	        dialog.dispose();
	    });

	    // Dialog'u görünür yap      
	    dialog.setVisible(true);
	}	

	
	
	}








	