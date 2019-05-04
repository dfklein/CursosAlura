import { Component, Input } from '@angular/core';

// Veja os arquivos photos.module.ts e app.module.ts para entender como inserir este componente em um m�dulo
@Component({
    selector: 'app-photo',
    templateUrl: 'photo.component.html'
})
export class PhotoComponent {

    // @Input() indica que o valor dessas vari�veis podem ser passadas atraves do selector deste componente
    // Essas vari�veis precisam ou n�o ter valor nenhum ou serem inicializadas como uma string vazia APENAS para 
    // caracterizar que trata-se de um objeto String
    // Se voc� tentar colocar um valor nela, como se fosse um default, ele n�o ser� considerado de qualquer forma.
    @Input() description = '';
    @Input() url = '';

}
