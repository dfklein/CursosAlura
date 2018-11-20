package br.com.alura.gerenciador.acao;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Logout implements Acao {

    @Override
    public String executa(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	HttpSession sessao = request.getSession();

    	// Você poderia apenas remover o usuário
    	// sessao.removeAttribute("usuarioLogado");
    	
    	// No entanto pode ser que você tenha outros atributos na sessão que podem influenciar acessos, permissões, etc.
    	// A melhor maneira é invalidar aquela sessão (ou seja, destruir o cookie criado pelo servidor):
    	sessao.invalidate();
        return "redirect:entrada?acao=LoginForm";
    }

}
