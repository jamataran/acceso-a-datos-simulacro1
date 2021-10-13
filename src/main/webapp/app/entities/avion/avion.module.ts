import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AvionComponent } from './list/avion.component';
import { AvionDetailComponent } from './detail/avion-detail.component';
import { AvionUpdateComponent } from './update/avion-update.component';
import { AvionDeleteDialogComponent } from './delete/avion-delete-dialog.component';
import { AvionRoutingModule } from './route/avion-routing.module';

@NgModule({
  imports: [SharedModule, AvionRoutingModule],
  declarations: [AvionComponent, AvionDetailComponent, AvionUpdateComponent, AvionDeleteDialogComponent],
  entryComponents: [AvionDeleteDialogComponent],
})
export class AvionModule {}
