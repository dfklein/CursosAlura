import { NgModule } from '@angular/core';
import { PhotoComponent } from './photo/photo.component';

@NgModule({
    // Ao criar um m�dulo voc� precisa declarar quais componentes fazem parte deste m�dulo.
    // O declarations possui um car�ter privado. Isso quer dizer que a princ�pio todos os componentes
    // declarados aqui dentro s�o acess�veis apenas entre si
    declarations: [
        PhotoComponent
    ],
    // Se o m�dulo que voc� est� fazendo for utilizado por outros m�dulos, voc� precisa export�-lo
    // Ao exportar o componente ele passa a ser vis�vel de fora deste m�dulo  
    exports: [
        PhotoComponent
    ]
})
export class PhotosModule {

}