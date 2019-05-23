import { OnInit, Component } from "@angular/core";
import { Input } from "@angular/core";
import { AlertService } from "./alert.service";
import { Alert, AlertType } from "./alert";


// Implementado no header.component.html
@Component({
    templateUrl: './alert.component.html',
    selector: 'ap-alert'
})
export class AlertComponent implements OnInit {

    @Input() timeout = 3000;
    alerts: Alert[] = [];
    
    constructor(
        private alertService: AlertService
    ) {}
    ngOnInit(): void {
        this.alertService
            .getAlert()
            .subscribe(alert => {
                // Se emitir um alerta nulo, você limpa os seus alertas
                if(!alert) {
                    this.alerts = [];
                    return;
                }

                this.alerts.push(alert);
                // O setTimeout recebe uma função para ser executada depois de um
                // tempo determinado (informado no segundo parâmetro dele).
                setTimeout(() => this.removeAlert(alert), this.timeout);
            })
    }

    removeAlert(alertToRemove: Alert) {
        this.alerts = this.alerts.filter(alert => alert != alertToRemove)
    }

    getAlertClass(alert: Alert) {
        if(!alert) return '';

        switch (alert.alertType) {
            case AlertType.WARNING:
                return 'alert alert-warning';
            case AlertType.SUCCCESS:
                return 'alert alert-success';
            case AlertType.INFO:
                return 'alert alert-info';
            case AlertType.DANGER:
                return 'alert alert-danger';
        }
    }
}