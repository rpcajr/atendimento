import {Component, OnInit} from '@angular/core';
import {BackupService} from './backup.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-backup',
  templateUrl: './backup.component.html',
  styleUrls: ['./backup.component.css']
})
export class BackupComponent implements OnInit {

  list: any;

  constructor(private service: BackupService, private router: Router) {
  }

  ngOnInit() {
    this.service.getList().subscribe(l => this.list = l);
  }

  selecionar(event) {
    this.router.navigate(['/principal/sistema/cliente/', event.data.codCliente]);
  }

}
