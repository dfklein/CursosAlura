import { Injectable } from '@angular/core';
import { SignUpService } from './sign-up.service';
import { AbstractControl } from '@angular/forms';
import { debounceTime, switchMap, map, first } from 'rxjs/operators';

// Inicialmente você disponibilizou este serviço para toda a aplicação. O modularizá-la você
// alterou quem disponibilizava esta injeção por ela ser utilizada apenas em um componente (sign-up.component.ts)
// No componente você o declarou como providers (ver sign-up.component.ts)
@Injectable(
    // { providedIn: 'root' }
)
export class UserNotTakenValidatorService {

    constructor(private signUpService: SignUpService) {}

    // Este método tem que retornar uma FUNÇÃO que acessa o serviço.
    checkUserNameTaken() {

            return (control: AbstractControl) => {
                return control
                    .valueChanges
                    // Executa a cada 300 milissegundos depois do último caractere digitado
                    .pipe(debounceTime(300))
                    // Chama o serviço que vai verificar se o userName existe (retorna um Observable)
                    .pipe(switchMap(userName =>
                        this.signUpService.checkUserNameTaken(userName)
                    ))
                    // Pega o resultado (isTaken, um booleano) e faz uma lógica para retornar no formato
                    // de um validador (que é um objeto javascript com um atributo de valor true quando
                    // a resposta é sim ou nulo quando a resposta for não.)
                    .pipe(map(isTaken => isTaken ? { userNameTaken: true } : null))
                    // Isto aqui é uma maneira de completar a operação, dizendo que o primeiro valor
                    // emitido é a resposta.
                    .pipe(first());
        }
    }
}