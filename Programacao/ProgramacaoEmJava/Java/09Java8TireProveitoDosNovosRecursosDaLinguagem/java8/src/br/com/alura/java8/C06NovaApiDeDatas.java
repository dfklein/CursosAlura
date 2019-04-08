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
		System.out.println("Data de hoje sem formata��o: " + hoje);
		System.out.println("Data de hoje completa sem formata��o: " + LocalDateTime.now());
		
		System.out.println("\n");
		
		LocalDate seuAniversario = LocalDate.of(1979, Month.JULY, 5);
		
		// Calculando a diferen�a entre o seu anivers�rio e hoje:
		int anos = hoje.getYear() - seuAniversario.getYear();
		System.out.println("Quantos anos entre hoje e o ano que voc� nasceu: " + anos);
		
		System.out.println("\n");
		
		/**
		 * Al�m das classes que definem datas e horas a api possui uma classe para representar
		 * um intervalo de tempo. Ela se chama Period.
		 */
		Period diasDeVida = Period.between(seuAniversario, hoje);
		System.out.println("Sua idade: " + diasDeVida.getYears() + " anos, " + diasDeVida.getMonths() + " meses e " + diasDeVida.getDays() + " dias.");
		
		System.out.println("\n");
		
		// A api de data tem objetos imut�veis. Ou seja:
		seuAniversario.plusYears(4);
		System.out.println("Voc� tentou somar 4 anos ao seu anivers�rio mas n�o funciona pq os objetos da API s�o imut�veis: " + seuAniversario);
		System.out.println("Voc� fez 4 anos de idade em: " + seuAniversario.plusYears(4));
		
		System.out.println("\n");
		
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		System.out.println("Seu anivers�rio formatado: " + fmt.format(seuAniversario));
		System.out.println("Experimentando um outro formatador com data atual: " + DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(LocalDateTime.now()));
		
		// Convertendo os tipos de data (sempre do LocalDateTime para o menor):
		LocalDateTime agora = LocalDateTime.now();
		LocalDate agoraData = agora.toLocalDate();
		LocalTime agoraHora = agora.toLocalTime();
		
		// O pacota do java time tem diversas classes que servem para guardar informa��es
		// espec�ficas:
		Year y = Year.now();
		YearMonth ym = YearMonth.now();
		Month m = Month.AUGUST;
		DayOfWeek dow = DayOfWeek.SATURDAY;
	}
}
