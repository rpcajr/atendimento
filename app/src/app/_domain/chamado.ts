import {JfFiles} from './jfFiles';
import {Comentario} from './comentario';

export class Chamado {
    id: number;
    titulo: string;
    versao: string;
    usuarioID: number;
    atribuicaoID: number;
    ocorrenciaID: number;
    contatoID: number;
    contatoNome: string;
    clienteID: number;
    datAbertura: Date;
    datFechamento: Date;
    detalhamento: string;
    sistemas: string;
    palavraChave: string;
    prioridade = 1;
    situacao = 2;
    atendimento: string;
    solucao: string;
    cliente: string;
    anexos: JfFiles[] = [];
    comentarios: Comentario[] = [];
}
