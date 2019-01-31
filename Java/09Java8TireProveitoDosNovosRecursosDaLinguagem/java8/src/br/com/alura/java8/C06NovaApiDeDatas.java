package br.com.alura.java8;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class C06NovaApiDeDatas {

	public static void main(String[] args) {
		
		LocalDate hoje = LocalDate.now();
		System.out.println("Data de hoje sem formatação: " + hoje);
		System.out.println("Data de hoje completa sem formatação: " + LocalDateTime.now());
		
		System.out.println("\n");
		
		LocalDate seuAniversario = LocalDate.of(1979, Month.JULY, 5);
		
		// Calculando a diferença entre o seu aniversário e hoje:
		int anos = hoje.getYear() - seuAniversario.getYear();
		System.out.println("Quantos anos entre hoje e o ano que você nasceu: " + anos);
		
		System.out.println("\n");
		
		/**
		 * Além das classes que definem datas e horas a api possui uma classe para representar
		 * um intervalo de tempo. Ela se chama Period.
		 */
		Period diasDeVida = Period.between(seuAniversario, hoje);
		System.out.println("Sua idade: " + diasDeVida.getYears() + " anos, " + diasDeVida.getMonths() + " meses e " + diasDeVida.getDays() + " dias.");
		
		System.out.println("\n");
		
		// A api de data tem objetos imutáveis. Ou seja:
		seuAniversario.plusYears(4);
		System.out.println("Você tentou somar 4 anos ao seu aniversário mas não funciona pq os objetos da API são imutáveis: " + seuAniversario);
		System.out.println("Você fez 4 anos de idade em: " + seuAniversario.plusYears(4));
		
		System.out.println("\n");
		
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		System.out.println("Seu aniversário formatado: " + fmt.format(seuAniversario));
		System.out.println("Experimentando um outro formatador com data atual: " + DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(LocalDateTime.now()));
		
		// Convertendo os tipos de data (sempre do LocalDateTime para o menor):
		LocalDateTime agora = LocalDateTime.now();
		LocalDate agoraData = agora.toLocalDate();
		LocalTime agoraHora = agora.toLocalTime();
		
		// O pacota do java time tem diversas classes que servem para guardar informações
		// específicas:
		Year y = Year.now();
		YearMonth ym = YearMonth.now();
		Month m = Month.AUGUST;
		DayOfWeek dow = DayOfWeek.SATURDAY;
	}
}
