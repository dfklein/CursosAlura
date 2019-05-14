import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AuthService } from '../../core/auth/auth.service';
import { Router } from '@angular/router';
import { PlatformDetectorService } from 'src/app/core/platform-detector/platform-detector.service';

@Component({
    // O selector não é obrigatório se você não vai usá-lo em outra página.
    templateUrl: './sign-in.component.html'
})
export class SignInComponent implements OnInit {
   
    loginForm: FormGroup;

    // Ele procura por "userNameInput" no template e injeta na variável de nome userNameInput.
    @ViewChild('userNameInput') userNameInput: ElementRef<HTMLInputElement>;

    constructor(
        private formBuilder:FormBuilder,
        private authService: AuthService,
        private router: Router,
        private platformDetectorService: PlatformDetectorService) { }

    ngOnInit(): void {
        this.loginForm = this.formBuilder.group({
            userName: ['', Validators.required],     // O primeiro parâmetro deste array é o valor padrão do campo.
                                // Do segundo parâmetro em diante você está passando validadores.
            password: ['', Validators.required]
        });

       
    }

    login() {
        const userName = this.loginForm.get('userName').value;
        const password = this.loginForm.get('password').value;

        this.authService
            .authenticate(userName, password)
            .subscribe(
                () => // this.router.navigateByUrl('user/' + userName), // esta é uma maneira de fazer a navegação, 
                                                                        // mas ficar concatenando string é meio tosco.
                    this.router.navigate(['user', userName]), // vai gerar a rota user/flavio.
                err => 
                    { 
                        this.loginForm.reset();
                        // Isso é uma sintaxe estranha do if
                        // Assim você verifica se a navegação é feita mesmo por um navegador e
                        // só nesse caso manipula o dom da aplicação.
                        this.platformDetectorService.isPlatformBrowser() && 
                            this.userNameInput.nativeElement.focus();   // isso aqui é só para voltar o foco para o input de login caso o login falhe 
                                                                        // evite isso: você está trabalhando diretamente no DOM assim.
                        
                    }
            );
    }

}