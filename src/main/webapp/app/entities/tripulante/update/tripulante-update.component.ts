import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ITripulante, Tripulante } from '../tripulante.model';
import { TripulanteService } from '../service/tripulante.service';

@Component({
  selector: 'jhi-tripulante-update',
  templateUrl: './tripulante-update.component.html',
})
export class TripulanteUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required, Validators.minLength(10), Validators.maxLength(255)]],
    apellidos: [null, [Validators.required, Validators.minLength(10), Validators.maxLength(255)]],
    dni: [null, [Validators.pattern('[0-9]{8}[TRWAGMYFPDXBNJZSQVHLCKE]')]],
    direccion: [null, [Validators.required, Validators.minLength(10), Validators.maxLength(255)]],
    email: [null, [Validators.required, Validators.pattern('^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$')]],
  });

  constructor(protected tripulanteService: TripulanteService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tripulante }) => {
      this.updateForm(tripulante);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tripulante = this.createFromForm();
    if (tripulante.id !== undefined) {
      this.subscribeToSaveResponse(this.tripulanteService.update(tripulante));
    } else {
      this.subscribeToSaveResponse(this.tripulanteService.create(tripulante));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITripulante>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(tripulante: ITripulante): void {
    this.editForm.patchValue({
      id: tripulante.id,
      nombre: tripulante.nombre,
      apellidos: tripulante.apellidos,
      dni: tripulante.dni,
      direccion: tripulante.direccion,
      email: tripulante.email,
    });
  }

  protected createFromForm(): ITripulante {
    return {
      ...new Tripulante(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      apellidos: this.editForm.get(['apellidos'])!.value,
      dni: this.editForm.get(['dni'])!.value,
      direccion: this.editForm.get(['direccion'])!.value,
      email: this.editForm.get(['email'])!.value,
    };
  }
}
