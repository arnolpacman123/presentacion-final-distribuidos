// To parse this JSON data, do
//
//     final data = dataFromJson(jsonString);

import 'dart:convert';

List<Data> dataFromJson(String str) =>
    List<Data>.from(json.decode(str).map((x) => Data.fromJson(x)));

String dataToJson(List<Data> data) =>
    json.encode(List<dynamic>.from(data.map((x) => x.toJson())));

class Data {
  Data({
    this.temperature,
    this.humidity,
    this.date,
    this.identifier,
  });

  final double? temperature;
  final double? humidity;
  final DateTime? date;
  final String? identifier;

  factory Data.fromJson(Map<String, dynamic> json) {
    return Data(
      temperature: double.parse(json["temp"]),
      humidity: json["hum"] != null ? double.parse(json["hum"]) : null,
      date: DateTime.parse(json["time"]).toUtc(),
      identifier: json["idDispositivo"],
    );
  }

  Map<String, dynamic> toJson() => {
        "temp": temperature,
        "hum": humidity,
        "time": date!.toIso8601String(),
        "idDispositivo": identifier,
      };
}
