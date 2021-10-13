import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AvionComponent } from '../list/avion.component';
import { AvionDetailComponent } from '../detail/avion-detail.component';
import { AvionUpdateComponent } from '../update/avion-update.component';
import { AvionRoutingResolveService } from './avion-routing-resolve.service';

const avionRoute: Routes = [
  {
    path: '',
    component: AvionComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AvionDetailComponent,
    resolve: {
      avion: AvionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AvionUpdateComponent,
    resolve: {
      avion: AvionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AvionUpdateComponent,
    resolve: {
      avion: AvionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(avionRoute)],
  exports: [RouterModule],
})
export class AvionRoutingModule {}
