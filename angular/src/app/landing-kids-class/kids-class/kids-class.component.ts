import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { KidsClassModel } from '../../models/kids-class-model';

@Component({
  selector: 'app-kids-class',
  templateUrl: './kids-class.component.html',
  styleUrls: ['./kids-class.component.css']
})
export class KidsClassComponent implements OnInit {

  @Input() kidsClassInput: KidsClassModel;

  constructor() { }

  ngOnInit() {
  }

}
