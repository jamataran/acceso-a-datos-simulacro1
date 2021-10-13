import { IVuelo } from 'app/entities/vuelo/vuelo.model';

export interface IAeropuerto {
  id?: number;
  nombre?: string | null;
  ciudad?: string;
  salidas?: IVuelo[] | null;
  destinos?: IVuelo[] | null;
}

export class Aeropuerto implements IAeropuerto {
  constructor(
    public id?: number,
    public nombre?: string | null,
    public ciudad?: string,
    public salidas?: IVuelo[] | null,
    public destinos?: IVuelo[] | null
  ) {}
}

export function getAeropuertoIdentifier(aeropuerto: IAeropuerto): number | undefined {
  return aeropuerto.id;
}
