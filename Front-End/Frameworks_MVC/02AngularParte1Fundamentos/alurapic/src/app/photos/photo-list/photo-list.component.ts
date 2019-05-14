import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Photo } from '../photo/photo';
import { PhotoService } from '../photo/photo.service';

@Component({
  selector: 'app-photo-list',
  templateUrl: './photo-list.component.html',
  styleUrls: ['./photo-list.component.css']
})
export class PhotoListComponent implements OnInit { // o OnInit é uma interface que te obriga a implementar o ngOnInit()

  title = 'alurapic';
  // Note que no TypeScript para tipar uma variável a sintaxe é {variável:tipo}
  photos:Photo[] = [];
  filter:string = '';
  hasMore: boolean = true;
  currentPage:number = 1;
  userName:string = '';

  
  // Observe a implementação da camada de serviço feita em photo.service.ts
  constructor(
    private photoService: PhotoService,
    private activateRoute: ActivatedRoute // este objeto é da api de rotas. Ele traz a rota que está
                                          // ativa no momento e é utilizado para o uso
                                          // de variáveis passadas via url configuradas no roteamento
                                          // (consulte o módulo app.routing.module.ts)
  ) {

  }
  
  // ngOnInit() é um método que por padrão é executado automaticamente após a construção da sua classe
  // Por convenção é mais interessante você utilizar o construtor apenas para a injeção de dependências e
  // o ngOnInit() para execução de rotinas da construção do objeto.
  ngOnInit() : void {
      // Obtendo o parâmetro passado via url e configurado nas rotas (app.routing.module.ts)
      // Foi removido porque sua função foi substituída ao implementar o resolver para o carregamento da rota.
      // const userName = this.activateRoute.snapshot.params.userName;

      // O código abaixo chamava as fotos do back-end. Isso foi substituído pelo uso de um resolver
      //this.photoService
      //    .listFromUserName(userName)
      //    .subscribe(photos => { 
      //        console.log(photos[0].description);
      //        this.photos = photos; 
      //    });

      // Abaixo você recupera a configuração da sua rota ativa e procura nela um objeto photos que ela possui.
      // É como você utilizou o resolver (ver app.routing.module.ts e photo-list.resolver.ts)
      this.photos = this.activateRoute.snapshot.data.photos;
      // Outra sintaxe possível.
      // this.photos = this.activateRoute.snapshot.data['photos'];

      this.userName = this.activateRoute.snapshot.params.userName;
  }

  load() {
    this.photoService
      .listFromUserNamePaginated(this.userName, ++this.currentPage)
      .subscribe(photosCarregar => {
        this.filter = '';
        this.photos = this.photos.concat(photosCarregar); // isso é fazer um push de cada ítem da coleção retornada em photosCarregar 
        if(!photosCarregar.length) this.hasMore = false; // ou seja: se não vierem dados na resposta da requisição, hasMore é falso.
      });

  }

}
