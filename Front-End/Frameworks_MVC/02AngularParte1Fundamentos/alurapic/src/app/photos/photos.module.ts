import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';

import { PhotoComponent } from './photo/photo.component';
import { PhotoListComponent } from './photo-list/photo-list.component';
import { PhotoFormComponent } from './photo-form/photo-form.component';
import { PhotosComponent } from './photo-list/photos/photos.component';

@NgModule({
    // Ao criar um módulo você precisa declarar quais componentes fazem parte deste módulo.
    // O declarations possui um caráter privado. Isso quer dizer que a princípio todos os componentes
    // declarados aqui dentro são acessíveis apenas entre si
    declarations: [
        PhotoComponent,
        PhotoListComponent,
        PhotoFormComponent,
        PhotosComponent
    ],
    // Se o módulo que você está fazendo for utilizado por outros módulos, você precisa exportá-lo
    // Ao exportar o componente ele passa a ser visível de fora deste módulo  
    exports: [
        PhotoFormComponent
    ],
    
    imports: [ 
        HttpClientModule,
        CommonModule    // O common module é a parte do BrowserModule que contém as diretivas básicas do Angular,
                        // como o ngFor, ngIf, etc...
                        // É uma boa prática sempre importá-lo nos seus módulos.
    ]
})
export class PhotosModule {

}