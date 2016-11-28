package br.com.crud.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import br.com.crud.conection.ConectionFactory;
import br.com.crud.model.Cliente;
import totalcross.sql.ResultSet;
import totalcross.sql.Statement;

public class ClienteDao {
	ConectionFactory cn = new ConectionFactory();

	public void salvar(Cliente cliente) throws SQLException {
		Statement conection = cn.getConection();
		conection.executeUpdate("insert into pessoas values(null,'" + cliente.getNome() + "','" + cliente.getTelefone()
				+ "','" + cliente.getEndereco() + "')");
	}

	public void remover(String id) throws SQLException {
		Statement conection = cn.getConection();
		conection.executeUpdate("delete from pessoas where id ='" + id + "'");
	}

	public ArrayList<String[]> listar() throws SQLException {
		Statement conection = cn.getConection();
		ResultSet rs = conection.executeQuery("SELECT * from pessoas");
		ArrayList<String[]> pessoas = new ArrayList<>();
		while (rs.next()) {
			String[] arrPessoas = new String[] { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4) };
			pessoas.add(arrPessoas);
			// Vm.debug("retorno: " + rs.getString(1));
		}
		return pessoas;
	}

	public void atualizar(Cliente cliente) throws SQLException {
		Statement conection = cn.getConection();
		conection.executeUpdate("update pessoas set nome= '" + cliente.getNome() + "', end='" + cliente.getEndereco()
				+ "',tel='" + cliente.getTelefone() + "' where id ='" + cliente.getId() + "'");
	}
}
