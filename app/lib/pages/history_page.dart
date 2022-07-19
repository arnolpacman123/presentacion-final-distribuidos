import 'package:flutter/material.dart';

import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:get/get.dart';
import 'package:syncfusion_flutter_charts/charts.dart';

import 'package:app/models/device.dart';
import 'package:app/models/data.dart';
import 'package:app/services/data_service.dart';

class HistoryPage extends StatefulWidget {
  const HistoryPage({Key? key}) : super(key: key);

  @override
  State<HistoryPage> createState() => _HistoryPageState();
}

class _HistoryPageState extends State<HistoryPage> {
  bool isLoading = false;
  Device device = Get.arguments["device"];
  List<String> listOptions = [
    "Hoy",
    "Última semana",
    "Este mes",
  ];
  Map<int, String> mapMonths = {
    1: "Enero",
    2: "Febrero",
    3: "Marzo",
    4: "Abril",
    5: "Mayo",
    6: "Junio",
    7: "Julio",
    8: "Agosto",
    9: "Septiembre",
    10: "Octubre",
    11: "Noviembre",
    12: "Diciembre",
  };
  late String currentMonth;
  int optionSelected = 0;
  Map<int, String> mapOptions = {
    0: "Registros de hoy",
    1: "Registros de la última semana",
    2: "Registros de este mes",
  };
  List<Data> datas = [];

  @override
  void initState() {
    initializeApp();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        centerTitle: true,
        title: Text('Dispositivo: ${ device.identifier }'),
      ),
      body: isLoading
          ? const Center(child: CircularProgressIndicator())
          : Padding(
              padding: const EdgeInsets.symmetric(
                horizontal: 15.0,
                vertical: 20.0,
              ),
              child: Column(
                children: [
                  DropdownButtonHideUnderline(
                    child: DropdownButton<int>(
                      hint: const Text(
                        "Selecciona la opción",
                        style: TextStyle(
                          color: Colors.grey,
                          fontSize: 16,
                          fontFamily: "verdana_regular",
                        ),
                      ),
                      items: List.generate(
                        listOptions.length,
                        (index) => DropdownMenuItem(
                          value: index,
                          child: Text(
                            listOptions[index],
                            style: const TextStyle(
                              fontSize: 16,
                              fontFamily: "verdana_regular",
                            ),
                          ),
                        ),
                      ),
                      onChanged: (value) async {
                        optionSelected = value!;
                        await requestOption(optionSelected);
                      },
                    ),
                  ),
                  Text(
                    "${mapOptions[optionSelected]!}${optionSelected == 2 ? ': $currentMonth' : ""}",
                    style: const TextStyle(
                      fontSize: 20.0,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  Expanded(
                    child: SfCartesianChart(
                      primaryXAxis: CategoryAxis(
                        visibleMinimum: 0,
                        visibleMaximum: 1,
                        interval: 1,
                        labelIntersectAction:
                            AxisLabelIntersectAction.multipleRows,
                      ),
                      zoomPanBehavior: ZoomPanBehavior(enablePanning: true),
                      tooltipBehavior: TooltipBehavior(enable: true),
                      legend: Legend(isVisible: true),
                      series: [
                        LineSeries<Data, String>(
                          dataSource: datas,
                          xValueMapper: (Data data, _) {
                            switch (optionSelected) {
                              case 0:
                                return getHourFormat(data.date!);
                              case 1:
                                return getDateFormat(data.date!);
                              case 2:
                                return getMonthFormat(data.date!);
                            }
                            return null;
                          },
                          yValueMapper: (Data data, _) => data.temperature,
                          name: "Temperatura",
                          color: Colors.blue,
                          markerSettings: const MarkerSettings(
                            isVisible: true,
                            shape: DataMarkerType.circle,
                          ),
                          dataLabelSettings: const DataLabelSettings(
                            isVisible: true,
                          ),
                        ),
                      ],
                    ),
                  ),
                ],
              ),
            ),
    );
  }

  Future<void> getDataToday(String? identifier) async {
    datas = await DataService.getDataToday(identifier!);
    setState(() {
    });
  }

  Future<void> getDataLastWeek(String? identifier) async {
    datas = await DataService.getDataLastWeek(identifier!);
    setState(() {
    });
  }

  Future<void> getDataThisMonth(String? identifier) async {
    datas = await DataService.getDataThisMonth(identifier!);
    setState(() {
    });
  }

  String getHourFormat(DateTime dateTime) {
    return "${dateTime.hour < 10 ? '0${dateTime.hour}' : dateTime.hour}:${dateTime.minute < 10 ? '0${dateTime.minute}' : dateTime.minute}:${dateTime.second < 10 ? '0${dateTime.second}' : dateTime.second}";
  }

  String getDateFormat(DateTime dateTime) {
    return "${dateTime.day < 10 ? '0${dateTime.day}' : dateTime.day}/${dateTime.month < 10 ? '0${dateTime.month}' : dateTime.month}/${dateTime.year} - ${dateTime.hour < 10 ? '0${dateTime.hour}' : dateTime.hour}:${dateTime.minute < 10 ? '0${dateTime.minute}' : dateTime.minute}";
  }

  String getMonthFormat(DateTime dateTime) {
    return "Mes ${dateTime.day < 10 ? '0${dateTime.day}' : dateTime.day}/${dateTime.month < 10 ? '0${dateTime.month}' : dateTime.month}/${dateTime.year} - ${dateTime.hour < 10 ? '0${dateTime.hour}' : dateTime.hour}:00 horas";
  }

  void initializeApp() async {
    currentMonth = mapMonths[DateTime.now().month]!;
    setState(() {
      isLoading = true;
    });
    await requestOption(optionSelected);
    setState(() {
      isLoading = false;
    });

    final FirebaseMessaging firebaseMessaging = FirebaseMessaging.instance;
    String? token = await firebaseMessaging.getToken();
    print("FCM token: $token");

    FirebaseMessaging.onMessage.listen(_onMessageHandler);
    FirebaseMessaging.onMessageOpenedApp.listen(_onMessageOpenedAppHandler);
  }

  void _onMessageHandler(RemoteMessage message) async {
    await receiveNotification(message);
  }

  Future<void> receiveNotification(RemoteMessage message) async {
    String identifier = message.data["id"]!;
    bool updateDatas = identifier == device.identifier!;
    if (updateDatas) {
      await requestOption(optionSelected);
    }
  }

  Future<void> requestOption(int optionSelected) async {
    switch (optionSelected) {
      case 0:
        await getDataToday(device.identifier);
        break;
      case 1:
        await getDataLastWeek(device.identifier);
        break;
      case 2:
        await getDataThisMonth(device.identifier);
        break;
    }
  }

  void _onMessageOpenedAppHandler(RemoteMessage message) async {
    await receiveNotification(message);
  }
}