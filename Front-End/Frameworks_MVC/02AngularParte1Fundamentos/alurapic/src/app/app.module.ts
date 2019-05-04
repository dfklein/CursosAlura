import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { PhotosModule } from './photos/photos.module';

@NgModule({
    // Aqui você importa componentes
    declarations: [
        AppComponent
    ],
    // Aqui você importa outros módulos
  imports: [
    BrowserModule,
    PhotosModule // Veja em photos.module.ts como é criado um módulo que contém um conjunto de componentes e que pode ser exportado para ser aceito em outro módulo 
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
