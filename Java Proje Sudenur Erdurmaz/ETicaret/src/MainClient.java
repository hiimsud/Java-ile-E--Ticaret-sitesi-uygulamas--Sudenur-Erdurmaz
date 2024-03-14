import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import client.Kullanici;
import java.awt.Font;


/**
 * @author busrayral
 */
public class MainClient extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainClient frame = new MainClient();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public MainClient() {
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 200, 500, 312); //düzenlendi
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(212, 212, 212));
		panel.setBounds(240, 0, 243, 273);
		contentPane.add(panel);
		panel.setLayout(null);
		
		
		JButton btn_kullanici = new JButton("KULLANICI");
		btn_kullanici.setBackground(new Color(228, 228, 228));
		btn_kullanici.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btn_kullanici.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFrame frame = new Kullanici(); 
				frame.setVisible(true);
				
			}
		});
		btn_kullanici.setForeground(new Color(74, 74, 74));
		btn_kullanici.setBounds(42, 97, 150, 66);
		panel.add(btn_kullanici);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 0, 160));
		panel_1.setBounds(0, 0, 242, 273);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JButton btn_admin = new JButton("ADMİN");
		btn_admin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Administrator.adminpanel frame= new Administrator.adminpanel();
                frame.setVisible(true);
			}
		});
		btn_admin.setBackground(new Color(228, 228, 228));
		btn_admin.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btn_admin.setForeground(new Color(74, 74, 74));
		btn_admin.setBounds(42, 97, 150, 66);
		panel_1.add(btn_admin);
	}

}
