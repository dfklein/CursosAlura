import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Photo } from '../photo/photo';
import { PhotoService } from '../photo/photo.service';

@Component({
  selector: 'app-photo-list',
  templateUrl: './photo-list.component.html',
  styleUrls: ['./photo-list.component.css']
})
export class PhotoListComponent implements OnInit {

  photos: Photo[] = [];
  filter: string = '';
  hasMore: boolean = true;
  currentPage: number = 1;
  userName: string = '';

  constructor(
    private activatedRoute: ActivatedRoute,
    private photoService: PhotoService
  ) { }

  ngOnInit(): void {
    // this.userName = this.activatedRoute.snapshot.params.userName;

    // Você alterou o código acima para este abaixo porque da forma acima ele não entendia
    // como mudança de rota quando você alterava a parte parametrizada dela (por exemplo:
    // ir de user/flavio para user/almeida).
    // Da forma abaixo você se inscreve no evento de alteração de rotas e envia uma função
    // que é executada toda vez que a mesma acontece.
    this.activatedRoute.params.subscribe(param => {
      this.userName = param.userName;
      this.photos = this.activatedRoute.snapshot.data['photos'];
    })
  }

  load() {
    this.photoService
      .listFromUserPaginated(this.userName, ++this.currentPage)
      .subscribe(photos => {
        this.filter = '';
        this.photos = this.photos.concat(photos);
        if(!photos.length) this.hasMore = false;
      });
  }
}
