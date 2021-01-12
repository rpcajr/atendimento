import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ChamadoPesquisa} from '../../../../_domain/chamado.pesquisa';
import {TicketComponent} from '../../../ticket/ticket.component';
import {Chamado} from '../../../../_domain/chamado';
import {DialogService} from 'primeng/api';

@Component({
  selector: 'app-chamado-grid-view',
  templateUrl: './chamado-grid-view.component.html',
  styleUrls: ['./chamado-grid-view.component.css']
})
export class ChamadoGridViewComponent implements OnInit {
  @Input() chamado: ChamadoPesquisa;
  @Output() update = new EventEmitter();
  imagem: string;

  constructor(private dialogService: DialogService) { }

  ngOnInit() {
    this.imagem = this.chamado.imagemUsuario;
  }

  setDefaultPic() {
    this.imagem = 'assets/images/profile.jpg';
  }

  verChamado() {
    const ref = this.dialogService.open(TicketComponent, {
      header: 'Atualizar Chamado',
      width: '80%',
      closable: true,
      data: {
        chamadoID: this.chamado.id
      }
    });

    ref.onClose.subscribe((c: Chamado) => {
      if (c) {
        this.update.emit();
      }
    });

  }
}
