import { Injectable } from "@angular/core";
import { HttpInterceptor } from "@angular/common/http";
import { HttpRequest } from "@angular/common/http";
import { HttpHandler } from "@angular/common/http";
import { Observable } from "rxjs";
import { HttpSentEvent } from "@angular/common/http";
import { HttpHeaderResponse } from "@angular/common/http";
import { HttpProgressEvent } from "@angular/common/http";
import { HttpResponse } from "@angular/common/http";
import { HttpUserEvent } from "@angular/common/http";
import { TokenService } from '../token/token.service';

// ********** O QUE É ISSO AQUI? **********
// Esta classe é como o filter que você usava no java. Aqui a idéia é criar um interceptador
// que vai verificar se o LocalStorage do navegador possui ou não o token. Caso possua, envia
// o mesmo para o back-end, contendo as permissões do usuário que solicitou a requisição.
@Injectable()
export class RequestInterceptor implements HttpInterceptor {

    constructor(private tokenService: TokenService) {}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpSentEvent
        | HttpHeaderResponse | HttpProgressEvent | HttpResponse<any> | HttpUserEvent<any>> {

        // Verifica se o LocalStorage possui o token. Caso exista, anexa ele ao x-access-token da
        // requisição.
        if(this.tokenService.hasToken()) {
            const token = this.tokenService.getToken();
            req = req.clone({
                setHeaders : {
                    'x-access-token': token
                }
            });
        }

        // IMPORTANTE: Você precisa configurar o Angular para utilizar requisições customizadas quando
        // você faz isso. Para tal, você precisou declarar um provider no módulo do interceptor 
        // (core.module.ts) indicando que é esta classe aqui que deve ser utilizada como interceptor.
        
        // Diz ao HttpHandler para continuar a requisição.
        // equivalente ao chain.doFilter();
        return next.handle(req);
    }
}