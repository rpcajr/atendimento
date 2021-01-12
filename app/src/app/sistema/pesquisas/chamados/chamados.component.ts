import {ChangeDetectionStrategy, Component, Input, OnInit, ViewChild} from '@angular/core';
import {PesquisaService} from '../pesquisa.service';
import {catchError, finalize} from 'rxjs/operators';
import {HttpErrorResponse} from '@angular/common/http';
import {BehaviorSubject, Observable, Subscription, throwError} from 'rxjs';
import {MsgService} from '../../../shared/msg.service';
import {DialogService, LazyLoadEvent} from 'primeng/api';
import {JFUtil} from '../../../_util/jf.util';
import {PesquisaFilterComponent} from '../pesquisa-filter/pesquisa-filter.component';
import {JFFilter} from '../../../_domain/jffilter';
import {Table} from 'primeng/table';
import {CollectionViewer, DataSource} from '@angular/cdk/collections';
import {ChamadoPesquisa} from '../../../_domain/chamado.pesquisa';
import {ChangeEvent} from 'ngx-virtual-scroller';
import {DataView} from 'primeng/dataview';

@Component({
  selector: 'app-chamados',
  templateUrl: './chamados.component.html',
  styleUrls: ['./chamados.component.css'],
  providers: [DialogService]
})
export class ChamadosComponent implements OnInit {
  data: any;
  loading = false;
  @Input() filter = JFUtil.getFilter();
  @Input() hideFilter = false;
  @Input() ignoreInit = false;
  @ViewChild('table') table: DataView;

  constructor(private service: PesquisaService, private msg: MsgService, private dialogService: DialogService) {
  }

  ngOnInit() {
  }

  loadChamado() {
    if (this.ignoreInit) {
      return;
    }
    this.loading = true;
    this.service.getChamados(this.filter)
      .pipe(
        finalize(() => this.loading = false),
        catchError((res: HttpErrorResponse) => {
          this.msg.handle(res);
          return throwError(res);
        })
      )
      .subscribe(dat => {
        this.data = dat;
      });
  }

  imprimir() {
    this.loading = true;
    this.service.printChamados(this.filter)
      .pipe(
        finalize(() => this.loading = false),
        catchError((res: HttpErrorResponse) => {
          this.msg.handle(res);
          return throwError(res);
        })
      )
      .subscribe((data) => {
        const downloadURL = window.URL.createObjectURL(new Blob([data], {type: 'application/pdf'}));
        const link = document.createElement('a');
        link.href = downloadURL;
        link.download = name;
        link.click();
      });
  }

  aoMudarPagina(event: LazyLoadEvent) {
    this.filter.page = event.first / event.rows;
    this.loadChamado();
  }

  chamarFiltro() {
    const ref = this.dialogService.open(PesquisaFilterComponent, {
      header: 'Filtrar',
      data: {
        filter: this.filter
      },
      width: JFUtil.getWidthDialog(),
      closable: true
    });

    ref.onClose.subscribe((f: JFFilter) => {
      if (f) {
        this.filter = f;
        this.loadChamado();
        this.table.first = 0;
      }
    });
  }

}


