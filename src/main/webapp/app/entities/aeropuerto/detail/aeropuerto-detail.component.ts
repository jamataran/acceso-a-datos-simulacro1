import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAeropuerto } from '../aeropuerto.model';

@Component({
  selector: 'jhi-aeropuerto-detail',
  templateUrl: './aeropuerto-detail.component.html',
})
export class AeropuertoDetailComponent implements OnInit {
  aeropuerto: IAeropuerto | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ aeropuerto }) => {
      this.aeropuerto = aeropuerto;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
