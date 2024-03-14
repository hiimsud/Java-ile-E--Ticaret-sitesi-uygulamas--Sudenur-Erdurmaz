package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


/**
 * @author busrayral
 */


public class LogOutButton extends JButton implements ActionListener{
	
	JMenuBar menuBar2 = new JMenuBar();


    public void initializeMenubar2() {

        JMenu menu2 = new JMenu("MENU");

        menuBar2.add(menu2);

        JMenuItem homePage = new JMenuItem("Çıkış Yap");

        menu2.add(homePage);


        homePage.addActionListener(e -> new LogOutButton().actionPerformed(e));
    }

    public LogOutButton () {
        super("Çıkış Yap");
        setSize(50, 50);
        setBorder(BorderFactory.createEmptyBorder());
    }

    

	@Override
    public void actionPerformed(ActionEvent e) {
        JFrame frame = new Kullanici();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 312);
        frame.setVisible(true);
    }

}
