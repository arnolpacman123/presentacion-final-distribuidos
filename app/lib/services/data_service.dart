import 'package:app/utils/urls.dart';
import 'package:http/http.dart' as http;
import 'package:app/models/data.dart';

class DataService {
  static const String url = Urls.urlBaseApi;

  static Future<List<Data>> getDataToday(String device) async {
    List<Data> data = [];
    final uri = Uri.parse("$url/getHistorialPorDia/$device");
    final response = await http.get(uri);
    if (response.statusCode == 200 || response.statusCode == 201) {
      final String json = response.body;
      data = dataFromJson(json);
    }
    return data;
  }

  static Future<List<Data>> getDataLastWeek(String device) async {
    List<Data> data = [];
    final uri = Uri.parse("$url/getHistorialPorSemana/$device");
    final response = await http.get(uri);
    if (response.statusCode == 200 || response.statusCode == 201) {
      final String json = response.body;
      data = dataFromJson(json);
    }
    return data;
  }

  static Future<List<Data>> getDataThisMonth(String device) async {
    List<Data> data = [];
    final uri = Uri.parse("$url/getHistorialPorMes/$device");
    final response = await http.get(uri);
    if (response.statusCode == 200 || response.statusCode == 201) {
      final String json = response.body;
      data = dataFromJson(json);
    }
    return data;
  }
}
