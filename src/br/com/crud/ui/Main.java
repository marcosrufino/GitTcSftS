package br.com.crud.ui;

import totalcross.sys.Settings;
import totalcross.ui.MainWindow;
import totalcross.ui.UIColors;
import totalcross.ui.gfx.Color;

public class Main extends MainWindow {
	static {
		Settings.applicationId = "tapi";
	}

	public Main(){
		super("SoftSite - CRUD", VERTICAL_GRADIENT);
	}
	
	public void initUI(){
		UIColors.controlsBack = Color.WHITE;
		setBackColor(Color.BRIGHT);
		setUIStyle(Settings.Android);
		swap(new ListarClientes());
	}
}