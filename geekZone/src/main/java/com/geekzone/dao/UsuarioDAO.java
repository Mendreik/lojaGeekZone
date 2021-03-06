package com.geekzone.dao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.geekzone.entity.Usuario;

public class UsuarioDAO {
	
	public UsuarioDAO(){
		
	}
	
	public void cadastra(Usuario usuario) throws Exception {
		Connection db = ConnectionManager.getDBConnection();
		PreparedStatement pstmt = null;

		StringBuilder sql = new StringBuilder();

		String senha = usuario.getSenha();
		MessageDigest m = MessageDigest.getInstance("MD5");
		m.update(senha.getBytes(),0,senha.length());
		String criptografia = new BigInteger(1,m.digest()).toString(16);

		sql.append("INSERT INTO cliente ");
		sql.append(" ( ");
		sql.append(" nomeCompleto, ");
		sql.append(" cpf, ");
		sql.append(" nascimento, ");		
		sql.append(" sexo, ");
		sql.append(" cep, ");
		sql.append(" cidade, ");
		sql.append(" bairro, ");
		sql.append(" estado, ");
		sql.append(" numero, ");
		sql.append(" complemento, ");
		sql.append(" telefone, ");
		sql.append(" celular, ");
		sql.append(" email, ");
		sql.append(" senha ");
		sql.append(" ) ");
		sql.append(" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
		
		try {
			pstmt = db.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, usuario.getNomeCompleto());
			pstmt.setString(2, usuario.getCpf());
			pstmt.setString(3, usuario.getNascimento());
			pstmt.setString(4, usuario.getSexo());
			pstmt.setString(5, usuario.getCep());
			pstmt.setString(6, usuario.getCidade());
			pstmt.setString(7, usuario.getBairro());
			pstmt.setString(8, usuario.getEstado());
			pstmt.setString(9, usuario.getNumero());
			pstmt.setString(10, usuario.getComplemento());
			pstmt.setString(11, usuario.getTelefone());
			pstmt.setString(12, usuario.getCelular());
			pstmt.setString(13, usuario.getEmail());
			pstmt.setString(14, criptografia);

			pstmt.executeUpdate();

		} finally {
			if (pstmt != null)
				pstmt.close();
			db.close();
		}
	
	}

	public Usuario login(Usuario usuario) throws Exception {
		Usuario login = null;
		Connection db = ConnectionManager.getDBConnection();
		PreparedStatement pstmt = null;

		ResultSet result = null;


		String senha = usuario.getSenha();
		MessageDigest m=MessageDigest.getInstance("MD5");
		m.update(senha.getBytes(),0,senha.length());
		String criptografia = new BigInteger(1,m.digest()).toString(16);


		pstmt = db.prepareStatement("select * from cliente;");

		try {
			result = pstmt.executeQuery();
			while (result.next()) {
				if (usuario.getEmail().equals(result.getString("email"))
						&& criptografia.equals(result.getString("senha"))) {
					login = new Usuario();				
					login.setNomeCompleto(result.getString("nomeCompleto"));
					login.setCpf(result.getString("cpf"));
					login.setNascimento(result.getString("nascimento"));
					login.setSexo(result.getString("sexo"));
					login.setCep(result.getString("cep"));
					login.setCidade(result.getString("cidade"));
					login.setBairro(result.getString("bairro"));
					login.setEstado(result.getString("estado"));
					login.setNumero(result.getString("numero"));
					login.setComplemento(result.getString("complemento"));
					login.setTelefone(result.getString("telefone"));
					login.setCelular(result.getString("celular"));
					login.setEmail(result.getString("email"));
				}
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (pstmt != null)
				pstmt.close();
			db.close();
		}
		return login;
	}
	
	public Usuario getUsuario(String email) throws Exception {
		Usuario usuario = null;
		Connection db = ConnectionManager.getDBConnection();
		PreparedStatement pstmt = null;

		ResultSet result = null;

		pstmt = db.prepareStatement("select * from cliente where email='"+email+"';");

		try {
			result = pstmt.executeQuery();
			while (result.next()) {
				if (email.equals(result.getString("email"))) {
					usuario = new Usuario();
					usuario.setNomeCompleto(result.getString("nomeCompleto"));
					usuario.setCpf(result.getString("cpf"));
					usuario.setNascimento(result.getString("nascimento"));
					usuario.setSexo(result.getString("sexo"));
					usuario.setCep(result.getString("cep"));
					usuario.setCidade(result.getString("cidade"));
					usuario.setBairro(result.getString("bairro"));
					usuario.setEstado(result.getString("estado"));
					usuario.setNumero(result.getString("numero"));
					usuario.setComplemento(result.getString("complemento"));
					usuario.setTelefone(result.getString("telefone"));
					usuario.setCelular(result.getString("celular"));
					usuario.setEmail(result.getString("email"));
				}
			}

		} finally {
			if (pstmt != null)
				pstmt.close();
			db.close();
		}
		return usuario;
	}


}
