package br.com.casadocodigo.loja.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.casadocodigo.loja.dao.UsuarioDAO;

// A anotação abaixo foi usada no curso mas está depreciada em versões posteriores do Spring. Utilize @EnableWebSecurity 
// ---> @EnableWebMvcSecurity
// Lembre-se de que você tem que não apenas criar esta classe, mas também torná-la acessível para o Spring.
// Veja na classe ServletSpringMVC.class o método getRootConfigClasses()
@EnableWebSecurity
// No vídeo o instrutor mostra que o projeto quebra se esta classe não extender WebSecurityConfigurerAdapter.
// O Tomcat teria dado uma mensagem dizendo que não consegue encontrar as configurações de segurança da
// aplicação. No entanto, ao fazer este teste, você não teve problemas. Talvez seja a versão nova da
// anotação que habilita a classe como sendo de segurança. Ao extender a classe você não conseguiu
// subir o projeto, você precisou sobrescrever o método configure para subi-lo
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	// Você precisa especificar para o Spring quando a sua classe DAO for responsável por implementar
	// a busca dos perfis de usuário. Veja que a classe UsuarioDAO implementa a interface UserDetailsService.
	@Autowired
    private UsuarioDAO usuarioDao;

	// O configure é onde você vai dar as configurações de acesso. A implementação padrão dele
	// bloqueia TODAS as requisições pedindo login e senha.
	// Para criar permissionamento dentro das telas (ocultar um link para quem não tem determinado perfil,
	// por exemplo), o srping possui taglibs especiais. Veja a implementação em home.jsp (perto da linha 37, 
	// onde tem a barra de navegação da aplicação com as opções "Listagem de Produtos" e "Cadastro de Produtos",
	// tag <security:authorize>)
	// IMPORTANTE: o Spring faz uma verificação de ataques de CSRF (o fin da classe uma explicação sobre o que é)
	// e isto vai exigir que você transporte um tokken através das suas 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http.authorizeRequests()
	    // Não tem muito mistério: o antMatchers recebe o caminho cuja regra você está escrevendo e,
	    // em seguida, você escreve qual regra permite o acesso a ela.
	    .antMatchers("/produtos/form").hasRole("ADMIN")
	    // Aqui apenas o usuário que possui a permissão (role) de nome ROLE_ADMIN.
	    // O Spring entende que todos os perfis possuem no banco o prefixo "ROLE_" e concatena automaticamente.
	    // Portanto você passa apenas "ADMIN" como argumento, mas não se esqueça que no banco o nome estará
	    // como "ROLE_ADMIN"
	    // ----> .antMatchers(HttpMethod.POST, "/produtos").hasRole("ADMIN")
	    // ----> .antMatchers(HttpMethod.GET, "/produtos").hasRole("ADMIN")
	    .antMatchers("/carrinho/**").permitAll()
	    .antMatchers("/produtos/").hasRole("ADMIN")
	    // Segundo o instrutor, o ideal é você escrever os bloqueios antes das permissões. Isto garante
	    // que no exemplo da linha seguinte você esteja liberando o acesso a todas as requisições ao
	    // /produtos exceto as que foram bloqueadas anteriormente.
	    .antMatchers("/produtos/**").permitAll()
	    // Não esqueça de liberar a pasta resources com JS e CSS
	    // .antMatchers("/resources/**").permitAll()
	    .antMatchers("/pagamento/**").permitAll()
	    .antMatchers("/").permitAll()
	    // Para finalizar você diz que todas as requisições devem ser autenticadas...
	    .anyRequest().authenticated()
	    // ... e caso não estejam, deve exigir e direcionar o usuário à tela de login
	    // ---> .and().formLogin(); // Assim você vai para a página padrão de login criada pelo próprio Spring
	    // Veja que você criou um controller e uma página para isso. E você precisa dar a permissão para acessá-la.
	    // Se você para
	    .and().formLogin().loginPage("/login").defaultSuccessUrl("/produtos").permitAll()
	    // Aqui você pode definir um caminho que vai servir como logout da aplicação no argumento do new AntPathRequestMatcher(...)
	    .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll().logoutSuccessUrl("/login");
	}
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Você precisa especificar para o Spring quando a sua classe DAO for responsável por implementar
		// a busca dos perfis de usuário. Veja que a classe UsuarioDAO implementa a interface UserDetailsService.
		// Isto é essencial para que este método aceite seu DAO como argumento.
		// Veja a classe UsuarioDAO para ver a implementação do método loadUserByUsername()
        auth.userDetailsService(usuarioDao)
        	.passwordEncoder(new BCryptPasswordEncoder()); // Esta é uma classe do Spring de encriptação de senha
    }
	
	// Forma recomendada de ignorar no filtro de segurança as requisições para recursos estáticos
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }
}
// *************************** O que é CSRF ***************************
// CSRF é uma sigla que representa a frase Cross Site Request Forgery, uma técnica de ataque a sites
// muito comum na web. A técnica representa o cenário de que um outro site está enviando dados para
// nossa aplicação, em vez do usuário diretamente. Geralmente, acontece com páginas clonadas, uma página
// falsa é apresentada ao usuário e este sem saber submete seus dados que por sua vez podem ser enviados
// ao servidor original ou podem ser feitas cópias em um servidor falso para posteriormente ter o uso
// indevido.
//
// O Spring espera sempre receber um tokken na requisição com o name ${_csrf.token} e 
// o value ${_csrf.parameterName} (que é o nome da variável com o token que foi gerado pelo 
// servidor do Spring para aquela sessão http).
// Ela vai garantir que o formulário foi gerado no seu servidor, e não que se trata de uma requisição post
// vindo de qualquer lugar:
// ----> <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }" />
//
// Esta é a forma mínima de como poderia ser feito, mas veja em detalhes.jsp como esta implementação 
