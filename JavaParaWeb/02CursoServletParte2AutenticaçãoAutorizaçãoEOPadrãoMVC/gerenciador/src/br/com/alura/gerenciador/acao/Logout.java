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

    	// Voc� poderia apenas remover o usu�rio
    	// sessao.removeAttribute("usuarioLogado");
    	
    	// No entanto pode ser que voc� tenha outros atributos na sess�o que podem influenciar acessos, permiss�es, etc.
    	// A melhor maneira � invalidar aquela sess�o (ou seja, destruir o cookie criado pelo servidor):
    	sessao.invalidate();
        return "redirect:entrada?acao=LoginForm";
    }

}
