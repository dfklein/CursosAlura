import { ValidatorFn, FormGroup } from '@angular/forms';

export const userNamePasswordValidator:ValidatorFn = (formGroup: FormGroup) => {

    const userName = formGroup.get('userName').value;
    const password = formGroup.get('password').value;

    // testa se os campos não estão em branco.
    if(userName.trim() + password.trim()) {
        return userName != password 
            // retornar nulo é o equivalente a validado.
            ? null 
            : { userNamePassword: true };
    } else {
        // retornar nulo é o equivalente a validado.
        return null;
    }
};
