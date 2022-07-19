import { Controller, Get, Param, Query } from '@nestjs/common';
import { DataService } from '../service/data.service';

@Controller('')
export class DataController {
  constructor(private dataService: DataService) {}

  @Get('getHistorialPorDia/:idDispositivo')
  async getHistorialPorDia(@Param('idDispositivo') idDispositivo: string) {
    return await this.dataService.getHistorialPorDia(idDispositivo);
  }

  @Get('getHistorialPorSemana/:idDispositivo')
  async getHistorialPorSemana(@Param('idDispositivo') idDispositivo: string) {
    return await this.dataService.getHistorialPorSemana(idDispositivo);
  }

  @Get('getHistorialPorMes/:idDispositivo')
  async getHistorialPorMes(@Param('idDispositivo') idDispositivo: string) {
    return await this.dataService.getHistorialPorMes(idDispositivo);
  }
}
