import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


import { SignInComponent } from './sign-in/sign-in.component';
import { AuthGuard } from '../core/auth/auth.guard';
import { SignUpComponent } from './sign-up/sign-up.component';
import { HomeComponent } from './home.component';

const routes: Routes = [
   
    { 
        path: '',   // aqui você passa como se fosse uma rota vazia porque ela é dependente de uma rota pai. 
                    // Você vê isso no app.routing.module.ts.
        component: HomeComponent,
        canActivate: [AuthGuard],   // isto aqui cria um filtro. Veja auth.guard.ts.
                                    // foi usado para fazer o mecanismo que não deixa o usuário ir para o
                                    // login caso já esteja logado.
                                    // OBSERVAÇÃO: sempre que implementar isso, reinicie o Angular CLI
        children: [
            { 
                path: '',                   // Observe que você não passou nenhum parâmetro para esta rota,
                                            // assim como você não passou nada para o HomeComponent. Isso
                                            // quer dizer que este aqui é o filho padrão da rota pai. (Ou
                                            // seja: ao acessar o HomeComponent o router-outlet exibirá primeiro
                                            // o SignIn)  
                component: SignInComponent
            },
            { 
                path: 'signup', 
                component: SignUpComponent
                
            }
        ]
    }
];

@NgModule({
    imports: [ 
        RouterModule.forChild(routes)   // Quando seu módulo é subordinado a um módulo principal você usa
                                        // forChild. Isso faz parte da montagem de Lazy Loading de módulos
                                        // desta aplicação.
    ],
    exports: [ RouterModule ]
})
export class HomeRoutingModule { }

