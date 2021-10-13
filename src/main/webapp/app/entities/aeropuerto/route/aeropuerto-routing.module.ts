import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AeropuertoComponent } from '../list/aeropuerto.component';
import { AeropuertoDetailComponent } from '../detail/aeropuerto-detail.component';
import { AeropuertoUpdateComponent } from '../update/aeropuerto-update.component';
import { AeropuertoRoutingResolveService } from './aeropuerto-routing-resolve.service';

const aeropuertoRoute: Routes = [
  {
    path: '',
    component: AeropuertoComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AeropuertoDetailComponent,
    resolve: {
      aeropuerto: AeropuertoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AeropuertoUpdateComponent,
    resolve: {
      aeropuerto: AeropuertoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AeropuertoUpdateComponent,
    resolve: {
      aeropuerto: AeropuertoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(aeropuertoRoute)],
  exports: [RouterModule],
})
export class AeropuertoRoutingModule {}
