import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { PhotoComponent } from './photo/photo.component';
import { PhotoListComponent } from './photo-list/photo-list.component';
import { CommonModule } from '@angular/common';
import { PhotoFormComponent } from './photo-form/photo-form.component';

@NgModule({
    // Ao criar um módulo você precisa declarar quais componentes fazem parte deste módulo.
    // O declarations possui um caráter privado. Isso quer dizer que a princípio todos os componentes
    // declarados aqui dentro são acessíveis apenas entre si
    declarations: [
        PhotoComponent,
        PhotoListComponent,
        PhotoFormComponent
    ],
    // Se o módulo que você está fazendo for utilizado por outros módulos, você precisa exportá-lo
    // Ao exportar o componente ele passa a ser visível de fora deste módulo  
    
    imports: [ 
        HttpClientModule, 
        CommonModule    // O common module é um módulo que faz parte do BrowserModule que é declarado em app.module.ts.
                        // O BrowserModule é um módulo que SÓ PODE SER DECLARADO no app.module e que possui mecanismos
                        // próprios do Angular para rodar. Porém é ele também quem possui as diretivas, sendo necessário
                        // em outros lugares. Desta forma, nestes outros lugares, você pode importar o CommonModule
                        // para obter estes recursos como as diretivas.
    ]
})
export class PhotosModule {

}