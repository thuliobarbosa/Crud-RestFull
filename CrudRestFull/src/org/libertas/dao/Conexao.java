package org.libertas.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {
	
	private Connection conexao;
	
	/**
	 * Responsavel por criar uma conexão com a base de dados
	 * @author Thulio Barbosa de Paula Martins
	 */
	public Conexao() {
		try {
			String url = "jdbc:mariadb://localhost:3306/lib";
			conexao = DriverManager.getConnection(url, "root", "123");
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Responsavel por desconectar a conexão criada
	 * @author Thulio Barbosa de Paula Martins
	 */
	public void desconecta() {
		try {
			conexao.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConexao() {
		return conexao;
	}
}
