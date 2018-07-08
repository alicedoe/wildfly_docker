export class SchoolDto {
  public id: number;
  public name: string;
  public townName: string;

  constructor(id: number, name: string, townName:string ) {
    this.id = id;
    this.name = name;
    this.townName = townName;
  }
}