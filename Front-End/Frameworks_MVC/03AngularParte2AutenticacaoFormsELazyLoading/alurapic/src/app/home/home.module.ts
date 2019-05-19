import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';

import { SignInComponent } from './sign-in/sign-in.component';
import { CommonModule } from '@angular/common';
import { VmessageModule } from '../shared/components/vmessage/vmessage.module';
import { RouterModule } from '@angular/router';
import { SignUpComponent } from './sign-up/sign-up.component';

@NgModule({
    declarations: [
        SignInComponent,
        SignUpComponent
    ],
    imports: [
        ReactiveFormsModule,    // este módulo contém uma série de ferramentas para manipulação 
                                // e validação de formulários.
        VmessageModule,
        CommonModule,
        RouterModule
    ]
})
export class HomeModule{}