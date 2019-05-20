import { NgModule } from '@angular/core';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';

import { SignInComponent } from './sign-in/sign-in.component';
import { CommonModule } from '@angular/common';
import { VmessageModule } from '../shared/components/vmessage/vmessage.module';
import { RouterModule } from '@angular/router';
import { SignUpComponent } from './sign-up/sign-up.component';
import { HomeComponent } from './home.component';
import { HomeRoutingModule } from './home.routung.module';

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
    ]
})
export class HomeModule{}