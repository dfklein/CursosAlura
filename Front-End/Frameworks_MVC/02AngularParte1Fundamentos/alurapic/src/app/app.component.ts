import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';


@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent {
    title = 'alurapic';

    // Note que no TypeScript para tipar uma variável a sintaxe é {variável:tipo}
    photos:Object[] = [];
    
    // 1 - MUITA ATENÇÃO para a criação deste HttpClient: as ferramentas de autocomplete sugerem
    // um outro import e não oferecem o correto (ver acima). Isto acontece porque o módulo do HttpClient
    // que sabe fabricar este objeto para injeção não foi inserido ao módulo ao qual pertence esta classe.
    // Veja a importação do módulo em app.module.ts
    // 2 - Neste caso aqui a dependência é injetada pelo Angular, e não instanciada. Para
    // declarar a injeção ela deve ser passada como argumento no construtor da sua classe;
    // 3 - Note que no TypeScript para tipar uma variável a sintaxe é {variável:tipo}
    constructor(http: HttpClient) {
        
        // O http não retorna um objeto de cara: ele funciona como uma chamada assíncrona, sem parar
        // sua aplicação Angular enquanto a resposta da requisição não chega. É uma versão mais
        // robusta das promises:
        // const observable = http.get('http://localhost:3000/flavio/photos');
        
        // o método subscribe irá disparar um evento notificando a chegada da resposta da requisição.
        // observable.subscribe();

        // O subscribe pode receber como argumento uma função que recebe o objeto de resposta como argumento.
        // Ela será o callback da sua chamada.
        // Opcionalmente (como está implementado aqui), um segundo argumento recebe eventuais mensagens de
        // erro que irão ser tratadas em um segundo callback
        // Por fim, você pode deixar uma semântica mais interessante escrevendo desta maneira:
        http
            // O operador diamante aqui é na verdade o mesmo que um cast. Não confunda com o java
            .get<Object[]>('http://localhost:3000/flavio/photos')
            // A arrow function do ECMAScript substitui a sintaxe function(fotos) {...}
            .subscribe(
                photos => {this.photos = photos;}, // como no java, essas chaves são opcionais
                err => console.log(err.message)
            );

    }

}
