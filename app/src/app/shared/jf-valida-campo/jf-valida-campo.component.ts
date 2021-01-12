import {Component, Input, OnInit} from '@angular/core';
import {FormControl} from '@angular/forms';

@Component({
  selector: 'app-jf-valida-campo',
  templateUrl: './jf-valida-campo.component.html',
  styleUrls: ['./jf-valida-campo.component.css']
})
export class JfValidaCampoComponent {

  @Input() error: string;
  @Input() control: FormControl;
  @Input() text: string;

  temErro(): boolean {
    return this.control.hasError(this.error) && this.control.dirty;
  }


}
