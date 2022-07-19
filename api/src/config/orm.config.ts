import { TypeOrmModuleOptions } from '@nestjs/typeorm';

export const config: TypeOrmModuleOptions = {
  type: 'postgres',
  host: 'postgresql-arnolpacman123.alwaysdata.net',
  port: 5432,
  username: 'arnolpacman123',
  password: 'Aspirine217021220',
  database: 'arnolpacman123_distribuidos',
  synchronize: true,
  entities: ['dist/**/*.entity.{ts,js}'],
  autoLoadEntities: true,
  ssl: {
    rejectUnauthorized: false,
  },
};
