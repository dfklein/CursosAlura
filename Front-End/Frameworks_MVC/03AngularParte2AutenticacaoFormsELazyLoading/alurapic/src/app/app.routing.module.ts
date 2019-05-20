import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { PhotoListComponent } from './photos/photo-list/photo-list.component';
import { PhotoFormComponent } from './photos/photo-form/photo-form.component';
import { NotFoundComponent } from './errors/not-found/not-found.component';
import { PhotoListResolver } from './photos/photo-list/photo-list.resolver';

const routes: Routes = [
    {
        path: '',           // equivale a http://localhost:4200/
        pathMatch: 'full',  // Significa que ele deve fazer o match da rota completa.
                            // Caso você não passe este atributo ele tentará fazer o match com a rotas
                            // parciais, tentando encontrar o valor passado em path em qualquer rota que
                            // o usuário tentar acessar.
        redirectTo: 'home'
    },
    // ATENÇÃO!!!!
    // O seu home é o exemplo de implementação de lazy loading. A função disto é fazer com que sua aplicação
    // seja carregada em partes, separando alguns módulos (code splitting) e definindo que os mesmos só serão
    // carregados se demandados para evitar que sua single page application tenha um carregamento muito lento.
    // ATENÇÃO!!!
    // Para fazer isso você NÃO PODE DECLARAR O HOME NO IMPORTS DO app.module.ts.
    { 
        path: 'home', 
        loadChildren: './home/home.module#HomeModule'   // Aqui é a magia do lazy loading.
                                                        // ./home/ -> é o caminho para o módulo em questão
                                                        // home.module -> é o nome do ARQUIVO do componente SEM O .ts
                                                        // #HomeModule -> é o nome da classe que o arquivo exporta.
    },
    
    { 
        path: 'user/:userName', 
        component: PhotoListComponent,
        resolve: {
            photos: PhotoListResolver
        }
    },
    { 
        path: 'p/add', 
        component: PhotoFormComponent 
    },
    { 
        path: '**', 
        component: NotFoundComponent 
    }  
];

@NgModule({
    imports: [ 
        RouterModule.forRoot(routes, {useHash:true} )   // O segundo argumento é opcional. Ele adiciona um # às rotas
                                                        // chamada pela sua aplicação. Este é um artifício antigo
                                                        // que existia para evitar que as navegações de rotas chamassem
                                                        // o back-end da aplicação.
                                                        // Se você optar por não usar o # na sua aplicação em produção,
                                                        // você precisará configurar o seu servidor (tomcat, php, o que for) para que
                                                        // em todas as requisições ele devolva index.html. 
                                                        // Note que o Angular CLI simula esta configuração para você.
    ],
    exports: [ RouterModule ]
})
export class AppRoutingModule { }

