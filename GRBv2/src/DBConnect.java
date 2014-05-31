import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DBConnect {

	private Connection con;
	private Statement st;
	private ResultSet rs;

	public DBConnect() {
		// constructor to create dbconnector
		String username = "root";
		String password = "";
		String url = "jdbc:mysql://localhost/grb_berat";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, username, password);

		} catch (Exception ex) {
			System.out.println("Error: " + ex);
		}
	}

	public void insert(String npm, String nama, String berat) {
		// to insert data
		try {
			st = con.createStatement();
			float beratF = Float.parseFloat(berat);

			String insertSql = "INSERT berat VALUES(" + "'" + npm + "', '"
					+ nama + "', " + beratF + ")";
			int val = st.executeUpdate(insertSql);

		} catch (Exception ex) {
			System.out.println("Error: " + ex);
		}
	}

	public float cekTotal() {
		// to get total mass
		try {
			st = con.createStatement();

			String query = "SELECT * FROM berat";
			rs = st.executeQuery(query);
			float beratTotal = 0;
			while (rs.next()) {
				String nS = rs.getString("berat");
				float n = Float.parseFloat(nS);
				beratTotal += n;
			}

			return beratTotal;
		} catch (Exception ex) {
			System.out.println("Error: " + ex);
		}
		return 0;

	}

	public void update(String npm, String berat) {
		// to update table
		try {
			st = con.createStatement();
			float beratF = Float.parseFloat(berat);
			String updateSql = "UPDATE berat SET berat = berat + " + beratF
					+ " WHERE npm = '" + npm + "'";
			int val = st.executeUpdate(updateSql);

		} catch (Exception ex) {
			System.out.println("Error: " + ex);
		}
	}

	public void flush() {
		// to flush mass records
		try {
			st = con.createStatement();

			String updateSql = "UPDATE berat SET berat = 0";
			int val = st.executeUpdate(updateSql);

		} catch (Exception ex) {
			System.out.println("Error: " + ex);
		}
	}

	public ArrayList<String> cekNPM() {
		// to get all NPM
		try {
			st = con.createStatement();

			String query = "SELECT * FROM berat";
			rs = st.executeQuery(query);
			ArrayList<String> record = new ArrayList<String>();
			int i = 0;
			while (rs.next()) {
				String nS = rs.getString("npm");
				record.add(nS);
				i++;
			}
			System.out.println();

			return record;
		} catch (Exception ex) {
			System.out.println("Error: " + ex);
		}
		return null;
	}
}
