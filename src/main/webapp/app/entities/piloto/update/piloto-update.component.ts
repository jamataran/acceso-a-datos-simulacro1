import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IPiloto, Piloto } from '../piloto.model';
import { PilotoService } from '../service/piloto.service';

@Component({
  selector: 'jhi-piloto-update',
  templateUrl: './piloto-update.component.html',
})
export class PilotoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required, Validators.minLength(10), Validators.maxLength(255)]],
    apellidos: [null, [Validators.required, Validators.minLength(10), Validators.maxLength(255)]],
    dni: [null, [Validators.required, Validators.pattern('^[0-9]{8}[TRWAGMYFPDXBNJZSQVHLCKE]')]],
    direccion: [null, [Validators.required]],
    email: [null, [Validators.pattern('^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$')]],
    esPiloto: [],
    horasDeVuelo: [],
  });

  constructor(protected pilotoService: PilotoService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ piloto }) => {
      this.updateForm(piloto);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const piloto = this.createFromForm();
    if (piloto.id !== undefined) {
      this.subscribeToSaveResponse(this.pilotoService.update(piloto));
    } else {
      this.subscribeToSaveResponse(this.pilotoService.create(piloto));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPiloto>>): void {
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

  protected updateForm(piloto: IPiloto): void {
    this.editForm.patchValue({
      id: piloto.id,
      nombre: piloto.nombre,
      apellidos: piloto.apellidos,
      dni: piloto.dni,
      direccion: piloto.direccion,
      email: piloto.email,
      esPiloto: piloto.esPiloto,
      horasDeVuelo: piloto.horasDeVuelo,
    });
  }

  protected createFromForm(): IPiloto {
    return {
      ...new Piloto(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      apellidos: this.editForm.get(['apellidos'])!.value,
      dni: this.editForm.get(['dni'])!.value,
      direccion: this.editForm.get(['direccion'])!.value,
      email: this.editForm.get(['email'])!.value,
      esPiloto: this.editForm.get(['esPiloto'])!.value,
      horasDeVuelo: this.editForm.get(['horasDeVuelo'])!.value,
    };
  }
}
