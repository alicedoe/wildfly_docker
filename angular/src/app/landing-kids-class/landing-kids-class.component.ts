import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { KidsClassModel } from '../models/kids-class-model';

@Component({
  selector: 'app-landing-kids-class',
  templateUrl: './landing-kids-class.component.html',
  styleUrls: ['./landing-kids-class.component.css']
})
export class LandingKidsClassComponent implements OnInit {

  @Output() classSelected = new EventEmitter<KidsClassModel>();

  kidsClasses : KidsClassModel[] = [ 
    new KidsClassModel(1,'6ème A'),
    new KidsClassModel(2,'5ème A')
   ];

  constructor() { }

  ngOnInit() {

  }

  selectClass(kidsClassEl:KidsClassModel) {
    console.log(kidsClassEl);
  }

}
