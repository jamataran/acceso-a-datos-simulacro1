import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAvion } from '../avion.model';
import { AvionService } from '../service/avion.service';

@Component({
  templateUrl: './avion-delete-dialog.component.html',
})
export class AvionDeleteDialogComponent {
  avion?: IAvion;

  constructor(protected avionService: AvionService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.avionService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
