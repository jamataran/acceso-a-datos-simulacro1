import { IAvion } from 'app/entities/avion/avion.model';
import { IAeropuerto } from 'app/entities/aeropuerto/aeropuerto.model';
import { IPiloto } from 'app/entities/piloto/piloto.model';
import { ITripulante } from 'app/entities/tripulante/tripulante.model';

export interface IVuelo {
  id?: number;
  avion?: IAvion | null;
  origen?: IAeropuerto | null;
  destino?: IAeropuerto | null;
  piloto?: IPiloto | null;
  tripulacions?: ITripulante[] | null;
}

export class Vuelo implements IVuelo {
  constructor(
    public id?: number,
    public avion?: IAvion | null,
    public origen?: IAeropuerto | null,
    public destino?: IAeropuerto | null,
    public piloto?: IPiloto | null,
    public tripulacions?: ITripulante[] | null
  ) {}
}

export function getVueloIdentifier(vuelo: IVuelo): number | undefined {
  return vuelo.id;
}
