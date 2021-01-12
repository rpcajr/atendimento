import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

export class CrudService<T> {

  constructor(protected http: HttpClient, protected url: string) {
  }

  getList(): Observable<T[]> {
    return this.http.get<T[]>(this.url);
  }

  getTotal(): Observable<number> {
    return this.http.get<number>(`${this.url}/total`);
  }

  get(id: number): Observable<T> {
    return this.http.get<T>(`${this.url}/${id}`);
  }

  delete(codigo: number): Promise<void> {
    return this.http.delete(`${this.url}/${codigo}`)
      .toPromise()
      .then(() => null);
  }

  set(u: T): Observable<T> {
    return this.http.post<T>(this.url, u);
  }

}
