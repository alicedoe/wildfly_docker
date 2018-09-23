import { Component, OnInit, Input } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { NgForm, FormGroup, FormControl, Validators } from '@angular/forms';

import * as fromStore from '../../store';
import * as UserAdminActions from '../../store/actions/userAdmin.actions';
import { RoleDto } from '../../models/roleDto.model';
import { UserDto } from '../../../shared/models/userDto.model';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.css']
})
export class CreateUserComponent implements OnInit {

  roles$: Observable<Array<RoleDto>>;
  editMode$: Observable<boolean>;
  userToEdit$: Observable<UserDto>;
  userForm: FormGroup;
  

  constructor(private store: Store<fromStore.UserAdminState>) { }

  ngOnInit() {
    this.store.dispatch(new UserAdminActions.GetRoles());
    this.userForm = new FormGroup({
      'name': new FormControl( '', Validators.required ),
      'firstname': new FormControl( '', Validators.required ),
      'email': new FormControl( '', [Validators.required, Validators.pattern(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,6})+$/)] ),
      'role': new FormControl( '', Validators.required ),
      'password': new FormControl( '', Validators.required )
    });
    this.roles$ = this.store.select(fromStore.getRoles);
    this.editMode$ = this.store.select(fromStore.getEditMode);
    this.userToEdit$ = this.store.select(fromStore.getUser);

    this.store.select(fromStore.getEditMode).subscribe(res=>{
      if (res) {
        this.userForm.controls['password'].clearValidators();
      } else {
        this.userForm.controls['password'].setValidators( Validators.required );
      }
      this.userForm.get('password').updateValueAndValidity();
    })

    this.store.select(fromStore.getUser).subscribe(res=>{
      this.userForm.get('firstname').setValue(res.firstname);
      this.userForm.get('name').setValue(res.name);
      this.userForm.get('email').setValue(res.email);
      this.userForm.get('role').setValue(res.roleName);
    })
  }

  onSaveUser() {

    let id;

    this.store.select(fromStore.getEditMode).subscribe(res=>{
      if (res) {
        id = this.userToEdit$.id;
      } else {
        id = null;
      }
    })
    
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
