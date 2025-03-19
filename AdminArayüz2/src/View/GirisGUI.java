package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Admin.MainAdmin;
import Admin.MainAdminImplementation;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JTabbedPane;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JSplitPane;
import javax.swing.JPasswordField;
import Helper.*;
import Model.Man;
import Model.Women;

public class GirisGUI extends JFrame {
    private JPanel w_pane;
    private JTextField fld_müşteriEposta;
    private JPasswordField fld_müşteriPass;
    private JButton btn_MüsteriGirişButton, btn_KayitOl;
    private JTextField fld_KayıtEposta;
    private JPasswordField fld_KayıtŞifre;
    private DatabaseConnection conn = new DatabaseConnection();
    private JTextField fld_kayıtIsım;
    private JTextField fld_kayıtSoyisim;

    
    public GirisGUI() {

    	
        setTitle("OTOBUS OTOMASYONU");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 570, 557);

        // Ana panel
        w_pane = new JPanel() {
        	private Image backgroundImage = new ImageIcon(new File("C:\\Users\\ilhan\\Desktop\\girisBG.jpg").getAbsolutePath()).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        w_pane.setBorder(null);
        w_pane.setLayout(null);
        setContentPane(w_pane);

        // Giriş Yap arayüzü oluşturma
        initializeLoginComponents();
        
        switchToLoginComponentsAdmin(); // Başlangıçta giriş bileşenleri gözükür
        

    }
    
    //Giriş Sekmesi
    private void initializeLoginComponents() {
        // Hoş geldin mesajı
        JLabel lblNewLabel = new JLabel("KARDEŞLER TURİZME HOSGELDİNİZ");
        lblNewLabel.setBounds(98, 150, 379, 44);
        lblNewLabel.setFont(new Font("Franklin Gothic Medium Cond", Font.BOLD, 21));
        lblNewLabel.setForeground(Color.BLACK);
        w_pane.add(lblNewLabel);

        // E-posta ve şifre etiketleri
        JLabel lblEposta = new JLabel("E-Posta");
        lblEposta.setFont(new Font("Franklin Gothic Medium Cond", Font.BOLD, 26));
        lblEposta.setForeground(Color.WHITE);
        lblEposta.setBounds(44, 216, 145, 44);
        w_pane.add(lblEposta);

        fld_müşteriEposta = new JTextField();
        fld_müşteriEposta.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 18));
        fld_müşteriEposta.setBounds(150, 223, 279, 37);
        fld_müşteriEposta.setBackground(Color.LIGHT_GRAY);
        fld_müşteriEposta.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.BLACK));
        w_pane.add(fld_müşteriEposta);

        JLabel lblSifre = new JLabel("Şifre");
        lblSifre.setFont(new Font("Franklin Gothic Medium Cond", Font.BOLD, 26));
        lblSifre.setForeground(Color.WHITE);
        lblSifre.setBounds(44, 270, 145, 44);
        w_pane.add(lblSifre);

        fld_müşteriPass = new JPasswordField();
        fld_müşteriPass.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 19));
        fld_müşteriPass.setBounds(150, 270, 279, 37);
        fld_müşteriPass.setBackground(Color.LIGHT_GRAY);
        fld_müşteriPass.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.BLACK));
        w_pane.add(fld_müşteriPass);

        // Kayıt Ol butonu
        btn_KayitOl = new JButton("Kayıt Ol");
        btn_KayitOl.setFont(new Font("Bauhaus 93", Font.BOLD, 19));
        btn_KayitOl.setForeground(Color.WHITE);
        btn_KayitOl.setBackground(Color.BLACK); 
        btn_KayitOl.setBorderPainted(false);
        btn_KayitOl.setContentAreaFilled(true);
        btn_KayitOl.setFocusPainted(false);
        btn_KayitOl.setOpaque(true);
        btn_KayitOl.setBounds(200, 380, 145, 53);
        btn_KayitOl.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btn_KayitOl.setBackground(new Color(144, 238, 144));
            }

            public void mouseExited(MouseEvent evt) {
                btn_KayitOl.setBackground(Color.BLACK);
            }
        });
        btn_KayitOl.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btn_KayitOl.setBackground(new Color(34, 139, 34)); // Daha koyu yeşil
            }
            public void mouseClicked(MouseEvent evt) {
                switchToRegisterComponents();
            }
        });
        btn_KayitOl.addMouseListener(new MouseAdapter() {
        	public void mouseEntered(MouseEvent e) {
        		btn_KayitOl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        	}
        	public void mouseExited(MouseEvent e) {
        		btn_KayitOl.setCursor(Cursor.getDefaultCursor());
        	}
		});
        w_pane.add(btn_KayitOl);
        
        JCheckBox showPassword = new JCheckBox("Şifreyi Göster");
        showPassword.setFont(new Font("Franklin Gothic Medium Cond", Font.BOLD, 10));
        showPassword.setBounds(440, 280, 100, 20);
        showPassword.setForeground(Color.WHITE);
        showPassword.setBackground(Color.BLACK);
        showPassword.setOpaque(false);
        showPassword.addActionListener(e -> {
            if (showPassword.isSelected()) {
                fld_müşteriPass.setEchoChar((char) 0);
            } else {
                fld_müşteriPass.setEchoChar('*');
            }
        });
        w_pane.add(showPassword);

        btn_MüsteriGirişButton = new JButton("Giriş Yap");
        btn_MüsteriGirişButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btn_MüsteriGirişButton.setBackground(new Color(144, 238, 144));
            }

            public void mouseExited(MouseEvent evt) {
                btn_MüsteriGirişButton.setBackground(Color.BLACK);
            }
        });
        btn_MüsteriGirişButton.addMouseListener(new MouseAdapter() {
        	public void mouseEntered(MouseEvent e) {
        		btn_MüsteriGirişButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        	}
        	public void mouseExited(MouseEvent e) {
        		btn_MüsteriGirişButton.setCursor(Cursor.getDefaultCursor());
        	}
		});
        btn_MüsteriGirişButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(fld_müşteriEposta.getText().length() == 0 || fld_müşteriPass.getText().length() == 0) {
					Helper.showMsg("fill");
				}
				else {
					try {
						Connection connect = DatabaseConnection.getConnection();
						Statement st = connect.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM musteriler");
						while(rs.next()) {
							if(fld_müşteriEposta.getText().equals(rs.getString("email")) && fld_müşteriPass.getText().equals(rs.getString("sifre"))) {
							String email = fld_müşteriEposta.getText();
							String password = new String(fld_müşteriPass.getPassword());
							
							if(isLoginValid(email, password)) {

								GuzergahSeferSecim guzergahsefersecim = new GuzergahSeferSecim();
								dispose();
							}
							else {
								JOptionPane.showMessageDialog(null, "Geçersiz eposta yada şifre girdiniz.");
							}
							}
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				
			}
		});
        btn_MüsteriGirişButton.setBorderPainted(false);
        btn_MüsteriGirişButton.setContentAreaFilled(true);
        btn_MüsteriGirişButton.setFocusPainted(true);
        btn_MüsteriGirişButton.setBackground(Color.BLACK);
        btn_MüsteriGirişButton.setOpaque(true);
        btn_MüsteriGirişButton.setFont(new Font("Bauhaus 93", Font.BOLD, 19));
        btn_MüsteriGirişButton.setForeground(Color.WHITE);
        btn_MüsteriGirişButton.setBounds(200, 320, 145, 53);
        w_pane.add(btn_MüsteriGirişButton);
        
    }

    //Kayıt Ol Sekmesi
    private void switchToRegisterComponents() {
        w_pane.removeAll();
        w_pane.repaint();
        
     // Kayıt ol bölümünde "Giriş Yap" yazısı ve butonu
        JLabel lblGirisYap = new JLabel("Zaten bir hesabınız var mı?");
        lblGirisYap.setFont(new Font("Franklin Gothic Medium Cond", Font.BOLD, 14));
        lblGirisYap.setForeground(Color.WHITE);
        lblGirisYap.setBounds(150, 400, 250, 30);
        w_pane.add(lblGirisYap);

        JButton btnGirisYap = new JButton("Giriş Yap");
        btnGirisYap.setFont(new Font("Bauhaus 93", Font.BOLD, 19));
        btnGirisYap.setForeground(Color.WHITE);
        btnGirisYap.setBackground(Color.BLACK);
        btnGirisYap.setBorderPainted(false);
        btnGirisYap.setContentAreaFilled(true);
        btnGirisYap.setFocusPainted(false);
        btnGirisYap.setOpaque(true);
        btnGirisYap.setBounds(200, 440, 145, 53);
        btnGirisYap.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btnGirisYap.setBackground(new Color(144, 238, 144));
            }

            public void mouseExited(MouseEvent evt) {
                btnGirisYap.setBackground(Color.BLACK);
            }
        });
        btnGirisYap.addMouseListener(new MouseAdapter() {
        	public void mouseEntered(MouseEvent e) {
        		btnGirisYap.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        	}
        	public void mouseExited(MouseEvent e) {
        		btnGirisYap.setCursor(Cursor.getDefaultCursor());
        	}
		});
        btnGirisYap.addActionListener(e -> {
            switchToLoginComponents(); // Giriş yap bileşenlerine geçiş yap
        });
        w_pane.add(btnGirisYap);

        
     // İsim alanı
        JLabel lblIsim = new JLabel("İsim");
        lblIsim.setFont(new Font("Franklin Gothic Medium Cond", Font.BOLD, 26));
        lblIsim.setForeground(Color.WHITE);
        lblIsim.setBounds(44, 110, 145, 44);
        w_pane.add(lblIsim);

        JTextField fld_kayıtIsım = new JTextField();
        fld_kayıtIsım.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 18));
        fld_kayıtIsım.setBounds(200, 120, 279, 37);
        fld_kayıtIsım.setBackground(Color.LIGHT_GRAY);
        fld_kayıtIsım.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.BLACK));
        w_pane.add(fld_kayıtIsım);

        // Soyisim alanı
        JLabel lblSoyisim = new JLabel("Soyisim");
        lblSoyisim.setFont(new Font("Franklin Gothic Medium Cond", Font.BOLD, 26));
        lblSoyisim.setForeground(Color.WHITE);
        lblSoyisim.setBounds(44, 164, 145, 44);
        w_pane.add(lblSoyisim);

        JTextField fld_kayıtSoyisim = new JTextField();
        fld_kayıtSoyisim.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 18));
        fld_kayıtSoyisim.setBounds(200, 170, 279, 37);
        fld_kayıtSoyisim.setBackground(Color.LIGHT_GRAY);
        fld_kayıtSoyisim.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.BLACK));
        w_pane.add(fld_kayıtSoyisim);


        // Yeni alanlar
        JLabel lblYeniEposta = new JLabel("Yeni E-Posta");
        lblYeniEposta.setFont(new Font("Franklin Gothic Medium Cond", Font.BOLD, 26));
        lblYeniEposta.setForeground(Color.WHITE);
        lblYeniEposta.setBounds(44, 216, 145, 44);
        w_pane.add(lblYeniEposta);

        JTextField fld_KayıtEposta = new JTextField();
        fld_KayıtEposta.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 18));
        fld_KayıtEposta.setBounds(200, 223, 279, 37);
        fld_KayıtEposta.setBackground(Color.LIGHT_GRAY);
        fld_KayıtEposta.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.BLACK));
        w_pane.add(fld_KayıtEposta);

        JLabel lblYeniSifre = new JLabel("Yeni Şifre");
        lblYeniSifre.setFont(new Font("Franklin Gothic Medium Cond", Font.BOLD, 26));
        lblYeniSifre.setForeground(Color.WHITE);
        lblYeniSifre.setBounds(44, 270, 145, 44);
        w_pane.add(lblYeniSifre);

        JPasswordField fld_KayıtŞifre = new JPasswordField();
        fld_KayıtŞifre.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 19));
        fld_KayıtŞifre.setBounds(200, 270, 279, 37);
        fld_KayıtŞifre.setBackground(Color.LIGHT_GRAY);
        fld_KayıtŞifre.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.BLACK));
        w_pane.add(fld_KayıtŞifre);

        // Kayıt ol butonu
        JButton btnKayitOl = new JButton("Kayıt Ol");
        btnKayitOl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// kullanıcıdan aldığın bilgileri kontrol et
				String email = fld_KayıtEposta.getText();
				String password = fld_KayıtŞifre.getText();
				String isim = fld_kayıtIsım.getText();
				String soyisim = fld_kayıtSoyisim.getText();
				
		        // Geçerli e-posta adresi kontrolü
		        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
		            JOptionPane.showMessageDialog(null, "Lütfen geçerli bir e-posta adresi girin.", "Hata", JOptionPane.ERROR_MESSAGE);
		            return; 
		        }
				
				if(isEmailExists(email)) {
					return;
				}
				
				// boş alan kontrolü
				if (email.length() == 0 || password.length() == 0 || isim.length() == 0 || soyisim.length() == 0 ) {
					Helper.showMsg("fill");  // kullanıcıya eksik alanları bildirip mesaj gönderir
				} else {
					// VEritabanına kayıt işlemi yap
					registerUserToDatabase(email, password, isim, soyisim); 
		            switchToLoginComponents(); // Giriş ekranına geçiş yap
		            fld_KayıtEposta.setText("");
		            fld_KayıtŞifre.setText("");
		            fld_kayıtIsım.setText("");
		            fld_kayıtSoyisim.setText("");		            
				}
				
			}
		});
        btnKayitOl.setFont(new Font("Bauhaus 93", Font.BOLD, 19));
        btnKayitOl.setForeground(Color.WHITE);
        btnKayitOl.setBackground(Color.BLACK);
        btnKayitOl.setBorderPainted(false);
        btnKayitOl.setContentAreaFilled(true);
        btnKayitOl.setFocusPainted(false);
        btnKayitOl.setOpaque(true);
        btnKayitOl.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btnKayitOl.setBackground(new Color(144, 238, 144));
            }

            public void mouseExited(MouseEvent evt) {
                btnKayitOl.setBackground(Color.BLACK);
            }
        });
        btnKayitOl.addMouseListener(new MouseAdapter() {
        	public void mouseEntered(MouseEvent e) {
        		btnKayitOl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        	}
        	public void mouseExited(MouseEvent e) {
        		btnKayitOl.setCursor(Cursor.getDefaultCursor());
        	}
		});
        btnKayitOl.setBounds(200, 320, 145, 53);
        w_pane.add(btnKayitOl);
        
        w_pane.revalidate();
    }
    
    private boolean isLoginValid(String email, String password) {
        // Veritabanına bağlanarak email ve şifre doğrulaması yapmalısınız
        // Örneğin, burada basit bir kontrol yapalım:
        return email.equals(fld_müşteriEposta.getText()) && password.equals(fld_müşteriPass.getText());
    }
    
    private void registerUserToDatabase(String email, String password, String isim, String soyisim) {
        // Veritabanı bağlantısı için gerekli bilgiler
        String url = "jdbc:mariadb://localhost:****/seferyönetim"; // Veritabanı bağlantı URL'si
        String username = "****";  // Veritabanı kullanıcı adı
        String dbPassword = "****"; // Veritabanı şifresi

        // Veritabanı bağlantısı
        try (Connection connection = DriverManager.getConnection(url, username, dbPassword)) {
            // SQL sorgusu (kullanıcıyı veritabanına eklemek için)
            String sql = "INSERT INTO musteriler (email, sifre, isim, soyisim) VALUES (?, ?, ?, ?)";
            
            // PreparedStatement ile SQL sorgusu oluşturuluyor
            try (PreparedStatement pst = connection.prepareStatement(sql)) {
                // Parametreleri ayarla
                pst.setString(1, email);
                pst.setString(2, password);
                pst.setString(3, isim);
                pst.setString(4, soyisim);
                
                // Sorguyu çalıştır
                int rowsAffected = pst.executeUpdate();
                
                // Kayıt işlemi başarılıysa
                if (rowsAffected > 0) {
                    Helper.showMsg("Kayıt başarılı!"); // Kayıt başarılı mesajı
                } else {
                    Helper.showMsg("Bir hata oluştu!"); // Kayıt hatası mesajı
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Helper.showMsg("Veritabanı bağlantısı sırasında bir hata oluştu.");
        }
    }
    
    private void switchToLoginComponents() {
        w_pane.removeAll(); // Tüm bileşenleri temizle
        initializeLoginComponents(); // Giriş yap bileşenlerini yeniden yükle
        switchToLoginComponentsAdmin(); // Admin butonunu yeniden ekle
        w_pane.revalidate();
        w_pane.repaint();
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GirisGUI girisGUI = new GirisGUI();
            girisGUI.setVisible(true); // Pencereyi görünür yap
        });
    }
    
    // admin giriş alnlarını gösterecek kısım
    private void initializeAdminCompontes() {
    	//Admin kullanıcı adı kısmı
    	JLabel lblAdminUser = new JLabel("Kullanıcı Adı");
    	lblAdminUser.setFont(new Font("Franklin Gothic Medium Cond", Font.BOLD, 26));
    	lblAdminUser.setForeground(Color.WHITE);
    	lblAdminUser.setBounds(44, 150, 145, 44);
    	w_pane.add(lblAdminUser);
    	
    	JTextField fld_AdminUser = new JTextField();
    	fld_AdminUser.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 18 ));
    	fld_AdminUser.setBounds(200, 150, 279, 37);
    	fld_AdminUser.setBackground(Color.LIGHT_GRAY);
    	fld_AdminUser.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.BLACK));
    	w_pane.add(fld_AdminUser);
    	
    	// Admin şifre kısmı
    	JLabel lblAdminPass = new JLabel("Şifre");
    	lblAdminPass.setFont(new Font("Franklin Gothic Medium Cond", Font.BOLD, 26));
    	lblAdminPass.setForeground(Color.WHITE);
    	lblAdminPass.setBounds(44, 210, 145, 44);
    	w_pane.add(lblAdminPass);
    	
    	JPasswordField fld_AdminPass = new JPasswordField();
    	fld_AdminPass.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 19));
    	fld_AdminPass.setBounds(200, 210, 279, 37);
    	fld_AdminPass.setBackground(Color.LIGHT_GRAY);
    	fld_AdminPass.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.BLACK));
    	w_pane.add(fld_AdminPass);
    	
    	//Admin giriş butonu
    	JButton btn_AdminLogin = new JButton("Giriş Yap");
    	btn_AdminLogin.setFont(new Font("Bauhaus 93", Font.BOLD, 19));
    	btn_AdminLogin.setForeground(Color.WHITE);
    	btn_AdminLogin.setBackground(Color.BLACK);
    	btn_AdminLogin.setBorderPainted(false);
    	btn_AdminLogin.setContentAreaFilled(true);
    	btn_AdminLogin.setFocusPainted(false);
    	btn_AdminLogin.setOpaque(true);
    	btn_AdminLogin.setBounds(200, 270, 145, 53);
    	btn_AdminLogin.addMouseListener(new MouseAdapter() {
    		public void mouseEntered(MouseEvent evt) {
    			btn_AdminLogin.setBackground(new Color(144, 238, 144));
    		}
    		public void mouseExited(MouseEvent evt) {
    			btn_AdminLogin.setBackground(Color.BLACK);
    		}
		});
    	btn_AdminLogin.addMouseListener(new MouseAdapter() {
        	public void mouseEntered(MouseEvent e) {
        		btn_AdminLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        	}
        	public void mouseExited(MouseEvent e) {
        		btn_AdminLogin.setCursor(Cursor.getDefaultCursor());
        	}
		});
    	btn_AdminLogin.addActionListener(e-> {
    		String username = fld_AdminUser.getText();
    		String password = new String(fld_AdminPass.getPassword());
    		// burada admin doğrulama işlemi yapıyoruz
			if (username.isEmpty() || password.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Kullanıcı Adı Ve Şifre Boş Bırakılamaz", "Hata", JOptionPane.ERROR_MESSAGE);
				return;
			}

			try (Connection connect = DatabaseConnection.getConnection()) {
				String query = "SELECT * FROM admin WHERE admin_isim = ? AND admin_sifre = ?";
				PreparedStatement state = connect.prepareStatement(query);
				state.setString(1, username);
				state.setString(2, password);
				
				ResultSet rs = state.executeQuery();

				if (rs.next()) {
						
						MainAdmin mainAdmin = new MainAdminImplementation();
						mainAdmin.setVisible(true);
						dispose();
				} else {
					// Kullanıcı adı veya şifre hatalı
					JOptionPane.showMessageDialog(null, "Kullanıcı Adı Veya Şifre Hatalı !","Hata", JOptionPane.ERROR_MESSAGE);                    }
			} catch (SQLException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "Veritabanı Hatası : " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);                }
		
			fld_AdminUser.setText("");
			fld_AdminPass.setText("");
		
    	});
    	w_pane.add(btn_AdminLogin);
    	
    	// Geri Dön Butonu
        JButton btn_BackToHome = new JButton("Ana Sayfaya Dön");
        btn_BackToHome.setBounds(200, 350, 145, 53);
        btn_BackToHome.setFont(new Font("Bauhaus 93", Font.BOLD, 13));
        btn_BackToHome.setForeground(Color.WHITE);
        btn_BackToHome.setBackground(Color.BLACK);
        btn_BackToHome.setBorderPainted(false);
        btn_BackToHome.setContentAreaFilled(true);
        btn_BackToHome.setFocusPainted(false);
        btn_BackToHome.setOpaque(true);

        btn_BackToHome.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btn_BackToHome.setBackground(new Color(144, 238, 144));
            }

            public void mouseExited(MouseEvent evt) {
                btn_BackToHome.setBackground(Color.BLACK);
            }
        });

        btn_BackToHome.addActionListener(e -> switchToLoginComponents()); // Ana sayfa bileşenlerini yükle
        w_pane.add(btn_BackToHome);
    }
    
    	
    
    // Admin giriş alnlarını kontrol eden metodu ekleyin
    private boolean isAdminValid(String username, String password) {
    	// Burada admin girişi için basit bir kontrol yapıyoruz
    	return "admin".equals(username) && "adminpass".equals(password);
    }
    
    
    
    // burada da giriş yap butonuna admin paneli açma işlevi ekleyin
    private void switchToLoginComponentsAdmin() {
        // Admin butonu
        JButton btn_Admin = new JButton("ADMİN");
        btn_Admin.setFont(new Font("Franklin Gothic Medium Cond", Font.BOLD, 9));
        btn_Admin.setBorderPainted(true);
        btn_Admin.setContentAreaFilled(true);
        btn_Admin.setFocusPainted(false);

        // Resmi yükleme ve ölçeklendirme
        String imagePath = "C:/Users/ilhan/Desktop/admin.png"; // Resmin tam yolu
        ImageIcon icon = new ImageIcon(imagePath);
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        btn_Admin.setIcon(new ImageIcon(scaledImg));

        btn_Admin.setHorizontalTextPosition(SwingConstants.RIGHT);
        btn_Admin.setBackground(Color.WHITE);
        btn_Admin.setForeground(Color.BLACK);
        btn_Admin.setBounds(440, 30, 100, 30);

        // Fare olayları
        btn_Admin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent event) {
                btn_Admin.setBackground(new Color(144, 238, 144));
                btn_Admin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent event) {
                btn_Admin.setBackground(Color.WHITE);
                btn_Admin.setCursor(Cursor.getDefaultCursor());
            }
        });

        // Admin bileşenlerini yükleme
        btn_Admin.addActionListener(e -> {
            w_pane.removeAll();
            initializeAdminCompontes(); // Admin bileşenlerini yükler
            w_pane.revalidate();
            w_pane.repaint();
        });

        w_pane.add(btn_Admin); // Butonu pencereye ekle
    }

    
    // E-posta adresinin veritabanında mevcut olup olmadığını kontrol eden fonksiyon
    public static boolean isEmailExists(String email) {
        String query = "SELECT COUNT(*) FROM musteriler WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    if (rs.getInt(1) > 0) {
                        // E-posta adresi mevcutsa uyarı mesajı göster
                        JOptionPane.showMessageDialog(null, "Bu e-posta adresi zaten kayıtlı!", "Uyarı", JOptionPane.WARNING_MESSAGE);
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // E-posta adresi mevcut değilse false döner
    }

}
