import { Component, OnInit, Input, TemplateRef, ViewChild } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { BsModalService } from 'ngx-bootstrap/modal';
import { BsModalRef } from 'ngx-bootstrap/modal/bs-modal-ref.service';
import { ToastrService } from 'ngx-toastr';

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
  error$: Observable<string>;
  status$: Observable<string>;
  editMode$: Observable<boolean>;
  userToEdit$: Observable<UserDto>;
  userForm: FormGroup;  
  userIdToEdit: number;
  modalRef: BsModalRef;
  @ViewChild('modal') modal; 

  constructor(private store: Store<fromStore.UserAdminState>, private modalService: BsModalService, private toastr: ToastrService) {
  }

  ngOnInit() {
    this.roles$ = this.store.select(fromStore.getRoles);    
    this.error$ = this.store.select(fromStore.getError);  
    this.status$ = this.store.select(fromStore.getStatus); 
    this.editMode$ = this.store.select(fromStore.getEditMode);
    this.userToEdit$ = this.store.select(fromStore.getUser);
    this.initForm();
    
    this.editMode$.subscribe( res => {
      if (res) {
        console.log("this.editMode$ : "+res) 
        this.fillForm();
        this.openModal(this.modal);
      }
    })

    this.error$.subscribe( res => {
      if ( res !== null ) {            
        console.log("this.error$ : "+res)        
        this.toastr.error(res);
      }
    })

    this.status$.subscribe( res => {
      if ( res !== null ) {    
        console.log("this.status$ : "+res)    
        this.toastr.success(res);
      }
    })
  }

  openModal(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(template);
  }

  initForm() {
      this.userForm = new FormGroup({
        'name': new FormControl( '', Validators.required ),
        'firstname': new FormControl( '', Validators.required ),
        'email': new FormControl( '', [Validators.required, Validators.pattern(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,6})+$/)] ),
        'role': new FormControl( '', Validators.required ),
        'password': new FormControl( '', Validators.required )
      }); 
  }

  fillForm() {
      
    this.userToEdit$.subscribe(res=> {
        if (res!== null) {
          this.userForm.get('firstname').setValue(res.firstname);
          this.userForm.get('name').setValue(res.name);
          this.userForm.get('email').setValue(res.email);
          this.userForm.get('role').setValue(res.roleName);
          this.userIdToEdit = res.id;
          this.userForm.controls['password'].clearValidators();
          this.userForm.get('password').updateValueAndValidity();  
        }
      });    
  }

  onSaveUser() {
    
    let user = new UserDto(
      null,
      this.userForm.value['firstname'],
      this.userForm.value['name'],
      this.userForm.value['email'],
      this.userForm.value['password'],
      this.userForm.value['role']
    );
    if ( this.isEditModeTrue() ) {
      user.id = this.userIdToEdit;
      this.store.dispatch(new UserAdminActions.SaveUser(user));
    } else this.store.dispatch(new UserAdminActions.CreateUser(user));

    this.userForm.reset();
    this.modalRef.hide();
    
  }

  isEditModeTrue() {
    let edit;
    this.editMode$.subscribe( res => {
      if (res) edit=true; 
      else edit=false;
    })
    return edit;
  }

  onCancel() {
    this.modalRef.hide();
    this.store.dispatch(new UserAdminActions.Clear);
    this.userForm.reset();   
  }

}
