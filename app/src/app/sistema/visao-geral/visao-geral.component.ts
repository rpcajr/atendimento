import {Component, HostListener, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {Chamado} from '../../_domain/chamado';
import {TabView} from 'primeng/primeng';
import {DatePipe} from '@angular/common';
import {MsgService} from '../../shared/msg.service';
import {RxStompService} from '@stomp/ng2-stompjs';
import {JFUtil} from '../../_util/jf.util';
import {AtendimentoCliente} from '../../_domain/atendimento-cliente';
import {TopbarService} from '../../layout/top/topbar.service';
import {UserPerfil} from '../../_domain/user-perfil';

@Component({
  selector: 'app-visao-geral',
  templateUrl: './visao-geral.component.html',
  styleUrls: ['./visao-geral.component.css']
})
export class VisaoGeralComponent implements OnInit, OnDestroy {
  items: Chamado[] = [];
  index = 0;
  @ViewChild('tabView') tabView: TabView;

  constructor(private msg: MsgService, private rxStompService: RxStompService) {
  }

  ngOnInit() {

  }

  novo() {
    if (this.items.length > 6) {
      this.msg.erro('Não é Possível criar mais de 6 atendimentos simutaneos');
      return;
    }

    const chamado = new Chamado();
    chamado.cliente = new DatePipe('en-US').transform(new Date(), 'HH:mm:ss');
    this.items.push(chamado);
    this.callWebSocket();
    setTimeout(() => this.index = this.items.length, 400);
  }

  handleClose(e: any) {
    this.items.splice(e.index - 1, 1);
    this.tabView.activeIndexChange.emit(e.index - 1);
  }

  fecahrAba(i: number) {
    this.items.splice(i, 1);
    this.callWebSocket();
    setTimeout(() => this.index = this.items.length, 300);
  }

  @HostListener('document:keyup', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent): void {
    if (event.key === ' ' && event.ctrlKey) {
      this.novo();
    }

  }

  callWebSocket() {
    let user = JFUtil.getUser();

    if (!user) {
      user = JFUtil.getUser();
      return;
    }

    const list: AtendimentoCliente[] = [];
    for (const a of this.items) {
      const ac = new AtendimentoCliente();
      ac.nome = a.cliente;
      ac.contato = a.contatoNome;
      ac.tipo = a.atendimento;
      list.push(ac);
    }

    this.rxStompService.publish({
      destination: `/app/call.atendimento/${user.setor}`,
      body: JSON.stringify(list)
    });
  }

  ngOnDestroy(): void {
    this.items = [];
    this.callWebSocket();
  }

}
