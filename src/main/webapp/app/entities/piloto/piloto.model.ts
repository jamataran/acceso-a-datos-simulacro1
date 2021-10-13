export interface IPiloto {
  id?: number;
  nombre?: string;
  apellidos?: string;
  dni?: string;
  direccion?: string;
  email?: string | null;
  esPiloto?: boolean | null;
  horasDeVuelo?: number | null;
}

export class Piloto implements IPiloto {
  constructor(
    public id?: number,
    public nombre?: string,
    public apellidos?: string,
    public dni?: string,
    public direccion?: string,
    public email?: string | null,
    public esPiloto?: boolean | null,
    public horasDeVuelo?: number | null
  ) {
    this.esPiloto = this.esPiloto ?? false;
  }
}

export function getPilotoIdentifier(piloto: IPiloto): number | undefined {
  return piloto.id;
}
