import {Injectable} from '@angular/core';
import {MessageService} from 'primeng/api';
import {HttpErrorResponse} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class MsgService {

  constructor(private msg: MessageService) {
  }

  sucesso(msg = '', title = 'Atenção') {
    this.msg.add({severity: 'success', summary: title, detail: msg});
  }

  erro(msg = '', title = 'Atenção') {
    this.msg.add({severity: 'error', summary: title, detail: msg});
  }

  info(msg = '', title = 'info') {
    this.msg.add({severity: 'error', summary: title, detail: msg});
  }

  handle(errorResponse: any) {
    let msg: string;

    if (typeof errorResponse === 'string') {
      msg = errorResponse;

    } else if (errorResponse instanceof HttpErrorResponse
      && errorResponse.status >= 400 && errorResponse.status <= 499) {
      msg = 'Ocorreu um erro ao processar a sua solicitação';

      if (errorResponse.status === 400) {
        msg = 'Usuário ou Senha Inválidos';
      } else if (errorResponse.status === 403) {
        msg = 'Você não tem permissão para executar esta ação';
      }

      try {
        msg = errorResponse.error[0].mensagemUsuario;
      } catch (e) {
      }

    } else {
      msg = 'Erro ao processar serviço remoto. Tente novamente.';
      console.error('Ocorreu um erro', errorResponse);
    }

    this.erro(msg);
  }

}
