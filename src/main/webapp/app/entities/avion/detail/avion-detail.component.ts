import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAvion } from '../avion.model';

@Component({
  selector: 'jhi-avion-detail',
  templateUrl: './avion-detail.component.html',
})
export class AvionDetailComponent implements OnInit {
  avion: IAvion | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ avion }) => {
      this.avion = avion;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
