import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

class background extends JComponent {

	Image i;

	public background(Image i) {

		this.i = i;

	}

	public void paintComponent(Graphics g) {

		g.drawImage(i, 0, 0, null);

	}

}

public class DataField extends JFrame {
	// membuat frame dgn field nama, npm, berat
	private JTextField namaField;
	private JTextField npmField;
	private JTextField beratField;
	private String berat;

	public DataField(String b) throws IOException {

		this.setTitle("GRB Form");

		BufferedImage bf = ImageIO.read(new File(
				"C:\\Users\\Asus\\workspace\\GRBv2\\src\\10480948_m.jpg"));

		this.setContentPane(new background(bf));

		namaField = new JTextField(20);
		npmField = new JTextField(20);
		beratField = new JTextField(b);
		beratField.setEditable(false);

		JPanel panel = new JPanel();

		panel.setSize(300, 150);

		JLabel namaLabel = new JLabel("Nama :");
		JLabel npmLabel = new JLabel("NPM :");
		JLabel beratLabel = new JLabel("Berat :");
		JButton btn1 = new JButton("OK");
		panel.add(namaLabel);
		panel.add(namaField);
		panel.add(npmLabel);
		panel.add(npmField);
		panel.add(beratLabel);
		panel.add(beratField);
		panel.add(btn1);
		
		panel.setBounds(150, 100, 300, 150);
		
		panel.setBackground(new Color(100, 100, 255));

		add(panel);

		ButtonHandler handler = new ButtonHandler();
		btn1.addActionListener(handler);

		berat = b;

	}

	private class ButtonHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String nama = namaField.getText();
			String npm = npmField.getText();
			DataField.this.dispose(); // untuk mengclose frame
			GRBApps.setData(npm, nama, berat);
		}

	}

}
