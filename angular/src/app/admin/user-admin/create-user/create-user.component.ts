import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { NgForm, FormGroup, FormControl, Validators } from '@angular/forms';

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
  userForm: FormGroup;

  constructor(private store: Store<fromApp.AppState>) { }

  ngOnInit() {
    this.userAdminState = this.store.select('userAdmin');
    this.store.dispatch(new UserAdminActions.GetRoles());
    this.userAdminState.subscribe(res => {
      this.roles = res.roles;
    })

    this.userForm = new FormGroup({
      'name': new FormControl( '', Validators.required ),
      'firstname': new FormControl( '', Validators.required ),
      'email': new FormControl( '', [Validators.required, Validators.pattern(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,6})+$/)] ),
      'role': new FormControl( '', Validators.required ),
      'password': new FormControl( '', Validators.required )
    });
  }

  onCreateUser() {
    
    let newUser = new NewUserDto(
      this.userForm.value['firstname'],
      this.userForm.value['name'],
      this.userForm.value['email'],
      this.userForm.value['password'],
      this.userForm.value['role']
    );
    this.store.dispatch(new UserAdminActions.CreateUser(newUser));
    
  }

}
