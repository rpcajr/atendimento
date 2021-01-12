import {UserPerfil} from './user-perfil';
import {AtendimentoCliente} from './atendimento-cliente';

export class Atendimento {
  sessionId: string;
  perfil: UserPerfil;
  list: AtendimentoCliente[] = [];
  remove = false;
}
