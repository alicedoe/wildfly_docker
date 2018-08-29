import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { UserAdminComponent } from './user-admin/user-admin.component';
import { SchoolAdminComponent } from './school-admin/school-admin.component';
import { HomeworkAdminComponent } from './homework-admin/homework-admin.component';
import { AdminRoutingModule } from './admin-routing.module';
import { CreateUserComponent } from './user-admin/create-user/create-user.component';
import { ListUserComponent } from './user-admin/list-user/list-user.component';

@NgModule({
  imports: [
    CommonModule,
    AdminRoutingModule,
    FormsModule
  ],
  declarations: [
    UserAdminComponent, 
    SchoolAdminComponent, 
    HomeworkAdminComponent, CreateUserComponent, ListUserComponent]
})
export class AdminModule { }
