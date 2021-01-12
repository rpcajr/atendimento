import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-jf-rodape',
  templateUrl: './jf-rodape.component.html',
  styleUrls: ['./jf-rodape.component.css']
})
export class JfRodapeComponent implements OnInit {
  @Input() total = 0;

  constructor() {
  }

  ngOnInit() {
  }

}
