import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { lowerCaseValidator } from 'src/app/shared/validators/lower-case.validator';
import { UserNotTakenValidatorService } from './user-not-taken.validator.service';
import { NewUser } from './new-user';
import { SignUpService } from './sign-up.service';
import { Router } from '@angular/router';
import { PlatformDetectorService } from 'src/app/core/platform-detector/platform-detector.service';

@Component({
    templateUrl: './sign-up.component.html',
    // O selector não é obrigatório quando você não vai usar o componente fora da sua própria página (quando ele é a página raiz)
})
export class SignUpComponent implements OnInit {
    
    signupForm: FormGroup;
    @ViewChild('emailInput') emailInput: ElementRef<HTMLInputElement>;
    
    constructor(
        private formBuilder: FormBuilder,
        private userNotTakenValidatorService: UserNotTakenValidatorService,
        private signUpService: SignUpService,
        private router: Router,
        private platformDetectorService: PlatformDetectorService) {
    }
    ngOnInit(): void {
        
        this.signupForm = this.formBuilder.group({
            email: ['', 
                [
                    Validators.required,
                    Validators.email
                ]
            ],
            // Lembre que o atributo deve ser um array com 3 argumentos:
            // 1 - O valor padrão do atributo,
            // 2 - Um array com validadores síncronos e
            // 3 - Validadores assíncronos (chamadas Ajax).
            userName: ['', 
                [
                    Validators.required, 
                    Validators.minLength(2), 
                    Validators.maxLength(30), 
                    //Implementação do validador customizado.
                    lowerCaseValidator /* Validators.pattern(/^[a-z0-9_\-]+$/)] */ 
                ],
                [ 
                    this.userNotTakenValidatorService.checkUserNameTaken()
                ]

            ],
            fullName: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(40)]],
            password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(14)]]
        })
        
        this.platformDetectorService.isPlatformBrowser() &&
        this.emailInput.nativeElement.focus();
    } 

    signup() {
        // o getRawValue vai retornar asa propriedades que você definiu na variável signupForm com os seus valores atuais.
        // Note que você fez também uma tipificação para NewUser também.
        const newUser = this.signupForm.getRawValue() as NewUser;
        this.signUpService
            .signup(newUser)
            .subscribe(
                () => this.router.navigate(['']),
                err => console.log(err)
            );
    }
}