package br.com.casadocodigo.loja.infra;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

// @Component é um componente genérico do Spring (não é um DAO, não é um controller, etc...). Isto torna o recurso injetável pelo Spring
// NUNCA SE ESQUEÇA QUE POR PADRÃO UMA CLASSE @Component É UM SINGLETON - > Ver maiores explicações em CarrinhoCompras.java
// NÃO ESQUEÇA: a classe FileSaver só pode ser injetada se você colocá-la no @ComponentScan da sua classe de configurações web (AppWebConfiguration.class)
@Component 
public class FileSaver {
	
	@Autowired // Eu não entendi bem isso, mas por se tratar de um componente o Spring pode injetar a requisição que chamou este método
	private HttpServletRequest request;

	public String write(String baseFolder, MultipartFile file) {
		try {
			String realPath = request.getServletContext().getRealPath("/" + baseFolder);
			String path = realPath + "/" + file.getOriginalFilename();
			file.transferTo(new File(path));
			
			return baseFolder + "/" + file.getOriginalFilename();
		
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
