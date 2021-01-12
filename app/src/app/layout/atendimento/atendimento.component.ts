import {Component, OnDestroy, OnInit} from '@angular/core';
import {Atendimento} from '../../_domain/atendimento';
import {RxStompService} from '@stomp/ng2-stompjs';
import {Message} from '@stomp/stompjs';
import {Subscription} from 'rxjs';
import {AppLayoutSidebarComponent} from '../app.layout.sidebar.component';
import {SetorService} from '../../sistema/setor/setor.service';
import {AppLayoutTopbarComponent} from '../top/app.layout.topbar.component';
import {TopbarService} from '../top/topbar.service';
import {UserPerfil} from '../../_domain/user-perfil';
import {JFUtil} from '../../_util/jf.util';

@Component({
  selector: 'app-atendimento',
  templateUrl: './atendimento.component.html',
  styleUrls: ['./atendimento.component.css']
})
export class AtendimentoComponent implements OnInit, OnDestroy {
  atendimentos: Atendimento[] = [];
  u: UserPerfil;
  desconectado = false;
  jaConectou = false;
  private topicSubscription: Subscription[] = [];

  constructor(private rxStompService: RxStompService,
              private setorService: SetorService,
              public sBar: AppLayoutSidebarComponent,
              public topService: TopbarService) {
  }

  ngOnInit() {
    this.topService.emiterPerfil.subscribe(u => {
      this.u = u;
      this.rxStompService.connectionState$.subscribe(v => {
        this.desconectado = false;
        if (v === 0) {
          this.sBar.statusAtendimento = 'Conectando...';
        } else if (v === 1 && !this.jaConectou) {
          this.jaConectou = true;
          this.sBar.statusAtendimento = 'Atendendo';
          this.push();
        } else if (v === 2) {
          this.sBar.statusAtendimento = 'Desconectando...';
        } else if (v === 3) {
          this.sBar.statusAtendimento = 'Desconectado';
          this.desconectado = true;
        }
      });

    });
  }

  push() {
    this.setorService.getSetoresDependentes().subscribe(list => {
      for (const s of list) {
        this.addWatch(s.nome);
      }
      this.addPerfil();
    });
  }

  private addPerfil() {
    console.log('add Perfil', this.u);
    if (this.u === null) {
      this.u = JFUtil.getUser();
      this.addPerfil();
      return;
    }
    this.rxStompService.publish({destination: `/app/add.perfil/${this.u.setor}`, body: JSON.stringify(this.u)});
  }

  private addWatch(setor: string) {
    console.log('watch', setor);
    this.topicSubscription.push(this.rxStompService.watch(`/topic/atendimento/${setor}`).subscribe((message: Message) => {
      const val = JSON.parse(message.body);
      if (!Array.isArray(val)) {
        this.recebeAtendimento(val);
      } else {
        this.recebeListaAtendimento(val);
      }
    }));
  }

  recebeListaAtendimento(atendimentos: Atendimento[]) {
    for (const a of atendimentos) {
      const index = this.atendimentos.findIndex(i => i.sessionId === a.sessionId);
      if (index === -1) {
        this.atendimentos.push(a);
      }
    }
    this.sBar.totalUsuarios = this.atendimentos.length;
  }

  recebeAtendimento(atendimento: Atendimento) {
    const index = this.atendimentos.findIndex(i => i.sessionId === atendimento.sessionId);
    if (index === -1) {
      return;
    }
    if (atendimento.remove) {
      this.atendimentos.splice(index, 1);
      this.sBar.totalUsuarios = this.atendimentos.length;
      return;
    }
    this.atendimentos[index].list = atendimento.list;
  }

  ngOnDestroy(): void {
    for (const subs of this.topicSubscription) {
      subs.unsubscribe();
    }
    this.rxStompService.deactivate();
  }

  conectar() {
    this.rxStompService.activate();
  }
}
