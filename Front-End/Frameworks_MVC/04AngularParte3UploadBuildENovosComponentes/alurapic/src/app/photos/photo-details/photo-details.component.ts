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

@Component({
    templateUrl: './photo-details.component.html',
})
export class PhotoDetailsComponent implements OnInit { 

    photo$: Observable<Photo>;
    photoId: number;

    constructor(
        private route: ActivatedRoute,
        private photoService: PhotoService,
        private router: Router
    ) {}

    ngOnInit(): void {
        this.photoId = this.route.snapshot.params.photoId;

        this.photo$ = this.photoService.findById(this.photoId);

        this.photoService
            .getComments(this.photoId);

            
    }

    remove() {
        this.photoService
            .removePhoto(this.photoId)
            .subscribe(() => this.router.navigate(['']));
    }
}