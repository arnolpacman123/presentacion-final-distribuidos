import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { DeviceEntity } from './model/device.entity';
import { DeviceController } from './controller/device.controller';
import { DeviceService } from './service/device.service';

@Module({
  imports: [
    TypeOrmModule.forFeature([DeviceEntity]),
  ],
  controllers: [DeviceController],
  providers: [DeviceService],
})
export class DeviceModule {}
