import {Component, Input, OnInit} from '@angular/core';
import {Chamado} from '../../../_domain/chamado';
import {TicketService} from '../ticket.service';
import {JfFiles} from '../../../_domain/jfFiles';

@Component({
  selector: 'app-anexos',
  templateUrl: './anexos.component.html',
  styleUrls: ['./anexos.component.css']
})
export class AnexosComponent implements OnInit {

  @Input() chamado: Chamado;
  display = false;

  constructor(private ticketService: TicketService) {
  }

  ngOnInit() {
  }

  showDialog() {
    this.display = true;
  }

  uploadAuto(event: any) {
    const formData: any = new FormData();
    const files = event.target.files;
    for (const f of files) {
      formData.append('files', f, f.name);
    }
    this.ticketService.uploadFiles(formData).subscribe(list => {
      this.chamado.anexos.push(...list);
      console.log(this.chamado.anexos);
    });
  }

  remove(rowData: JfFiles) {
    const index = this.chamado.anexos.findIndex(i => i.nome === rowData.nome);
    this.chamado.anexos.splice(index, 1);
  }
}
