export interface Trophy {
  id: number;
  name: string;
  description: string;
  points_trophy: number;
  cod_trophy: string;
  unlocked: boolean;
  path?: string;
}
