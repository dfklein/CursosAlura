import { Directive, ElementRef, Renderer, OnInit } from "@angular/core";
import { Photo } from "../../photo/photo";
import { Input } from "@angular/core";
import { UserService } from "../../../core/user/user.service";

@Directive({
    selector: '[photoOwnerOnly]'
})
export class PhotoOwnerOnlyDirective implements OnInit {
    @Input() owndedPhoto: Photo;
    
    constructor(
        private element: ElementRef<any>,
        private renderer: Renderer,
        private userService: UserService
    ) { }
        
        
    ngOnInit(): void {
        this.userService
            .getUser()
            .subscribe(user => {
                if(!user || user.id != this.owndedPhoto.userId) {
                    this.renderer.setElementStyle(this.element.nativeElement, 'display', 'none');
                }
            });
    }
}