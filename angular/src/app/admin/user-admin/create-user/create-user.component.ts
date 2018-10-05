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
  error$: Observable<String>;
  editMode: boolean;
  userForm: FormGroup;  
  userId: number;

  constructor(private store: Store<fromStore.UserAdminState>) { 
    
  }

  ngOnInit() {
    this.roles$ = this.store.select(fromStore.getRoles);    
    this.error$ = this.store.select(fromStore.getError);
    this.userForm = new FormGroup({
      'name': new FormControl( '', Validators.required ),
      'firstname': new FormControl( '', Validators.required ),
      'email': new FormControl( '', [Validators.required, Validators.pattern(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,6})+$/)] ),
      'role': new FormControl( '', Validators.required ),
      'password': new FormControl( '', Validators.required )
    });
    
    this.store.select(fromStore.getEditMode).subscribe(res=>{      
      this.editMode=res;
      if (this.editMode) this.fillForm();
    })   
  }

  fillForm() {
      let user: Observable<UserDto> = this.store.select(fromStore.getUser);
      user.subscribe(res=> {console.log(res) 
        if (res!== null) {
        this.userForm.get('firstname').setValue(res.firstname);
        this.userForm.get('name').setValue(res.name);
        this.userForm.get('email').setValue(res.email);
        this.userForm.get('role').setValue(res.roleName);
        this.userId = res.id;
        this.userForm.controls['password'].clearValidators();
        this.userForm.get('password').updateValueAndValidity();  
        } else {
          this.userForm.reset();
        }
      });    
  }

  onSaveUser() {

    let id;   
    
    let newUser = new UserDto(
      id,
      this.userForm.value['firstname'],
      this.userForm.value['name'],
      this.userForm.value['email'],
      this.userForm.value['password'],
      this.userForm.value['role']
    );

    if ( this.editMode ) {
      newUser.id = this.userId;
      this.store.dispatch(new UserAdminActions.SaveUser(newUser));
    } else this.store.dispatch(new UserAdminActions.CreateUser(newUser));
    
  }

  onCancel() {
    
    
    
  }

}
