package br.com.caelum.financas.relatorio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import br.com.caelum.financas.ConnectionFactory;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;

public class TesteGeraRelatorio {

	public static void main(String[] args) throws SQLException, JRException, IOException {

		Connection conexao = new ConnectionFactory().getConnection();
		
		// Abaixo você compila o jrxml para um arquivo jasper. 
		// IMPORTANTE: para isso funcionar você precisa definir no iReport que a linguagem do 
		// seu relatório é java. Para isso você edita as propriedades do seu relatório - clique
		// em cima dele e à direita deve aparecer uma caixa com as propriedades. Na seção "More"
		// haverá uma propriedade chamada "Language" que você deve setar para "Java" (o default dele
		// é "Groovy"). Faça isso também em todos os subrelatórios
		// Você também pode alterar isso diretamente no jrxml
		JasperCompileManager.compileReportToFile("gastos_por_mes.jrxml"); // Não se esqueça de que você copiou os jrxmls para a raiz do seu projeto, por isso que você não declarou o caminho completo do arquivo.
		JasperCompileManager.compileReportToFile("gastos_por_mes_subreport1.jrxml"); 
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		// Este é um objeto com o formulário preenchido
		JasperPrint fillReport = JasperFillManager.fillReport("gastos_por_mes.jasper", parameters, conexao);
		
		// Este é um objeto exportador para PDF
		JRExporter exporter = new JRPdfExporter();
		// coloca o relatório preenchido como o parâmetro de impressão do exporter
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, fillReport);
		// define a saída do relatório
		OutputStream stream = new FileOutputStream("gasto_por_mes.pdf");
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, new FileOutputStream("gasto_por_mes.pdf"));
		exporter.exportReport();

		// Abaixo um outro exemplo de export (neste caso, para html). Foi mostrado num exercício mas não no curso.
		JRExporter exporterHtml = new JRHtmlExporter();
		OutputStream streamHtml = new FileOutputStream("gasto_por_mes.html");
		exporterHtml.setParameter(JRExporterParameter.JASPER_PRINT, fillReport);
		exporterHtml.setParameter(JRExporterParameter.OUTPUT_STREAM, stream);
		exporterHtml.exportReport();
		
		// XLS - acho que usa o POI, por isso que ele está no projeto.
//		JRExporter exporterXls = new JRXlsExporter();
//		exporterXls.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//		exporterXls.setParameter(JRExporterParameter.OUTPUT_STREAM, new FileOutputStream("financas.xls"));
//		exporterXls.exportReport();
		
		stream.close();
		streamHtml.close();
		conexao.close();
	}
}
