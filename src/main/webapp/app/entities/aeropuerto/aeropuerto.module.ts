import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AeropuertoComponent } from './list/aeropuerto.component';
import { AeropuertoDetailComponent } from './detail/aeropuerto-detail.component';
import { AeropuertoUpdateComponent } from './update/aeropuerto-update.component';
import { AeropuertoDeleteDialogComponent } from './delete/aeropuerto-delete-dialog.component';
import { AeropuertoRoutingModule } from './route/aeropuerto-routing.module';

@NgModule({
  imports: [SharedModule, AeropuertoRoutingModule],
  declarations: [AeropuertoComponent, AeropuertoDetailComponent, AeropuertoUpdateComponent, AeropuertoDeleteDialogComponent],
  entryComponents: [AeropuertoDeleteDialogComponent],
})
export class AeropuertoModule {}
