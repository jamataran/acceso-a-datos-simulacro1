import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TripulanteComponent } from '../list/tripulante.component';
import { TripulanteDetailComponent } from '../detail/tripulante-detail.component';
import { TripulanteUpdateComponent } from '../update/tripulante-update.component';
import { TripulanteRoutingResolveService } from './tripulante-routing-resolve.service';

const tripulanteRoute: Routes = [
  {
    path: '',
    component: TripulanteComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TripulanteDetailComponent,
    resolve: {
      tripulante: TripulanteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TripulanteUpdateComponent,
    resolve: {
      tripulante: TripulanteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TripulanteUpdateComponent,
    resolve: {
      tripulante: TripulanteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tripulanteRoute)],
  exports: [RouterModule],
})
export class TripulanteRoutingModule {}
