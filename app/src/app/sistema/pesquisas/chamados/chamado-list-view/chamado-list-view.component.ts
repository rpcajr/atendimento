import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ChamadoPesquisa} from '../../../../_domain/chamado.pesquisa';
import {JFUtil} from '../../../../_util/jf.util';
import {DialogService} from 'primeng/api';
import {TicketComponent} from '../../../ticket/ticket.component';
import {VisaoGeralComponent} from '../../../visao-geral/visao-geral.component';
import {Contato} from '../../../../_domain/contato';
import {Chamado} from '../../../../_domain/chamado';

@Component({
  selector: 'app-chamado-list-view',
  templateUrl: './chamado-list-view.component.html',
  styleUrls: ['./chamado-list-view.component.css']
})
export class ChamadoListViewComponent implements OnInit {
  @Input() chamado: ChamadoPesquisa;
  @Output() update = new EventEmitter();

  constructor(private dialogService: DialogService) {
  }

  ngOnInit() {
  }

  getSituacao(i: number) {
    return JFUtil.getDescricaoSituacao(i);
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
