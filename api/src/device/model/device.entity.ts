import { Column, Entity, PrimaryGeneratedColumn } from 'typeorm';

@Entity({ name: 'devices' })
export class DeviceEntity {
  @PrimaryGeneratedColumn()
  id: number;

  @Column({ unique: true })
  identifier: string;

  @Column({ type: 'timestamp without time zone' })
  date: Date; 

  @Column({ type: 'real' })
  temperature: number;

  @Column({ type: 'real' })
  humidity: number;

  @Column({ type: 'boolean' })
  connected: boolean;
}


