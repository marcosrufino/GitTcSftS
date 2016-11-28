package br.com.crud.conection;

import totalcross.sql.Connection;
import totalcross.sql.DriverManager;
import totalcross.sql.Statement;
import totalcross.sys.Convert;
import totalcross.sys.Settings;
import totalcross.ui.dialog.MessageBox;

public class ConectionFactory {

	private Connection dbcon;

	public Statement getConection() {
		try {
			dbcon = DriverManager.getConnection("jdbc:sqlite:" + Convert.appendPath(Settings.appPath, "crud.db"));
			Statement st = dbcon.createStatement();
			st.execute("create table if not exists pessoas (id INTEGER PRIMARY KEY,nome varchar, tel varchar , end varchar)");
			return st;
		}
		catch (Exception e) {
			MessageBox.showException(e, true);
			return null;
		}
	}

}
