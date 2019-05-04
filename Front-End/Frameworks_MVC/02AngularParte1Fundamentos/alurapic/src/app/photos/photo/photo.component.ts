import { Component, Input } from '@angular/core';

// Veja os arquivos photos.module.ts e app.module.ts para entender como inserir este componente em um módulo
@Component({
    selector: 'app-photo',
    templateUrl: 'photo.component.html'
})
export class PhotoComponent {

    // @Input() indica que o valor dessas variáveis podem ser passadas atraves do selector deste componente
    // Essas variáveis precisam ou não ter valor nenhum ou serem inicializadas como uma string vazia APENAS para 
    // caracterizar que trata-se de um objeto String
    // Se você tentar colocar um valor nela, como se fosse um default, ele não será considerado de qualquer forma.
    @Input() description = '';
    @Input() url = '';

}
