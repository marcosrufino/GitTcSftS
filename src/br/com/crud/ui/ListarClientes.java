package br.com.crud.ui;

import java.sql.SQLException;
import java.util.ArrayList;

import br.com.crud.dao.ClienteDao;
import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Grid;
import totalcross.ui.Label;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.gfx.Color;

public class ListarClientes extends Container {
	Grid grid;
	Button btnAdd, btnRemove, btnChange;

	public void initUI() {
		super.initUI();
		setBackColor(Color.WHITE);
		add(btnAdd = new Button("Novo"), LEFT+5, TOP + 2);
		add(btnChange = new Button("Editar"), AFTER + 2, SAME);
		add(btnRemove = new Button("Excluir"), AFTER + 2, SAME);
		add(new Label("Lista de Clientes"), RIGHT - 1, BOTTOM - 1);
		String[] gridCaptions = { " Id ", " Nome ", " Telefone ", " Endereço " };
		Grid.useHorizontalScrollBar = true;
		grid = new Grid(gridCaptions, false);
		Grid.useHorizontalScrollBar = false;
		grid.firstStripeColor = Color.WHITE;
		grid.verticalLineStyle = Grid.VERT_NONE;
		add(grid = new Grid(gridCaptions, false), LEFT+5, TOP+29, FILL-5, FILL-5);
		try {
			ArrayList<String[]> clientes = new ClienteDao().listar();
			for (int i = 0; i < clientes.size(); i++) {
				grid.add(clientes.get(i));
			}

		} catch (SQLException e) {
			MessageBox.showException(e, true);
		}
	}

	public void onEvent(Event e) {
		switch (e.type) {
		case ControlEvent.PRESSED:
			if (e.target == btnAdd) {
				new CadCliente().swapToTopmostWindow();
				repaint();
			} else if (e.target == btnRemove) {
				int idx = grid.getSelectedIndex();
				if (idx == -1)
					return;
				try {
					new ClienteDao().remover(grid.getSelectedItem()[0]);
					grid.del(idx);
				} catch (SQLException e1) {
					MessageBox.showException(e1, true);
				}
				repaint();
			} else if (e.target == btnChange) {
				int index = grid.getSelectedIndex();
				if (index == -1)
					return;
				String[] linha = grid.getSelectedItem();
				CadCliente cliente = new CadCliente(linha);
				cliente.swapToTopmostWindow();
				repaint();
			}
			break;
		}
	}
}
