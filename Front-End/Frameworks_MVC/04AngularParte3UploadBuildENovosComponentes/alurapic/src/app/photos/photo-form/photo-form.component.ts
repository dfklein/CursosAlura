import { Component, OnInit } from "@angular/core";

import { AuthService } from "../../core/auth/auth.service";
import { FormGroup, FormBuilder } from "@angular/forms";
import { Validators } from "@angular/forms";
import { PhotoService } from '../photo/photo.service';
import { Router } from '@angular/router';

@Component({
    selector: 'ap-photo-form',
    templateUrl: './photo-form.component.html',
})
export class PhotoFormComponent implements OnInit {

    photoForm: FormGroup;
    file: File;
    preview: string;

    constructor(
      private formBuilder: FormBuilder,
      private photoService: PhotoService,
      private router:Router) { }

    ngOnInit(): void { 

        this.photoForm = this.formBuilder.group({
            file: ['', Validators.required],
            description: ['', Validators.maxLength(300)],
            allowComments: [true]
        });
    }

    upload() {
      // const dados = this.photoForm.getRawValue();  // por causa da necessidade de obter o 
                                                      // binário do arquivo de upload, foi optado
                                                      // por se obter os valores de uma forma mais
                                                      // básica. Veja no template que o binário do
                                                      // arquivo é obtido pelo evento e enviado à 
                                                      // variável file.

      const description = this.photoForm.get('description').value;  
      const allowComments = this.photoForm.get('allowComments').value;  
      this.photoService
        .upload(description, allowComments, this.file)
        .subscribe(() => this.router.navigate(['']));

    }

    handleFile(file: File) {
        this.file = file;
        const reader = new FileReader();

        // Aqui você está passando configurações para o FileReader. No onload você está definindo um método
        // de callback. O readAsDataUrl vai definir que o que você está gerando é um Base64 (que é atribuído
        // à string preview no callback)
        reader.onload = (event:any) => this.preview = event.target.result;
        reader.readAsDataURL(file);
    }
}
