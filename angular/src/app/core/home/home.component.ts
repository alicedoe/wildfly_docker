import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { NgForm } from '@angular/forms';

import * as fromApp from '../../store/app.reducers';
import * as SchoolActions from '../../shared/schools/store/school.actions';
import { UserDto } from '../../shared/auth/userDto.model';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  userDto: UserDto;

  constructor(private store: Store<fromApp.AppState>) {
  }


  ngOnInit() {
    this.store.dispatch(new SchoolActions.GetSchool(1));
  }


}
