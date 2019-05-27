import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AuthService } from '../../core/auth/auth.service';
import { Router, ActivatedRoute } from '@angular/router';
import { PlatformDetectorService } from '../../core/plataform-detector/platform-detector.service';
import { Title } from '@angular/platform-browser';

@Component({
    templateUrl: './signin.component.html'
})
export class SignInComponent implements OnInit {
    
    loginForm: FormGroup;

    fromUrl:string;

    @ViewChild('userNameInput') userNameInput: ElementRef<HTMLInputElement>;
    
    constructor(
        private formBuilder: FormBuilder,
        private authService: AuthService,
        private router: Router,
        private platformDetectorService: PlatformDetectorService,
        private titleService: Title,
        private activatedRoute: ActivatedRoute) { }

    ngOnInit(): void {
        this.activatedRoute
            .queryParams
            .subscribe(params => {
            // O parâmetro "fromUrl" foi definido por você em auth.guard.ts. Ele captura
            // o parâmetro da url de nome fromUrl (você deu esse nome, poderia ser qualquer outro)
            this.fromUrl = params.fromUrl;
            // poderia ter escrito: this.fromUrl = params['fromUrl'];
        })
        this.titleService.setTitle('Login')
        this.loginForm = this.formBuilder.group({
            userName: ['', Validators.required],
            password: ['', Validators.required]
        });
        this.platformDetectorService.isPlatformBrowser() && 
        this.userNameInput.nativeElement.focus();        
    } 

    login() {
        const userName = this.loginForm.get('userName').value;
        const password = this.loginForm.get('password').value;

        this.authService
            .authenticate(userName, password)
            .subscribe(
                () => {
                    // Se existe alguma url informada no fromUrl, vai para ela. 
                    // Caso contrário vai para a página inicial do usuário. Ver
                    // auth.guard.ts para conhecer o funcionamento disso.
                    if(this.fromUrl) {
                        this.router.navigateByUrl(this.fromUrl);
                    } else {
                        this.router.navigate(['user', userName]) 

                    }
                },
                err => {
                    console.log(err);
                    this.loginForm.reset();
                    this.platformDetectorService.isPlatformBrowser() && 
                        this.userNameInput.nativeElement.focus();
                    alert('Invalid user name or password');
                }
            );
    }
}