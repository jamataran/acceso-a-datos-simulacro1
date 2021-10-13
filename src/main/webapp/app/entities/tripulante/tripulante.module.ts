import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TripulanteComponent } from './list/tripulante.component';
import { TripulanteDetailComponent } from './detail/tripulante-detail.component';
import { TripulanteUpdateComponent } from './update/tripulante-update.component';
import { TripulanteDeleteDialogComponent } from './delete/tripulante-delete-dialog.component';
import { TripulanteRoutingModule } from './route/tripulante-routing.module';

@NgModule({
  imports: [SharedModule, TripulanteRoutingModule],
  declarations: [TripulanteComponent, TripulanteDetailComponent, TripulanteUpdateComponent, TripulanteDeleteDialogComponent],
  entryComponents: [TripulanteDeleteDialogComponent],
})
export class TripulanteModule {}
