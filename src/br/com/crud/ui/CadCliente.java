package br.com.crud.ui;

import java.sql.SQLException;

import br.com.crud.dao.ClienteDao;
import br.com.crud.model.Cliente;
import totalcross.ui.AlignedLabelsContainer;
import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Edit;
import totalcross.ui.Label;
import totalcross.ui.ScrollContainer;
import totalcross.ui.Toast;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.gfx.Color;

public class CadCliente extends Container {
	Edit edId, edNome, edTelefone, edEndereco;
	Button btSalvar, btClear, btnCancelar;
	String id, nome, telefone, endereco;
	private Cliente cliente;
	public CadCliente(String[] linha) {
		id = linha[0];
		nome = linha[1];
		telefone = linha[2];
		endereco = linha[3];
	}

	public CadCliente() {
	}

	public void initUI() {
		super.initUI();
		ScrollContainer sc = new ScrollContainer(false, true);
		add(sc, LEFT, TOP, FILL, FILL);
		add(new Label("Informe os dados do Usuário"),CENTER,TOP+200);
		String[] labels = { "Id: ", "Nome: ", "Telefone: ", "Endereço: "};
		AlignedLabelsContainer c = new AlignedLabelsContainer(labels, 4);
		c.setBorderStyle(BORDER_LOWERED);
		c.labelAlign = RIGHT;
		c.foreColors = new int[] { Color.RED, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
				Color.BLACK, };
		c.setInsets(2, 2, 2, 2);
		sc.add(c, LEFT + 2, TOP + 2, FILL - 2, PREFERRED + 30);
		c.add(edId = new Edit(), LEFT + 2, c.getLineY(0));
		c.add(edNome = new Edit(), LEFT + 2, c.getLineY(1));
		c.add(edTelefone = new Edit("(99)9.9999-9999"), LEFT + 2, c.getLineY(2));
		edTelefone.setMode(Edit.NORMAL, true);
		c.add(edEndereco = new Edit(), LEFT + 2, c.getLineY(3));
		c.add(edId = new Edit(), LEFT + 2, c.getLineY(0));
		c.add(btSalvar = new Button(" Salvar "), RIGHT +2, c.getLineY(4));;
		c.add(btnCancelar = new Button(" Cancelar "),  BEFORE -2, SAME, PARENTSIZE+35, PREFERRED);
		c.add(btClear = new Button(" Limpar "),  BEFORE -2, SAME, PARENTSIZE+35, PREFERRED);
		//Se id <> null construtor com parametrosS
		if (id != null) {
			edId.setText(id);
			edNome.setText(nome);
			edTelefone.setText(telefone);
			edEndereco.setText(endereco);
			btSalvar.setText(" Editar ");
			//Desabilita para manter id
			btClear.setEnabled(false);
		}
		edId.setEnabled(false);
		
		edTelefone.setValidChars("0123456789");
	}
	public void onEvent(Event e) {
		try {
			switch (e.type) {
			case ControlEvent.PRESSED:
				if (e.target == btClear)
						clear();
				else if (e.target == btSalvar) {
					if (btSalvar.getText() == " Salvar ") {
						doInsert();
						return;
					}
					doUpdate();
				}else if(e.target == btnCancelar){
					new ListarClientes().swapToTopmostWindow();
				}
			}
		} catch (Exception ee) {
			MessageBox.showException(ee, true);
		}
	}
	private void doUpdate() throws SQLException {
		if (edNome.getLength() == 0 || edEndereco.getLength() == 0 || edTelefone.getLength() == 0  )
			Toast.show("Preencha todos os campos!", 2000);
		else {
			cliente = new Cliente();
			cliente.setNome(edNome.getText());
			cliente.setEndereco(edEndereco.getText());
			cliente.setTelefone(edTelefone.getText());
			cliente.setId(edId.getText());
			new ClienteDao().atualizar(cliente);
			clear();
			btSalvar.setText(" Salvar ");
			Toast.show("Atualizado com Sucesso!", 2000);
			new ListarClientes().swapToTopmostWindow();
		}
	}
	private void doInsert() throws SQLException {
		if (edNome.getLength() == 0 || edEndereco.getLength() == 0 || edTelefone.getLength() == 0)
			Toast.show("Preencha todos os campos!", 2000);
		else {
			cliente = new Cliente();
			cliente.setNome(edNome.getText());
			cliente.setEndereco(edEndereco.getText());
			cliente.setTelefone(edTelefone.getText());
			new ClienteDao().salvar(cliente);
			clear();
			Toast.show("Dados inseridos com sucesso!", 2000);
			new ListarClientes().swapToTopmostWindow();
		}
	}
}
