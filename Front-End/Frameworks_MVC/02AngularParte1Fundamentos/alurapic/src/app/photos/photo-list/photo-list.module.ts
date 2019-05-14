import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PhotoModule } from '../photo/photo.module';
import { PhotosComponent } from './photos/photos.component';
import { CardModule } from 'src/app/shared/components/card/card.module';

import { PhotoListComponent } from './photo-list.component';
import { LoadButtonComponent } from './load-button/load-button.component';
import { FilterByDescription } from './filter-by-description.pipe';
import { SearchComponent } from './search/search.component';
import { DarkenOnHoverModule } from 'src/app/shared/directives/darken-on-hover/darken-on-hover.module';


@NgModule({
    declarations: [
        PhotoListComponent,
        PhotosComponent,        // Para ele funcionar aqui, ele precisa ser exportado no seu módulo.
                                // de origem (a princípio ele só ve os componentes do próprio módulo).
        LoadButtonComponent,
        FilterByDescription,
        SearchComponent
        
    ],
    imports: [
        CommonModule,
        PhotoModule,
        CardModule,
        DarkenOnHoverModule // usado no PhotosComponent
    ]
})
export class PhotoListModule {

}