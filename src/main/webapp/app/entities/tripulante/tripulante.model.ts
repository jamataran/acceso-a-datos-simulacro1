import { IVuelo } from 'app/entities/vuelo/vuelo.model';

export interface ITripulante {
  id?: number;
  nombre?: string;
  apellidos?: string;
  dni?: string | null;
  direccion?: string;
  email?: string;
  vuelos?: IVuelo[] | null;
}

export class Tripulante implements ITripulante {
  constructor(
    public id?: number,
    public nombre?: string,
    public apellidos?: string,
    public dni?: string | null,
    public direccion?: string,
    public email?: string,
    public vuelos?: IVuelo[] | null
  ) {}
}

export function getTripulanteIdentifier(tripulante: ITripulante): number | undefined {
  return tripulante.id;
}
