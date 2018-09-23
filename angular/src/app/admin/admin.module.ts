import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { StoreModule } from '@ngrx/store';

import { UserAdminComponent } from './user-admin/user-admin.component';
import { SchoolAdminComponent } from './school-admin/school-admin.component';
import { HomeworkAdminComponent } from './homework-admin/homework-admin.component';
import { AdminRoutingModule } from './admin-routing.module';
import { CreateUserComponent } from './user-admin/create-user/create-user.component';
import { ListUserComponent } from './user-admin/list-user/list-user.component';
import { reducers } from './store';

@NgModule({
  imports: [
    CommonModule,
    AdminRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    StoreModule.forFeature('admin', reducers)
  ],
  declarations: [
    UserAdminComponent, 
    SchoolAdminComponent, 
    HomeworkAdminComponent, CreateUserComponent, ListUserComponent]
})
export class AdminModule { }