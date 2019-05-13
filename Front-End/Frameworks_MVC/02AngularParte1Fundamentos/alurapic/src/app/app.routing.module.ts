import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


import { PhotoListComponent } from './photos/photo-list/photo-list.component';
import { PhotoFormComponent } from './photos/photo-form/photo-form.component';
import { NotFoundComponent } from './errors/not-found/not-found.component';
import { PhotoListResolver } from './photos/photo-list/photo-list.resolver';

// ***************************** ATENÇÃO *****************************
// para seu roteamento funcionar você precisa importar este módulo no seu app.module.ts
// ***************************** ******* *****************************
const routes: Routes = [
    { 
        path:'user/:userName',  // ao usar o : na rota você está 
                                // declarando aquilo como uma variável
                                // Veja em photo-list-component.ts como
                                // isto é usado.
        component: PhotoListComponent,
        resolve: {
            photos:PhotoListResolver    // aqui você está dizendo a ele que ele deve aplicar à propriedade
                                        // photos o resultado do método resolve de PhotoListResolver e, só
                                        // depois disso, renderizar a página gerada.
        }
    },   
    { path:'p/add', component: PhotoFormComponent },
    // A seguir um exemplo de como referenciar rotas que não foram definidas
    { path:'**', component: NotFoundComponent }
];

@NgModule({
    imports: [
        RouterModule.forRoot(routes)    // forRoot referencia tudo o que está dentro do domínio da 
                                        // aplicação Angular (no nosso caso o localhost:4200)
                                        // Ao passar a constante routes para ele, você está
                                        // passando as configurações de rota que você declarou na
                                        // constante.
    ],

    exports: [
        RouterModule
    ]
})
export class AppRoutingModule {

    
}