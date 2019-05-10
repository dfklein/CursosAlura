import { Component, OnInit } from '@angular/core';
import { PhotoService } from '../photo/photo.service';
import { Photo } from '../photo/photo';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-photo-list',
  templateUrl: './photo-list.component.html',
  styleUrls: ['./photo-list.component.css']
})
export class PhotoListComponent implements OnInit { // o OnInit é uma interface que te obriga a implementar o ngOnInit()

  title = 'alurapic';

  // Note que no TypeScript para tipar uma variável a sintaxe é {variável:tipo}
  photos:Photo[] = [];
  
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
      const userName = this.activateRoute.snapshot.params.userName;

      this.photoService
          .listFromUserName(userName)
          .subscribe(photos => { 
              console.log(photos[0].description);
              this.photos = photos; 
          });

  }

}
