import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';

import { PhotosModule } from './photos/photos.module';
import { AppRoutingModule } from './app.routing.module';
import { ErrorsModule } from './errors/errors.module';

import { CoreModule } from './core/core.module';
import { HomeRoutingModule } from './home/home.routung.module';

@NgModule({
  declarations: [
    AppComponent
  ],
  // Lembre que tudo que é importado por um módulo é visível pelos outros módulos.
  // AppRoutingModule exporta RouterModule, o que quer dizer que todos tem acesso a ele.
  // Mesmo assim é considerado uma boa prática você declarar ele explicitamente em módulos
  // Que o utilizam.
  imports: [
    BrowserModule,
    PhotosModule,
    ErrorsModule,
    // HomeModule,    // NÃO PODE DECLARAR UM MÓDULO QUE TERÁ LAZY LOADING AQUI.
                      // Ele é chamado através de um tipo de reflection no app.routing.module.ts
    CoreModule,
    AppRoutingModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }


