import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { PhotoListComponent } from './photos/photo-list/photo-list.component';
import { PhotoFormComponent } from './photos/photo-form/photo-form.component';
import { NotFoundComponent } from './errors/not-found/not-found.component';
import { PhotoListResolver } from './photos/photo-list/photo-list.resolver';
import { SignInComponent } from './home/sign-in/sign-in.component';
import { AuthGuard } from './core/auth/auth.guard';
import { SignUpComponent } from './home/sign-up/sign-up.component';

const routes: Routes = [
    { 
        path: '', // Isto equivale a localhost:4200/ 
        component: SignInComponent,
        canActivate: [AuthGuard]    // isto aqui cria um filtro. Veja auth.guard.ts.
                                    // foi usado para fazer o mecanismo que não deixa o usuário ir para o
                                    // login caso já esteja logado.
                                    // OBSERVAÇÃO: sempre que implementar isso, reinicie o Angular CLI
    },
    { 
        path: 'signup', // Isto equivale a localhost:4200/ 
        component: SignUpComponent
        
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
        RouterModule.forRoot(routes) 
    ],
    exports: [ RouterModule ]
})
export class AppRoutingModule { }

