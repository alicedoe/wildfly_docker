import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HomeworkAdminComponent } from './homework-admin/homework-admin.component';
import { SchoolAdminComponent } from './school-admin/school-admin.component';
import { UserAdminComponent } from './user-admin/user-admin.component';

const adminRoutes: Routes = [
  { path: 'homework-admin', component: HomeworkAdminComponent },
  { path: 'school-admin', component: SchoolAdminComponent },
  { path: 'user-admin', component: UserAdminComponent },
];

@NgModule({
  imports: [
    RouterModule.forChild(adminRoutes)
  ],
  exports: [RouterModule]
})
export class AdminRoutingModule {}