import {Component, Input, OnInit} from '@angular/core';
import {Comentario} from '../../../_domain/comentario';
import {DomSanitizer} from '@angular/platform-browser';

@Component({
  selector: 'app-comentario-usuario',
  templateUrl: './comentario-usuario.component.html',
  styleUrls: ['./comentario-usuario.component.css']
})
export class ComentarioUsuarioComponent implements OnInit {

  imagem: string;
  @Input() comentario: Comentario;
  html: any;

  constructor(public sanitizer: DomSanitizer) {
  }

  ngOnInit() {
    this.imagem = this.comentario.usuario.url;
    this.html = this.sanitizer.bypassSecurityTrustHtml(this.comentario.comentario);
  }

  setDefaultPic() {
    this.imagem = 'assets/images/profile.jpg';
  }

}
