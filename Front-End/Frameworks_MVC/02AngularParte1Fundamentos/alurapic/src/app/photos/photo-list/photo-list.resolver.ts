import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

import { PhotoService } from '../photo/photo.service';
import { Observable } from 'rxjs';
import { Photo } from '../photo/photo';


// Um resolver é um objeto que é usado para garantir que uma rota só é ativada quando uma lógica é executada.
// É uma espécie de filtro que você pode usar para rotas específicas.
// Para utilizá-lo você deve ir no seu arquivo de declaração de rotas (app.routing.module.ts) e declarar
// em quais rotas você deseja aplicá-lo.
// Ele foi usado aqui porque ao carregar a sua aplicação você via a mensagem de resultado não encontrado
// antes da primeira renderização da sua tela, no intervalo de tempo necessário para a resposta da chamada
// do back-end chegar. Ao invés de chamar o serviço diretamente na sua listagem de fotos você passou a intermediar
// esta chamada com este resolver e depois disso ele só renderiza a sua tela depois de resolvida a lógica
// do método resolve (que é quem chama agora o back-end).
// Ver app.routing.module.ts com outros detalhes importantes
@Injectable({
    providedIn:'root'
})
export class PhotoListResolver implements Resolve<Observable<Photo[]>> {
    
    constructor(private service:PhotoService) {

    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const userName = route.params.userName;
        //return this.service.listFromUserName(userName);
        return this.service.listFromUserNamePaginated(userName, 1);
    }

}