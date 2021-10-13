import { IVuelo } from 'app/entities/vuelo/vuelo.model';

export interface IAvion {
  id?: number;
  matricula?: string | null;
  tipo?: string | null;
  edad?: number | null;
  numeroSerie?: string | null;
  vuelos?: IVuelo[] | null;
}

export class Avion implements IAvion {
  constructor(
    public id?: number,
    public matricula?: string | null,
    public tipo?: string | null,
    public edad?: number | null,
    public numeroSerie?: string | null,
    public vuelos?: IVuelo[] | null
  ) {}
}

export function getAvionIdentifier(avion: IAvion): number | undefined {
  return avion.id;
}
