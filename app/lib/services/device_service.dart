import 'package:app/utils/urls.dart';
import 'package:http/http.dart' as http;

import 'package:app/models/device.dart';

class DeviceService {
  static const String url = Urls.urlBaseApi;

  static Future<List<Device>> getAllDevices() async {
    List<Device> devices = [];
    final response = await http.get(Uri.parse("$url/getDispositivos"));
    if (response.statusCode == 200 || response.statusCode == 201) {
      final String json = response.body;
      devices = devicesFromJson(json);
    }
    return devices;
  }
}
