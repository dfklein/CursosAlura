package br.com.caelum.livraria.bean;

// Esta classe foi criada apenas para abrigar observações feitas no curso sobre EJBs remotos. Não possui função.
public class RemotoBean {

//	Os EJBs, quando nasceram lá em 1999, eram todos objetos remotos! O que significa isso? Significa que qualquer acesso ao EJB, 
//	qualquer chamada de um método era feita pela rede! Não era possível chamar um EJB localmente, ou seja, dentro de mesma JVM.
//
//	EJB <---- chamada remota via rede ------> EJB
//	
//	Isso certamente não foi uma boa ideia, já que piora muito o desempenho (muito IO na rede!) e complica desnecessariamente o 
//	desenvolvimento. Muito padrões do antigo J2EE foram criados para se sobreviver a complexidade que veio de graça com o modelo
//	distribuído de desenvolvimento. Talvez você já ouviu falar dos padrões como DTO (Data Transfer Object), Session Facade ou 
//	Business Delegate.
//
//	Há um artigo famoso do Martin Fowler que foi muito citado na época que fala sobre os problemas da distribuição de objetos:
//
//	http://www.drdobbs.com/errant-architectures/184414966
//
//	Além disso, o Martin Fowler no livro Pattern of Enterprise Application Architecture resumiu a discussão e criou uma "lei" 
//	ou regra fundamental do design de objetos distribuídos. Dê uma olhada nessa regra:
//
//  ...
//
//	A regra é clara: Não distribua seus objetos. Ou seja, não faça que alguém possa acessar os seus objetos remotamente!
//
//	Então não posso usar EJB remotos? Isso vai depender de como você irá expor seu EJB. Você deve pensar na granularidade das 
//	chamadas dos métodos.
//
//	Normalmente os objetos se associam em um nível muito fino já que definem relacionamentos e trocam pequenas mensagens. Aplicar 
//	isso no design de objetos remotos é errado pois diminui o desempenho e gera complexidade.
//
//	Vou dar um exemplo concreto: Um carrinho de compras poderia ter dois métodos, o primeiro para passar o nome do produto e 
//	segundo para passar o código. Já são duas chamadas para adicionar um produto no carrinho. Imagina se o carrinho fosse um 
//	objeto remoto e você gostaria de adicionar 3 produtos no carrinho. No total, terminaríamos com 6 chamadas remotas!
//
//	Para evitar essas chamadas em excesso seria melhor criar apenas um método que recebe um produto, dessa forma ao adicionar 3 
//	produtos, seriam apenas 3 chamadas remotas.
//
//	Melhor ainda é oferecer um método no carrinho que pode receber um ou mais produtos, já que assim faríamos apenas uma chamada 
//	remota deixando o método menos granular.

}
