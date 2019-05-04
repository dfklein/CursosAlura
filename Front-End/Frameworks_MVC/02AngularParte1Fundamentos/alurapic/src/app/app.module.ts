import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { PhotosModule } from './photos/photos.module';

@NgModule({
    // Aqui voc� importa componentes
    declarations: [
        AppComponent
    ],
    // Aqui voc� importa outros m�dulos
  imports: [
    BrowserModule,
    PhotosModule // Veja em photos.module.ts como � criado um m�dulo que cont�m um conjunto de componentes e que pode ser exportado para ser aceito em outro m�dulo 
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
