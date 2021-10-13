import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVuelo } from '../vuelo.model';

@Component({
  selector: 'jhi-vuelo-detail',
  templateUrl: './vuelo-detail.component.html',
})
export class VueloDetailComponent implements OnInit {
  vuelo: IVuelo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vuelo }) => {
      this.vuelo = vuelo;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
