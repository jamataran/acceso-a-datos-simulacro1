import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IAeropuerto, Aeropuerto } from '../aeropuerto.model';
import { AeropuertoService } from '../service/aeropuerto.service';

@Component({
  selector: 'jhi-aeropuerto-update',
  templateUrl: './aeropuerto-update.component.html',
})
export class AeropuertoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.minLength(10), Validators.maxLength(255)]],
    ciudad: [null, [Validators.required, Validators.minLength(10), Validators.maxLength(255)]],
  });

  constructor(protected aeropuertoService: AeropuertoService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ aeropuerto }) => {
      this.updateForm(aeropuerto);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const aeropuerto = this.createFromForm();
    if (aeropuerto.id !== undefined) {
      this.subscribeToSaveResponse(this.aeropuertoService.update(aeropuerto));
    } else {
      this.subscribeToSaveResponse(this.aeropuertoService.create(aeropuerto));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAeropuerto>>): void {
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

  protected updateForm(aeropuerto: IAeropuerto): void {
    this.editForm.patchValue({
      id: aeropuerto.id,
      nombre: aeropuerto.nombre,
      ciudad: aeropuerto.ciudad,
    });
  }

  protected createFromForm(): IAeropuerto {
    return {
      ...new Aeropuerto(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      ciudad: this.editForm.get(['ciudad'])!.value,
    };
  }
}
