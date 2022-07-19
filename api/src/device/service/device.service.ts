import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { DeviceEntity } from '../model/device.entity';
import { DeviceI } from '../model/device.interface';

@Injectable()
export class DeviceService {
  constructor(
    @InjectRepository(DeviceEntity)
    private readonly deviceRepository: Repository<DeviceEntity>,
  ) {}

  public async findAll(): Promise<DeviceI[]> {
    const devices = await this.deviceRepository
      .createQueryBuilder()
      .select('identifier', 'id')
      .addSelect('CAST(temperature AS VARCHAR)', 'temp')
      .addSelect('CAST(humidity AS VARCHAR)', 'hum')
      .addSelect('connected', 'estado')
      .addSelect("date", 'ultimoRegistro')
      .getRawMany();
    return devices;
  }
}
