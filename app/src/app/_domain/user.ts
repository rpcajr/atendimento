export class User {
  id: number;
  nome: string;
  fone: string;
  email: string;
  login: string;
  senha: string;
  foto: string;
  inativo: boolean;
  setorID: number;
  permissoes = '';
  nascimento: Date;
}
