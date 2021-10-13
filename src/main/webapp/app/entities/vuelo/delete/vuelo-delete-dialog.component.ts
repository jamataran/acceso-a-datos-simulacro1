import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IVuelo } from '../vuelo.model';
import { VueloService } from '../service/vuelo.service';

@Component({
  templateUrl: './vuelo-delete-dialog.component.html',
})
export class VueloDeleteDialogComponent {
  vuelo?: IVuelo;

  constructor(protected vueloService: VueloService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.vueloService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
