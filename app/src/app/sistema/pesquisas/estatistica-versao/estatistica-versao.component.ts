import {Component, OnInit} from '@angular/core';
import {PesquisaService} from '../pesquisa.service';

@Component({
  selector: 'app-estatistica-versao',
  templateUrl: './estatistica-versao.component.html',
  styleUrls: ['./estatistica-versao.component.css']
})
export class EstatisticaVersaoComponent implements OnInit {
  data: any;

  options: any;

  constructor(private service: PesquisaService) {
    this.data = {
      labels: [],
      datasets: [
        {
          data: [],
          backgroundColor: []
        }]
    };

    this.options = {
      title: {
        display: true,
        text: 'Versões',
        fontSize: 10
      },
      legend: {
        position: 'bottom'
      }
    };

  }

  getRandomColor() {
    const letters = '0123456789ABCDEF';
    let color = '#';
    for (let i = 0; i < 6; i++) {
      color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
  }

  ngOnInit() {
    this.service.getVeroes().subscribe(list => {
      for (const a of list) {
        this.data.labels.push(a.versao);
        this.data.datasets[0].data.push(a.total);
        this.data.datasets[0].backgroundColor.push(this.getRandomColor());
      }
    });
  }

}
