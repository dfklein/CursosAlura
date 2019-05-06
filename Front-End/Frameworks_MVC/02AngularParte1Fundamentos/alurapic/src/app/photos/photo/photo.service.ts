import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Photo } from './photo';

// Constantes são declaradas fora das classes.
const API = 'http://localhost:3000';

// Classes de serviço no angular são classes que isolam a responsabilidade de chamadas à api.
// O decorator injectable torna a sua classe de serviço injetável por outras. Ao definir como
// argumento do decorator o providedIn: 'root' você está dizendo que o escopo do seu serviço é
// singleton (ele é provido na raiz do projeto em uma única instância)
@Injectable({providedIn: 'root'})
export class PhotoService {

    // 1 - MUITA ATENÇÃO para a criação deste HttpClient: as ferramentas de autocomplete sugerem
    // um outro import e não oferecem o correto (ver acima). Isto acontece porque o módulo do HttpClient
    // que sabe fabricar este objeto para injeção não foi inserido ao módulo ao qual pertence esta classe.
    // Veja a importação do módulo em app.module.ts
    // 2 - Neste caso aqui a dependência é injetada pelo Angular, e não instanciada. Para
    // declarar a injeção ela deve ser passada como argumento no construtor da sua classe;
    // 3 - Note que no TypeScript para tipar uma variável a sintaxe é {variável:tipo}
    constructor(private http:HttpClient) {
        // Ao utilizar o modificador private na sua injeção você está indicando que ele é também um
        // atributo privado da sua classe, podendo então ser referenciado em um método com this.http
    }

    // Atenção: o tipo string em javascript é com a primeira letra minúscula.
    listFromUserName(userName:string) {
        // O http não retorna um objeto de cara: ele funciona como uma chamada assíncrona, sem parar
        // sua aplicação Angular enquanto a resposta da requisição não chega. É uma versão mais
        // robusta das promises:
        // const observable = http.get('http://localhost:3000/flavio/photos');
        
        // o método subscribe irá disparar a requisição propriamente dita.
        // observable.subscribe();

        // O SUBSCRIBE FOI REMOVIDO DAQUI PORQUE, NA VERDADE, É ALGO QUE DEVE ESTAR NA CLASSE QUE CONSOME
        // O SERVIÇO, MAS É IMPORTANTE QUE A EXPLICAÇÃO ESTEJA AQUI. A CHAMADA DO SUBSCRIBE FOI COMENTADA
        // MAS REPRESENTA O USO DO MÉTODO.

        // O subscribe pode receber como argumento uma função que recebe o objeto de resposta como argumento.
        // Ela será o callback da sua chamada.
        // Opcionalmente (como está implementado aqui), um segundo argumento recebe eventuais mensagens de
        // erro que irão ser tratadas em um segundo callback
        // Por fim, você pode deixar uma semântica mais interessante escrevendo desta maneira:
        return this.http
            // O operador diamante aqui é na verdade o mesmo que um cast. Não confunda com o java
            .get<Photo[]>(API + '/' + userName + '/photos')
            // A arrow function do ECMAScript substitui a sintaxe function(fotos) {...}
            // .subscribe(
            //     photos => {this.photos = photos;}, // como no java, essas chaves são opcionais
            //     err => console.log(err.message)
            // );
    }
}