import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { DataEntity } from './model/data.entity';
import { DataService } from './service/data.service';
import { DataController } from './controller/data.controller';

@Module({
  imports: [
    TypeOrmModule.forFeature([DataEntity]),
  ],
  providers: [DataService],
  controllers: [DataController],
})
export class DataModule {}
