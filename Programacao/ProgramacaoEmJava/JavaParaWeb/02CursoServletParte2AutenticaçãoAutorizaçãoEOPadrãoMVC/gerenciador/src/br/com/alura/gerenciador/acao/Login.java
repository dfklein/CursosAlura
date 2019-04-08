package br.com.alura.gerenciador.acao;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.alura.gerenciador.modelo.Banco;
import br.com.alura.gerenciador.modelo.Usuario;

public class Login implements Acao {

	@Override
	public String executa(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		
		System.out.println("Logando: " + login);
		
		Banco banco = new Banco();
		Usuario usuario = banco.existeUsuario(login, senha);
		
		if(usuario != null) {
			System.out.println("Usu�rio existe");
			// Quando um navegador acessa o servidor de aplica��o pela primeira vez, 
			// este gera uma sess�o que identifica aquele navegador (aquela sess�o).
			// � gerado um cookie para o navegador que trafegar� por todas as requisi��es 
			// como maneira de identificar aquela sess�o.
			// O objeto java associado no servidor a esta sess�o � uma implementa��o de HttpSession:
			HttpSession sessao = request.getSession();
			// Nele voc� pode pendurar outros objetos. Neste caso estamos associando o usu�rio � sess�o.
			sessao.setAttribute("usuarioLogado",  usuario);
			// Em listaEmpresa.jsp voc� ver� que ele sabe encontrar o objeto mesmo estando na sess�o ao inv�s da requisi��o.
			return "redirect:entrada?acao=ListaEmpresas";
		} else {
			System.out.println("Usu�rio n�o existe");
			return "redirect:entrada?acao=LoginForm";
			
		}
		
		
	}

}
