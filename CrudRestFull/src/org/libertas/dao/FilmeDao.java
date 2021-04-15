package org.libertas.dao;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.libertas.model.Filme;

public class FilmeDao {
		
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("ConexaoHibernate");
	private static EntityManager em = emf.createEntityManager();
	
	/**
	 * Lista todos registro inseridos na base de dados.
	 * @author Thulio Barbosa de Paula Martins
	 * @return lista
	 */
	public List<Filme> listar() {
		
		Query query = em.createQuery("SELECT f FROM Filme f");
		List<Filme> lista = (List<Filme>) query.getResultList();
		return lista;
		
	}
	
	/**
	 * Inseri um registro na base de dados.
	 * @author Thulio Barbosa de Paula Martins
	 * @param f 
	 */
	public void inserir(Filme f) {
		em.getTransaction().begin();
		em.persist(f);
		em.getTransaction().commit();
	}
	
	/**
	 * Altera um registro na base de dados.
	 * @author Thulio Barbosa de Paula Martins
	 * @param f 
	 */
	public void alterar(Filme f) {
		em.getTransaction().begin();
		em.merge(f);
		em.getTransaction().commit();
	}
	
	/**
	 * Exclui um registro na base de dados.
	 * @author Thulio Barbosa de Paula Martins
	 * @param f 
	 */
	public void excluir(Filme f) {
		em.getTransaction().begin();
		em.remove(em.merge(f));
		em.getTransaction().commit();
	}
	
	/**
	 * Consulta um registro na base de dados.
	 * @author Thulio Barbosa de Paula Martins
	 * @param id
	 * @return f 
	 */
	public Filme consultar(int id) {
		Filme f = em.find(Filme.class, id);
		return f;
	}

}
