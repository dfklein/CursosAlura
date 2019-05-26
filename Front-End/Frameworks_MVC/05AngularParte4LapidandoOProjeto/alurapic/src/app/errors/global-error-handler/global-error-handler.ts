import { ErrorHandler, Injectable, Injector } from '@angular/core';
import * as StackTrace from "stacktrace-js";
import { LocationStrategy, PathLocationStrategy } from '@angular/common';
import { UserService } from 'src/app/core/user/user.service';
import { ServerLogService } from './server-log.service';
import { Router } from '@angular/router';
import { environment } from '../../../environments/environment';

// Para você ativar um error handler, você precisa ativá-lo no seu módulo.
// Veja errors.module.ts
// Você usou uma bibiloteca para lidar com erros que não é nativa do Angular chamada stacktrace-js.
// Isso é uma boa ideia porque ele vai padronizar a sua msg de stacktrace independentemente de
// qual navegador você estiver usando (cada navegador gera uma stacktrace diferente para um mesmo
// erro).
//  - Instala a biblioteca do stacktrace: npm install stacktrace-js
//  - Instala as definition files do stacktrace (para funcionar o autocomplete): npm install @types/stacktrace-js
@Injectable() // não coloque escopo nele pois ele já está no escopo do módulo (declarado em errors.module.ts)
export class GlobalErrorHandler implements ErrorHandler {

    constructor(
        // Não injete artefatos aqui pois o Angular tentará injetá-los logo na construção
        // deste error handler. Ou seja: se houver um erro no seu serviço, você terá um erro aqui.
        // O Injector é aceitável pois ele é nativo do Angular.
        private injector:Injector
    ) {}
    
    handleError(error: any): void {
        // Aqui você está usando uma maneira de injetar uma dependência meio na mão.
        const location = this.injector.get(LocationStrategy); // usado para capturar a url atual
        const userService = this.injector.get(UserService); // usado para capturar seu usuário logado
        const serverLogService = this.injector.get(ServerLogService);
        const router = this.injector.get(Router);

        const url = location instanceof PathLocationStrategy ? location.path() : '';

        // Não é necessariamente uma instância de Error.
        const message = error.message ? error.message : error.toString;

        // navega para uma página de erro - somente em ambiente de produção.
        if(environment.production) router.navigate(['/error']);
        
        // Lida com o stacktrace criado (manipula e envia para o servidor de log).
        StackTrace
            .fromError(error)
            .then(stackFrames => {
                const stackAsString = stackFrames // o stackFrame é uma stream com cada linha do stacktrace.
                    .map(sf => sf.toString()) 
                    .join('\n');
                // console.log(message);
                // console.log(stackAsString);
                console.log( {message, url, userName:userService.getUserName(),  stack: stackAsString } );

                // Faz a lógica de enviar o erro capturado para o servidor de log.
                serverLogService.log({ 
                    message, 
                    url, 
                    userName: userService.getUserName(), 
                    stack: stackAsString
                }
                ).subscribe(
                    () => console.log('Error logged on server'),
                    err => {
                        console.log(err);
                        console.log('Fail to send error log to server');
                    }
                )
            });
        
    }

}