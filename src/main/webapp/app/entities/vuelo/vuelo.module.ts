import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { VueloComponent } from './list/vuelo.component';
import { VueloDetailComponent } from './detail/vuelo-detail.component';
import { VueloUpdateComponent } from './update/vuelo-update.component';
import { VueloDeleteDialogComponent } from './delete/vuelo-delete-dialog.component';
import { VueloRoutingModule } from './route/vuelo-routing.module';

@NgModule({
  imports: [SharedModule, VueloRoutingModule],
  declarations: [VueloComponent, VueloDetailComponent, VueloUpdateComponent, VueloDeleteDialogComponent],
  entryComponents: [VueloDeleteDialogComponent],
})
export class VueloModule {}
