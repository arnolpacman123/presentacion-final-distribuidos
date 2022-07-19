import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { config } from './config/orm.config';
import { DeviceModule } from './device/device.module';
import { DataModule } from './data/data.module';

@Module({
  imports: [
    TypeOrmModule.forRoot(config),
    DeviceModule,
    DataModule,
  ],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}
