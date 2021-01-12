import {Component, Input, OnInit} from '@angular/core';
import {Atendimento} from '../../../_domain/atendimento';

@Component({
  selector: 'app-atendimento-view',
  templateUrl: './atendimento-view.component.html',
  styleUrls: ['./atendimento-view.component.css']
})
export class AtendimentoViewComponent implements OnInit {

  @Input() atendimento: Atendimento;
  imagem = '';

  constructor() {
  }

  ngOnInit() {
    this.imagem = this.atendimento.perfil.url;
  }

  setDefaultPic() {
    this.imagem = 'assets/images/profile.jpg';
  }


}
