import { Component, OnInit, Input } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { NgForm, FormGroup, FormControl, Validators } from '@angular/forms';

import * as fromApp from '../../../shared/app.reducers';
import * as fromUserAdmin from '../../store/reducers/userAdmin.reducers';
import * as UserAdminActions from '../../store/actions/userAdmin.actions';
import { RoleDto } from '../../models/roleDto.model';
import { UserDto } from '../../../shared/models/userDto.model';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.css']
})
export class CreateUserComponent implements OnInit {

  roles: Array<RoleDto>;
  userAdminState: Observable<fromUserAdmin.UserAdminState>;
  userForm: FormGroup;
  editMode: boolean;
  userToEdit: UserDto;

  constructor(private store: Store<fromApp.AppState>) { }

  ngOnInit() {
    this.editMode = false;
    this.userAdminState = this.store.select('userAdmin');
    this.store.dispatch(new UserAdminActions.GetRoles());
    this.userForm = new FormGroup({
      'name': new FormControl( '', Validators.required ),
      'firstname': new FormControl( '', Validators.required ),
      'email': new FormControl( '', [Validators.required, Validators.pattern(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,6})+$/)] ),
      'role': new FormControl( '', Validators.required ),
      'password': new FormControl( '', Validators.required )
    });
    this.userAdminState.subscribe(res => {
      this.roles = res.roles;
      this.editMode = res.edit;
      this.userToEdit = res.user;
      if ( this.editMode ) {
        this.userForm.controls['password'].clearValidators();
      } else {
        this.userForm.controls['password'].setValidators( Validators.required );
      }
      this.userForm.get('password').updateValueAndValidity();
      if (res.user) {
        this.userForm.get('firstname').setValue(res.user.firstname);
        this.userForm.get('name').setValue(res.user.name);
        this.userForm.get('email').setValue(res.user.email);
        this.userForm.get('role').setValue(res.user.roleName);
      }
    })
  }

  onSaveUser() {

    let id;

    if ( this.editMode ) {
      id = this.userToEdit.id;
    } else {
      id = null;
    }
    
    let newUser = new UserDto(
      id,
      this.userForm.value['firstname'],
      this.userForm.value['name'],
      this.userForm.value['email'],
      this.userForm.value['password'],
      this.userForm.value['role']
    );

    if ( this.editMode ) {
      newUser.id = this.userToEdit.id;
      this.store.dispatch(new UserAdminActions.SaveUser(newUser));
    } else this.store.dispatch(new UserAdminActions.CreateUser(newUser));
    
  }

  onCancel() {
    
    
    
  }

}
