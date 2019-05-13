import { NgModule } from '@angular/core';

import { PhotoModule } from './photo/photo.module';
import { PhotoFormModule } from './photo-form/photo-form.module';
import { PhotoListModule } from './photo-list/photo-list.module';


@NgModule({
    // Ao criar um módulo você precisa declarar quais componentes fazem parte deste módulo.
    // O declarations possui um caráter privado. Isso quer dizer que a princípio todos os componentes
    // declarados aqui dentro são acessíveis apenas entre si
    // ATENÇÃO: a declaração dos componentes aqui foi substituída por declaração de submódulos
    // declarations: [
    //    PhotoComponent,
    //    PhotoListComponent,
    //    PhotoFormComponent,
    //    PhotosComponent,
    //    FilterByDescription,
    //    LoadButtonComponent
    // ],


    // Se o módulo que você está fazendo for utilizado por outros módulos, você precisa exportá-lo
    // Ao exportar o componente ele passa a ser visível de fora deste módulo  
    // ATENÇÃO: a declaração dos componentes aqui foi substituída por declaração de submódulos
    // exports: [
    //     PhotoFormComponent
    // ],
    
    imports: [
        PhotoModule,
        PhotoFormModule,
        PhotoListModule,
        
        
        // CommonModule // O common module é a parte do BrowserModule que contém as diretivas básicas do Angular,
                        // como o ngFor, ngIf, etc...
                        // É uma boa prática sempre importá-lo nos seus módulos.
            
    ]
})
export class PhotosModule {

}