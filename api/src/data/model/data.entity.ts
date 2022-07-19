import { Column, Entity, PrimaryGeneratedColumn } from "typeorm";

@Entity({ name: 'datas' })
export class DataEntity {
  @PrimaryGeneratedColumn()
  id: number;

  @Column()
  identifier: string;

  @Column({ type: 'real' })
  temperature: number;

  @Column({ type: 'real' })
  humidity: number;

  @Column({ type: 'timestamp without time zone' })
  date: Date;
}
