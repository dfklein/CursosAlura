import { Injectable } from "@angular/core";
import { Subject } from "rxjs";
import { Alert, AlertType } from "./alert";
import { Router, NavigationStart } from "@angular/router";

// Este é um serviço de notificações global na aplicação
// Implementado no header.component.html
@Injectable({ providedIn: 'root' })
export class AlertService {

    alertSubject: Subject<Alert> = new Subject<Alert>();

    keepAfterRouteChange = false;

    constructor(private router: Router) {
        // Este mecanismo faz com que você verifique se a ação gerada
        // houve navegação. Se o valor de keepAfterRouteChange for false
        // você diz que ele limpa todas as suas mensagens de navegação. Ou
        // seja: você devide se a mensagem de notificação deve permanecer ativa
        // ou não caso haja navegação durante o tempo de exibição da mensagem.
        // Se passado o true, o componente de mensagens será exibido somente na
        // próxima navegação e por 3 segundos (se antes dos 3 segundos você fizer
        // mais uma navegação, ele some)
        router.events.subscribe(event => {
            if(event instanceof NavigationStart) {
                if(this.keepAfterRouteChange) {
                    this.keepAfterRouteChange = false;
                } else {
                    this.clear();
                }
            }
        });
    }

    success(message: string, keepAfterRouteChange:boolean = false) {
        this.alert(AlertType.SUCCCESS, message, keepAfterRouteChange);
    }


    warning(message: string, keepAfterRouteChange:boolean = false) {
        this.alert(AlertType.WARNING, message, keepAfterRouteChange);
    }

    
    danger(message: string, keepAfterRouteChange:boolean = false) {
        this.alert(AlertType.DANGER, message, keepAfterRouteChange);
    }

    
    info(message: string, keepAfterRouteChange:boolean = false) {
        this.alert(AlertType.INFO, message, keepAfterRouteChange);
    }

    private alert(alertType: AlertType, message: string, keepAfterRouteChange:boolean = false) {
        this.keepAfterRouteChange = keepAfterRouteChange;
        this.alertSubject.next(new Alert(alertType, message));
    }

    // Isto é para quem quer receber a notificação de mensagem, que será
    // de interesse do componente de alertas.
    getAlert() {
        return this.alertSubject.asObservable();
    }

    clear() {
        this.alertSubject.next(null);
    }
}