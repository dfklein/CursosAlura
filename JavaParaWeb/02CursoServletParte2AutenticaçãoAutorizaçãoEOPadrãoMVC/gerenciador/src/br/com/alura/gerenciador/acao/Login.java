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
			System.out.println("Usuário existe");
			// Quando um navegador acessa o servidor de aplicação pela primeira vez, 
			// este gera uma sessão que identifica aquele navegador (aquela sessão).
			// É gerado um cookie para o navegador que trafegará por todas as requisições 
			// como maneira de identificar aquela sessão.
			// O objeto java associado no servidor a esta sessão é uma implementação de HttpSession:
			HttpSession sessao = request.getSession();
			// Nele você pode pendurar outros objetos. Neste caso estamos associando o usuário à sessão.
			sessao.setAttribute("usuarioLogado",  usuario);
			// Em listaEmpresa.jsp você verá que ele sabe encontrar o objeto mesmo estando na sessão ao invés da requisição.
			return "redirect:entrada?acao=ListaEmpresas";
		} else {
			System.out.println("Usuário não existe");
			return "redirect:entrada?acao=LoginForm";
			
		}
		
		
	}

}
