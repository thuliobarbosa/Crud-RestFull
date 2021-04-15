package org.libertas.rest;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.libertas.dao.FilmeDao;
import org.libertas.model.Filme;

import com.google.gson.Gson;

@WebServlet(urlPatterns={"/filme/*"}, name="filme")
public class FilmeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/***
	 * Responsavel por enviar uma mensagem de retorno do servidor.
	 * @author Thulio Barbosa de Paula Martins
	 * @param response
	 * @param json
	 * @param codigo
	 * @throws IOException
	 */
    private void enviaResposta(HttpServletResponse response, String json, int codigo) throws IOException {
		response.addHeader("Content-Type","application/json; charset=UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "http://127.0.0.1:5500");
		response.addHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT,OPTIONS");
		
		response.setStatus(codigo);
		
		BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
		out.write(json.getBytes("UTF-8"));
		out.close();
	}
    
    /***
     * Resposavel por listar os registro.
     * @author Thulio Barbosa de Paula Martins
     * @param request
     * @param response
     * @throws IOException, ServletException
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FilmeDao fdao = new FilmeDao();
		Gson gson = new Gson();
		int id = 0;
		
		if (request.getPathInfo()!=null) {
			String info = request.getPathInfo().replace("/", "");
			id = Integer.parseInt(info);
		}
		if (id > 0) {
			enviaResposta(response, gson.toJson(fdao.consultar(id)), 200);
		}
		else {
			enviaResposta(response, gson.toJson(fdao.listar()), 200);
		}
	}

	/***
	 * Responsavel por inserir registro.
	 * @author Thulio Barbosa de Paula Martins	
	 * @param request
	 * @param response
	 * @throws IOException, ServletException
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FilmeDao fdao = new FilmeDao();
		Gson gson = new Gson();
		String json = request.getReader().lines().collect(Collectors.joining());
		Filme f = gson.fromJson(json, Filme.class);
		fdao.inserir(f);
		enviaResposta(response, gson.toJson(new Response(true, "Registro inserido")), 201);
	}
	
	/***
	 * Responsavel por alterar registro.
	 * @author Thulio Barbosa de Paula Martins	
	 * @param request
	 * @param response
	 * @throws IOException, ServletException
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FilmeDao fdao = new FilmeDao();
		Gson gson = new Gson();
		int id = 0;
		
		if (request.getPathInfo()!=null) {
			String info = request.getPathInfo().replace("/", "");
			id = Integer.parseInt(info);
		}
		
		String json = request.getReader().lines().collect(Collectors.joining());
		Filme f = gson.fromJson(json, Filme.class);
		f.setId_filme(id);
		fdao.alterar(f);
		enviaResposta(response, gson.toJson(new Response(true, "Registro alterado")), 200);
	}
	
	/***
	 * Responsavel por deletar registro.
	 * @author Thulio Barbosa de Paula Martins	
	 * @param request
	 * @param response
	 * @throws IOException, ServletException
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FilmeDao fdao = new FilmeDao();
		Gson gson = new Gson();
		Filme f = new Filme();
		int id = 0;
		
		if (request.getPathInfo()!=null) {
			String info = request.getPathInfo().replace("/", "");
			id = Integer.parseInt(info);
		}
		
		f.setId_filme(id);
		fdao.excluir(f);
		enviaResposta(response, gson.toJson(new Response(true, "Registro excluido")), 200);
	}
	
	/***
	 * Responsavel por disparar uma mensagem do servidor.
	 * @author Thulio Barbosa de Paula Martins	
	 * @param request
	 * @param response
	 * @throws IOException, ServletException
	 */
	protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();
		enviaResposta(response, gson.toJson(new Response(true, "Options")), 200);
	}

}
