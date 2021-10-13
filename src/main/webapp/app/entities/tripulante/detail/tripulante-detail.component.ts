import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITripulante } from '../tripulante.model';

@Component({
  selector: 'jhi-tripulante-detail',
  templateUrl: './tripulante-detail.component.html',
})
export class TripulanteDetailComponent implements OnInit {
  tripulante: ITripulante | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tripulante }) => {
      this.tripulante = tripulante;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
