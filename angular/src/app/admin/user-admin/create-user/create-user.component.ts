import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { NgForm } from '@angular/forms';

import * as fromApp from '../../../shared/app.reducers';
import * as fromUserAdmin from '../store/userAdmin.reducers';
import * as UserAdminActions from '../store/userAdmin.actions';
import { RoleDto } from '../../models/roleDto.model';
import { NewUserDto } from '../../models/newUserDto.model';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.css']
})
export class CreateUserComponent implements OnInit {

  roles: Array<RoleDto>;
  userAdminState: Observable<fromUserAdmin.State>;

  constructor(private store: Store<fromApp.AppState>) { }

  ngOnInit() {
    this.userAdminState = this.store.select('userAdmin');
    this.store.dispatch(new UserAdminActions.GetRoles());
    this.userAdminState.subscribe(res => {
      this.roles = res.roles;
    })
  }

  onCreateUser(form: NgForm) {
    let newUser = new NewUserDto(
      form.value.firstname,
      form.value.name,
      form.value.email,
      form.value.password,
      form.value.role, 
      null
    );
    this.store.dispatch(new UserAdminActions.CreateUser(newUser));
  }

}
