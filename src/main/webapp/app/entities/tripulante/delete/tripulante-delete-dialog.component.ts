import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITripulante } from '../tripulante.model';
import { TripulanteService } from '../service/tripulante.service';

@Component({
  templateUrl: './tripulante-delete-dialog.component.html',
})
export class TripulanteDeleteDialogComponent {
  tripulante?: ITripulante;

  constructor(protected tripulanteService: TripulanteService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tripulanteService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
