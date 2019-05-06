import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { PhotoComponent } from './photo/photo.component';
import { PhotoListComponent } from './photo-list/photo-list.component';

@NgModule({
    // Ao criar um módulo você precisa declarar quais componentes fazem parte deste módulo.
    // O declarations possui um caráter privado. Isso quer dizer que a princípio todos os componentes
    // declarados aqui dentro são acessíveis apenas entre si
    declarations: [
        PhotoComponent,
        PhotoListComponent
    ],
    // Se o módulo que você está fazendo for utilizado por outros módulos, você precisa exportá-lo
    // Ao exportar o componente ele passa a ser visível de fora deste módulo  
    
    imports: [ HttpClientModule ]
})
export class PhotosModule {

}