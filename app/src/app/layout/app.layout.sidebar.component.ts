import {Component, OnInit} from '@angular/core';
import {AppLayoutComponent} from './app.layout.component';
import {BackupService} from './backup/backup.service';

@Component({
  selector: 'app-layout-sidebar',
  templateUrl: './app.layout.sidebar.component.html'
})
export class AppLayoutSidebarComponent implements OnInit {
  totalUsuarios = 0;
  totalClienteSemBackup: any;
  statusAtendimento = '';

  constructor(public app: AppLayoutComponent, private backupService: BackupService) {
  }

  ngOnInit(): void {
    this.backupService.getTotal().subscribe(t => this.totalClienteSemBackup = t);
  }

}
