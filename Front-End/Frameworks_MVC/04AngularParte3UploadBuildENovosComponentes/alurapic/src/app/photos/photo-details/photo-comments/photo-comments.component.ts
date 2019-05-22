import { Component, OnInit, Input } from '@angular/core';
import { Observable } from 'rxjs';
import { FormGroup } from '@angular/forms';
import { Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { switchMap, tap } from 'rxjs/operators'

import { Photo } from '../../photo/photo';
import { PhotoService } from '../../photo/photo.service';
import { PhotoComment } from '../../photo/photo-comment';

@Component({
  selector: 'ap-photo-comments',
  templateUrl: './photo-comments.component.html',
  styleUrls: ['./photo-comments.component.css']
})
export class PhotoCommentsComponent implements OnInit {

 
  @Input() photoId: number;
  commentForm: FormGroup;
  comments$: Observable<PhotoComment[]>;

  constructor(
    private photoService: PhotoService,
    private formBuilder: FormBuilder
  ) {}

  ngOnInit() {
    this.comments$ = this.photoService.getComments(this.photoId);

    this.commentForm = this.formBuilder.group({
      comment: ['', Validators.maxLength(300)]
    })  
  }

  save() {
    const comment = this.commentForm.get('comment').value as string;
//    this.photoService
//      .addComments(this.photoId, comment)
//      // Isso aqui te permite em uma stream consumir o objeto gerado a partir de um outro consumo.
//      // Então você cadenciou uma chamada assíncrona para recarregar os comentários (getComments())
//      // só depois de ter persistido (addComment) o comentário que você quer. O subscribe passou a
//      // ser o de getComments, e não de addComments. 
//      .pipe(switchMap(() => this.photoService.getComments(this.photoId)))
//      .subscribe(() => {
//        this.commentForm.reset();
//        alert('Comentário adicionado com sucesso.')
//      });

    // O código acima foi substituído pelo abaixo, mas eles fazem a mesma coisa:
    this.comments$ = this.photoService
      .addComments(this.photoId, comment)
      .pipe(switchMap(() => this.photoService.getComments(this.photoId)))
      // O tap() vai executar um código logo antes de retornar de fato o observable
      .pipe(tap(() => {
        this.commentForm.reset();
        // alert('Comentário adicionado com sucesso.')
      }));
  }
}
