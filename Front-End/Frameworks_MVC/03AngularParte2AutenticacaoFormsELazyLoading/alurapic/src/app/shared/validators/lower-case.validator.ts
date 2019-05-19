import { AbstractControl } from '@angular/forms';

export function lowerCaseValidator(control: AbstractControl) {
    // control.value.trim() equivale a verificar se não está vazio
    if(control.value.trim() && !/^[a-z0-9_\-]+$/.test(control.value)) {
        // Se houver erro de validação, você retorna uma propriedade com o nome do valor que será verificado e o valor true
        // para ela.
        // Ver a aplicação no sign-up.component.html (campo userName)
        return { lowerCase: true }
    }
    // Se não houver erro de validação, retorna null
    return null;
}