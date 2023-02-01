package br.com.cotiinformatica.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.factories.ConnectionFactory;

public class UsuarioRepository {

	// método para cadastrar um usuário no banco de dados
	public void create(Usuario usuario) throws Exception {
		// abrindo conexão com o banco de dados
		Connection connection = ConnectionFactory.createConnection();

		// escrevendo um comando SQL no banco de dados
		PreparedStatement statement = connection.prepareStatement("insert into usuario(nome, email, senha)	values(?, ?, ?)");

				
		statement.setString(1, usuario.getNome());
		statement.setString(2, usuario.getEmail());
		statement.setString(3, usuario.getSenha());
		statement.execute();
		// fechando a conexão
		connection.close();
	}
	

	// método para atualizar a senha de um usuário
	public void update(Integer idUsuario, String novaSenha) throws Exception {
		
		//abrindo conexão com o banco de dados
		Connection connection = ConnectionFactory.createConnection();
		
		PreparedStatement statement = connection.prepareStatement("update usuario set senha=md5(?) where idusuario=?");
		statement.setString(1, novaSenha);
		statement.setInt(2, idUsuario);
		statement.execute();
		
		connection.close();
	}	

	// m�todo para consultar 1 usu�rio no banco de dados atrav�s do email
	public Usuario findByEmail(String email) throws Exception {

		// abrindo conex�o com o banco de dados
		Connection connection = ConnectionFactory.createConnection();

		// escrevendo um comando SQL no banco de dados
		PreparedStatement statement = connection.prepareStatement("select * from usuario where email = ?");
		statement.setString(1, email);
		ResultSet resultSet = statement.executeQuery();

		Usuario usuario = null; // vazio

		// verificar se algum registro foi encontrado
		if (resultSet.next()) {

			usuario = new Usuario(); // instanciando o objeto

			usuario.setIdUsuario(resultSet.getInt("idusuario"));
			usuario.setNome(resultSet.getString("nome"));
			usuario.setEmail(resultSet.getString("email"));
		}

		// fechando a conex�o com o banco de dados
		connection.close();

		return usuario; // retornando o objeto
	}

	// m�todo para consultar 1 usu�rio no banco de dados atrav�s do email e senha
	public Usuario findByEmailAndSenha(String email, String senha) throws Exception {

		// abrindo conex�o com o banco de dados
		Connection connection = ConnectionFactory.createConnection();

		// escrevendo o comando SQL para execu��o
		PreparedStatement statement = connection.prepareStatement("select * from usuario where email = ? and senha = md5(?)");
			
		statement.setString(1, email);
		statement.setString(2, senha);
		ResultSet resultSet = statement.executeQuery();

		Usuario usuario = null;

		if (resultSet.next()) {

			usuario = new Usuario();

			usuario.setIdUsuario(resultSet.getInt("idusuario"));
			usuario.setNome(resultSet.getString("nome"));
			usuario.setEmail(resultSet.getString("email"));
		}

		connection.close(); // fechando a conex�o
		return usuario; // retornando objeto
	}

}