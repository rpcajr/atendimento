import {Cliente} from './cliente';
import {Ocorrencia} from './ocorrencia';
import {UserPerfil} from './user-perfil';

export class JFFilter {
  datInicial: Date;
  datFinal: Date;
  cliente: Cliente;
  ocorrencia: Ocorrencia;
  versao: string;
  situacao: number[] = [2];
  sistema: string;
  atribuicaoID: UserPerfil;
  size = 10;
  page = 0;
}
