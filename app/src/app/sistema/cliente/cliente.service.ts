import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {Cliente} from '../../_domain/cliente';
import {CrudService} from '../../shared/crud-service';
import {Observable} from 'rxjs';
import {Contato} from '../../_domain/contato';

@Injectable({
  providedIn: 'root'
})
export class ClienteService extends CrudService<Cliente> {

  pathSistema = '';

  constructor(protected http: HttpClient) {
    super(http, `${environment.API_BASE_URL}api/clientes`);
    this.pathSistema = `${environment.API_BASE_URL}api/sistemas`;
  }

  getPorCodCliente(codCliente: string): Observable<Cliente> {
    return this.http.get<Cliente>(`${this.url}/cod_cliente/${codCliente}`);
  }

  getSistemas(codCliente: string): Observable<any> {
    return this.http.get<any>(`${this.pathSistema}/${codCliente}`);
  }

  getSistemasDTO(codCliente: string): Observable<string[]> {
    return this.http.get<string[]>(`${this.pathSistema}/dto/${codCliente}`);
  }

  getClientePesquisa(query: string): Promise<Cliente[]> {
    return this.http.get<Cliente[]>(`${this.url}/pesquisa/${query}`)
      .toPromise();
  }

  getClientePesquisaVersao(versao: string): Promise<Cliente[]> {
    return this.http.get<Cliente[]>(`${this.url}/versao/${versao}`)
      .toPromise();
  }

  getContatos(id: number): Promise<Contato[]> {
    return this.http.get<Contato[]>(`${this.url}/contatos/${id}`)
      .toPromise();
  }

  setContato(id: number, contato: Contato): Promise<Contato> {
    return this.http.post<Contato>(`${this.url}/contatos/${id}`, contato).toPromise();
  }



}
