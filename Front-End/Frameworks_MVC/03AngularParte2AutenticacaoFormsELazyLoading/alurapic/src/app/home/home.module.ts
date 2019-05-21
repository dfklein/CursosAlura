import { NgModule } from '@angular/core';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';

import { SignInComponent } from './sign-in/sign-in.component';
import { CommonModule } from '@angular/common';
import { VmessageModule } from '../shared/components/vmessage/vmessage.module';
import { RouterModule } from '@angular/router';
import { SignUpComponent } from './sign-up/sign-up.component';
import { HomeComponent } from './home.component';
import { HomeRoutingModule } from './home.routung.module';
import { SignUpService } from './sign-up/sign-up.service';

@NgModule({
    declarations: [
        SignInComponent,
        SignUpComponent,
        HomeComponent
    ],
    imports: [
        ReactiveFormsModule,    // este módulo contém uma série de ferramentas para manipulação 
                                // e validação de formulários.
        CommonModule, 
        FormsModule,
        VmessageModule,
        RouterModule,
        HomeRoutingModule
    ],
    // Significa que se alguém precisar deste artefato disponível para injeção, ele estará disponível em
    // home.module, sendo acessível por todos que fizerem parte deste módulo. Você adicionou isso na 
    // implementação do lazy loading do home.module ao retirar o SignUpService do escopo 'root' (disponível
    // para toda a aplicação).
    providers: [
        SignUpService
    ]
})
export class HomeModule{}