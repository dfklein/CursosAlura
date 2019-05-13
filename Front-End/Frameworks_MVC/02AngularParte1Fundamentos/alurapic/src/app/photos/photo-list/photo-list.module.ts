import { NgModule } from '@angular/core';
import { PhotoListComponent } from './photo-list.component';
import { PhotosComponent } from './photos/photos.component';
import { LoadButtonComponent } from './load-button/load-button.component';
import { FilterByDescription } from './filter-by-description.pipe';
import { CommonModule } from '@angular/common';
import { PhotoModule } from '../photo/photo.module';
import { CardModule } from 'src/app/shared/components/card/card.module';


@NgModule({
    declarations: [
        PhotoListComponent,
        PhotosComponent,        // Para ele funcionar aqui, ele precisa ser exportado no seu módulo.
                                // de origem (a princípio ele só ve os componentes do próprio módulo).
        LoadButtonComponent,
        FilterByDescription
        
    ],
    imports: [
        CommonModule,
        PhotoModule,
        CardModule
    ]
})
export class PhotoListModule {

}