import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HomeworkAdminComponent } from './homework-admin/homework-admin.component';
import { SchoolAdminComponent } from './school-admin/school-admin.component';
import { UserAdminComponent } from './user-admin/user-admin.component';
import { AuthGuardService } from './auth-guard.service';

const adminRoutes: Routes = [
  { path: 'homework-admin', component: HomeworkAdminComponent, canActivate: [AuthGuardService] },
  { path: 'school-admin', component: SchoolAdminComponent, canActivate: [AuthGuardService] },
  { path: 'user-admin', component: UserAdminComponent, canActivate: [AuthGuardService] },
];

@NgModule({
  imports: [
    RouterModule.forChild(adminRoutes)
  ],
  exports: [RouterModule],
  providers: [
    AuthGuardService
  ]
})
export class AdminRoutingModule {}