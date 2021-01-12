import {Contato} from './contato';

export class Cliente {
  id: number;
  codCliente: string;
  nome: string;
  fantasia: string;
  endereco: string;
  bairro: string;
  cidade: string;
  uf: string;
  cep: string;
  fone: string;
  fone2: string;
  email: string;
  versao = '';
  contatos: Contato[];
}
