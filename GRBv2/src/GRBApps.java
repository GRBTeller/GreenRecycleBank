import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.swing.JFrame;

public class GRBApps {

	private static ArrayList<String> recordNPM = new ArrayList<String>();
	private static float threshold = (float) .1;
	private static SerialTest serial = new SerialTest();
	private static float result = 0;
	private static int count = 0;
	private static boolean state = true;
	private static final float incThreshold = (float) .1;


	public static void main(String[] args) {
		serial.run();
		

	}

	public static void tampilWindow(String berat) throws IOException {
		// untuk menampilkan frame yang menerima input nama dan npm, disertakan
		// field berisi berat yang tidak bisa diedit
		DataField dataField = new DataField(berat);
		dataField.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/*
		Toolkit tk = Toolkit.getDefaultToolkit();  
		int xSize = ((int) tk.getScreenSize().getWidth());  
		int ySize = ((int) tk.getScreenSize().getHeight());  
		dataField.setSize(xSize, ySize);
		*/
		dataField.setSize(600, 600);
		dataField.setVisible(true);
	}

	public static void setData(String npm, String nama, String berat) {
		if (npm.equals(""))
			try {
				GRBApps.tampilWindow(berat);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else {
			DBConnect connect = new DBConnect(); // inisialisasi object
													// dbconnect
			recordNPM = connect.cekNPM(); // mengambil semua npm yang ada di db

			boolean state = true;
			for (int i = 0; i < recordNPM.size(); i++) {
				// kalau npm sudah ada, maka db akan diupdate
				if (npm.equals(recordNPM.get(i))) {
					connect.update(npm, berat);
					state = false;
				}
			}
			// kalau npm belum ada, akan membuat record baru pada db
			if (state == true)
				connect.insert(npm, nama, berat);

			cekThreshold();
		}

	}

	public static void cekThreshold() {
		DBConnect connect = new DBConnect();
		float total = connect.cekTotal();
		System.out.println(total);
		if (total > threshold) {
			sms();
			threshold = (int) (Math.ceil(total / incThreshold) * incThreshold);
		}
		if (threshold > 100) {
			connect.flush();
			threshold = incThreshold;
		}
	}

	public static void setBerat(float asFloat) {
		
		// TODO Auto-generated method stub
		if (result == asFloat)
			count++;
		else {
			result = asFloat;
			count = 1;
		}
		if (count == 3) {
			state = false;
			String beratSerial = Float.toString(asFloat);
			try {
				tampilWindow(beratSerial);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			serial.kill();
		}

	}

	public static void sms() {
		try {
			URL smsGateway = new URL("http://localhost/grb/sms.php");
			URLConnection sg = smsGateway.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					sg.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null)
				System.out.println(inputLine);
			in.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
