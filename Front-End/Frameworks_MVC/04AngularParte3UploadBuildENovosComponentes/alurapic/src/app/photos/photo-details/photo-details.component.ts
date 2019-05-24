import { Component } from "@angular/core";
import { ActivatedRouteSnapshot, ActivatedRoute, Router } from "@angular/router";
import { OnInit } from "@angular/core";
import { Photo } from "../photo/photo";
import { PhotoService } from "../photo/photo.service";
import { Observable } from "rxjs";
import { FormGroup, FormBuilder } from "@angular/forms";
import { Validators } from "@angular/forms";
import { PhotoCommentsComponent } from "./photo-comments/photo-comments.component";
import { PhotoComment } from "../photo/photo-comment";
import { AlertService } from "../../shared/components/alert/alert.service";
import { UserService } from "../../core/user/user.service";

@Component({
    templateUrl: './photo-details.component.html',
})
export class PhotoDetailsComponent implements OnInit { 

    photo$: Observable<Photo>;
    photoId: number;

    constructor(
        private route: ActivatedRoute,
        private photoService: PhotoService,
        private router: Router,
        private alertService: AlertService,
        private userService: UserService
    ) {}

    ngOnInit(): void {
        this.photoId = this.route.snapshot.params.photoId;

        this.photo$ = this.photoService.findById(this.photoId);

        this.photo$.subscribe(() => {}, err => {
            this.router.navigate(['not-found']);
        })

        this.photoService
            .getComments(this.photoId);

            
    }

    remove() {
        this.photoService
            .removePhoto(this.photoId)
            .subscribe(() => {
                // Cuidado com a ordem de execução abaixo. Não funciona se inverter
                this.alertService.success('Photo removed!', true);
                this.router.navigate(['/user', this.userService.getUserName() ]);
        
            },
                err => {
                    console.log(err);
                    this.alertService.warning('Photo could not be deleted!', true);
                }
            );
    }

    like(photo: Photo) {
        this.photoService.like(photo.id)
            .subscribe(liked => {
                if(liked) {
                    this.photo$ = this.photoService.findById(photo.id);
                } else {

                }
            })
    }
}