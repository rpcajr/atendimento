import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {Observable} from 'rxjs';
import {JFFilter} from '../../_domain/jffilter';

@Injectable({
  providedIn: 'root'
})
export class PesquisaService {
  url: string;

  constructor(protected http: HttpClient) {
    this.url = `${environment.API_BASE_URL}api`;
  }

  getVeroes(): Observable<any> {
    return this.http.get(`${this.url}/estatisticas/versao`);
  }

  getChamados(filter: JFFilter): Observable<any> {

    const params = this.getParametrosDoFiltro(filter);

    return this.http.get(`${this.url}/chamados`, {params});
  }

  private getParametrosDoFiltro(filter: JFFilter) {
    let params = new HttpParams({
      fromObject: {
        page: filter.page.toString(),
        size: filter.size.toString()
      }
    });

    const httpOptions = {
      responseType: 'blob' as 'json',
    };

    if (filter.situacao && filter.situacao.length > 0) {
      params = params.append('situacao', filter.situacao.join(', '));
    }

    if (filter.cliente) {
      params = params.append('clienteID', filter.cliente.id.toString());
    }

    if (filter.datInicial) {
      params = params.append('datInicial', filter.datInicial.toString());
    }

    if (filter.datFinal) {
      params = params.append('datFinal', filter.datFinal.toString());
    }

    if (filter.ocorrencia) {
      params = params.append('ocorrenciaID', filter.ocorrencia.id.toString());
    }

    if (filter.atribuicaoID) {
      params = params.append('atribuidoID', filter.atribuicaoID.id.toString());
    }

    if (filter.versao) {
      params = params.append('versao', filter.versao);
    }

    if (filter.sistema) {
      params = params.append('sistema', filter.sistema);
    }
    return params;
  }

  printChamados(filter: JFFilter): Observable<any> {
    const params = this.getParametrosDoFiltro(filter);
    return this.http.get<any>(`${this.url}/chamados/relatorio`, {params, responseType: 'blob' as 'json'});
  }

}
