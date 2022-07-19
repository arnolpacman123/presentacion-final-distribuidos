import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { DataEntity } from '../model/data.entity';
import { DataI } from '../model/data.interface';

@Injectable()
export class DataService {
  constructor(
    @InjectRepository(DataEntity)
    private readonly dataRepository: Repository<DataEntity>,
  ) {}

  public async getHistorialPorDia(idDispositivo: string): Promise<DataI[]> {
    const today = new Date();
    // get today's date at 00:00
    today.setHours(today.getHours() - 4);
    today.setHours(0, 0, 0, 0);
    const datas = await this.dataRepository
      .createQueryBuilder()
      .select('CAST(temperature AS VARCHAR)', 'temp')
      .addSelect('CAST(humidity AS VARCHAR)', 'hum')
      .addSelect('date', 'time')
      .addSelect('identifier', 'idDispositivo')
      .where('identifier = :identifier', { identifier: idDispositivo })
      .andWhere("date_part('day', date) = :day", { day: today.getDate() })
      .andWhere("date_part('month', date) = :month", {
        month: today.getMonth() + 1,
      })
      .andWhere("date_part('year', date) = :year", {
        year: today.getFullYear(),
      })
      .orderBy('date', 'DESC')
      .getRawMany();
    return datas;
  }

  public async getHistorialPorSemana(idDispositivo: string): Promise<DataI[]> {
    // get today's date at 00:00
    const today = new Date();
    today.setHours(today.getHours() - 4);
    today.setHours(0, 0, 0, 0);
    // get the date of a week ago
    const weekAgo = today;
    weekAgo.setDate(weekAgo.getDate() - 7);
    // get the history of the last week
    const datas = await this.dataRepository
      .createQueryBuilder()
      .select('CAST(temperature AS VARCHAR)', 'temp')
      .addSelect('CAST(humidity AS VARCHAR)', 'hum')
      .addSelect('date', 'time')
      .addSelect('identifier', 'idDispositivo')
      .where('identifier = :identifier', { identifier: idDispositivo })
      .andWhere("date_part('day', date) >= :day", { day: weekAgo.getDate() })
      .andWhere("date_part('month', date) = :month", {
        month: weekAgo.getMonth() + 1,
      })
      .andWhere("date_part('year', date) = :year", {
        year: weekAgo.getFullYear(),
      })
      .orderBy('date', 'DESC')
      .getRawMany();
    return datas;
  }

  public async getHistorialPorMes(idDispositivo: string): Promise<DataI[]> {
    // get today's date at 00:00
    const today = new Date();
    today.setHours(today.getHours() - 4);
    const datas = await this.dataRepository
      .createQueryBuilder()
      .select('CAST(temperature AS VARCHAR)', 'temp')
      .addSelect('CAST(humidity AS VARCHAR)', 'hum')
      .addSelect('date', 'time')
      .addSelect('identifier', 'idDispositivo')
      .where('identifier = :identifier', { identifier: idDispositivo })
      .andWhere("date_part('month', date) = :month", {
        month: today.getMonth() + 1,
      })
      .andWhere("date_part('year', date) = :year", {
        year: today.getFullYear(),
      })
      .orderBy('date', 'DESC')
      .getRawMany();
    return datas;
  }
}
