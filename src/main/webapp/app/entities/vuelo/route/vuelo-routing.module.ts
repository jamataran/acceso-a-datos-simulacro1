import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { VueloComponent } from '../list/vuelo.component';
import { VueloDetailComponent } from '../detail/vuelo-detail.component';
import { VueloUpdateComponent } from '../update/vuelo-update.component';
import { VueloRoutingResolveService } from './vuelo-routing-resolve.service';

const vueloRoute: Routes = [
  {
    path: '',
    component: VueloComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VueloDetailComponent,
    resolve: {
      vuelo: VueloRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VueloUpdateComponent,
    resolve: {
      vuelo: VueloRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VueloUpdateComponent,
    resolve: {
      vuelo: VueloRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(vueloRoute)],
  exports: [RouterModule],
})
export class VueloRoutingModule {}
