import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAeropuerto } from '../aeropuerto.model';
import { AeropuertoService } from '../service/aeropuerto.service';

@Component({
  templateUrl: './aeropuerto-delete-dialog.component.html',
})
export class AeropuertoDeleteDialogComponent {
  aeropuerto?: IAeropuerto;

  constructor(protected aeropuertoService: AeropuertoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.aeropuertoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
