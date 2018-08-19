import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UserAdminComponent } from './user-admin/user-admin.component';
import { SchoolAdminComponent } from './school-admin/school-admin.component';
import { HomeworkAdminComponent } from './homework-admin/homework-admin.component';
import { AdminRoutingModule } from './admin-routing.module';

@NgModule({
  imports: [
    CommonModule,
    AdminRoutingModule
  ],
  declarations: [
    UserAdminComponent, 
    SchoolAdminComponent, 
    HomeworkAdminComponent]
})
export class AdminModule { }
