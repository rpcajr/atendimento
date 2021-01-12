import {FormGroup} from '@angular/forms';
import {UserPerfil} from '../_domain/user-perfil';
import {JFFilter} from '../_domain/jffilter';
import {Ocorrencia} from '../_domain/ocorrencia';

export class JFUtil {

  static TOKEN_NAME = 'token';

  static verificaValidacoesForm(formGroup: FormGroup) {
    Object.keys(formGroup.controls).forEach(campo => {
      const controle = formGroup.get(campo);
      controle.markAsDirty();
      if (controle instanceof FormGroup) {
        this.verificaValidacoesForm(controle);
      }
    });
  }

  static focus(elem) {
    setTimeout(() => elem.nativeElement.focus(), 200);
  }

  static isDesktop() {
    return window.innerWidth > 1024;
  }

  static getWidthDialog(): string {
    if (JFUtil.isDesktop()) {
      return '40%';
    } else {
      return '100%';
    }
  }

  static setUser(userPerfil: UserPerfil) {
    localStorage.setItem('user', JSON.stringify(userPerfil));
  }

  static setFilter(filter: JFFilter) {
    localStorage.setItem('filter', JSON.stringify(filter));
  }

  static setOcorrencia(oco: number) {
    localStorage.setItem('ocorrencia', JSON.stringify(oco));
  }

  static getOcorrencia() {
    return JSON.parse(localStorage.getItem('ocorrencia')) as number;
  }

  static getFilter() {
    const filter = JSON.parse(localStorage.getItem('filter')) as JFFilter;
    if (filter === null) {
      return new JFFilter();
    }
    if (filter.datInicial) {
      filter.datInicial = new Date(filter.datInicial);
    }

    if (filter.datFinal) {
      filter.datFinal = new Date(filter.datFinal);
    }
    return filter;
  }

  static getUser(): UserPerfil {
    return JSON.parse(localStorage.getItem('user')) as UserPerfil;
  }

  static getFormatacaoData(): any {
    return {
      firstDayOfWeek: 0,
      dayNames: ['Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta', 'Sábado'],
      dayNamesShort: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab'],
      dayNamesMin: ['D', 'S', 'T', 'Q', 'Q', 'S', 'S'],
      monthNames: ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho',
        'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'],
      monthNamesShort: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
      today: 'Hoje',
      clear: 'Limpar',
      dateFormat: 'dd/mm/yy'
    };
  }

  static getSituacoes() {
    return [
      {label: 'Pendente', value: 2},
      {label: 'Resolvida', value: 0}
    ];
  }

  static getPrioridades() {
    return [
      {label: 'Baixa', value: 0},
      {label: 'Normal', value: 1},
      {label: 'Alta', value: 2},
      {label: 'Urgente', value: 3},
      {label: 'Imediata', value: 4}
    ];
  }

  static getAtendimentos() {
    return [
      {label: '', value: '0', icon: 'fa fa-phone'},
      {label: '', value: '1', icon: 'fa fa-whatsapp'},
      {label: '', value: '2', icon: 'fa fa-skype'},
      {label: '', value: '3', icon: 'fa fa-envelope-o'},
      {label: '', value: '4', icon: 'fa fa-facebook-official'}
    ];
  }

  static getSistemas() {
    return [
      {label: '', value: null},
      {label: 'POSSEIDON', value: 'POSSEIDON'},
      {label: 'ZEUS', value: 'ZEUS'},
      {label: 'CENTAURUS', value: 'CENTAURUS'},
      {label: 'HERMES', value: 'HERMES'},
      {label: 'ORION', value: 'ORION'},
      {label: 'PEGASUS', value: 'PEGASUS'},
      {label: 'TRITAO', value: 'TRITAO'},
      {label: 'NINFAS', value: 'NINFAS'},
      {label: 'PROTEUS', value: 'PROTEUS'},
      {label: 'OCEANO', value: 'OCEANO'},
      {label: 'ASSOCIAÇÕES', value: 'ASSOCIAÇÕES'},
      {label: 'ATUALIZA', value: 'ATUALIZA'},
      {label: 'HERMES R.A', value: 'HERMES R.A'},
      {label: 'DEMETER', value: 'DEMETER'},
      {label: 'POSTO NET', value: 'POSTONET'},
      {label: 'SFAC', value: 'SFAC'},
      {label: 'ATUALIZA', value: 'ATUALIZA'},
      {label: 'TODOS', value: 'TODOS'}];
  }

  static getDescricaoSituacao(i: number): string {
    if (i === 0) {
      return 'Resolvida';
    } else if (i === 1) {
      return 'Andamento';
    } else {
      return 'Pendente';
    }
  }

  static getUF() {
    return [{label: '', value: null}, {label: 'AC', value: 'AC'}, {label: 'AL', value: 'AL'}, {
      label: 'AP',
      value: 'AP'
    },
      {label: 'AM', value: 'AM'}, {label: 'BA', value: 'BA'}, {label: 'CE', value: 'CE'}, {label: 'DF', value: 'DF'},
      {label: 'ES', value: 'ES'}, {label: 'GO', value: 'GO'}, {label: 'MA', value: 'MA'}, {label: 'MT', value: 'MT'},
      {label: 'MS', value: 'MS'}, {label: 'MG', value: 'MG'}, {label: 'PA', value: 'PA'}, {label: 'PB', value: 'PB'},
      {label: 'PR', value: 'PR'}, {label: 'PE', value: 'PE'}, {label: 'PI', value: 'PI'}, {label: 'RJ', value: 'RJ'},
      {label: 'RN', value: 'RN'}, {label: 'RS', value: 'RS'}, {label: 'RO', value: 'RO'}, {label: 'RR', value: 'RR'},
      {label: 'SC', value: 'SC'}, {label: 'SP', value: 'SP'}, {label: 'SE', value: 'SE'}, {label: 'TO', value: 'TO'}];
  }
}
