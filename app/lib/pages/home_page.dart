import 'package:app/models/device.dart';
import 'package:app/pages/history_page.dart';
import 'package:app/services/device_service.dart';
import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

class HomePage extends StatefulWidget {
  const HomePage({Key? key}) : super(key: key);

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  List<Device> devices = [];

  @override
  void initState() {
    getAllDevices();
    initializeApp();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        centerTitle: true,
        title: const Text('Lista de dispositivos'),
      ),
      body: Padding(
        padding: const EdgeInsets.symmetric(horizontal: 8.0),
        child: Column(
          children: [
            Expanded(
              child: ListView.builder(
                itemCount: devices.length,
                itemBuilder: (context, index) {
                  final identifier = devices[index].identifier;
                  final temperature = devices[index].temperature;
                  final humidity = devices[index].humidity;
                  final date = devices[index].date!.toUtc();
                  final dateFormat =
                      "${getDateFormat(date)} - ${getTimeFormat(date)}";
                  final connected = devices[index].connected!;
                  return SizedBox(
                    height: 150,
                    child: Padding(
                      padding: const EdgeInsets.symmetric(
                        vertical: 10.0,
                        horizontal: 10.0,
                      ),
                      child: InkWell(
                        borderRadius: const BorderRadius.all(
                          Radius.circular(25.0),
                        ),
                        onTap: () {
                          Get.to(
                            () => const HistoryPage(),
                            arguments: {
                              "device": devices[index],
                            },
                          );
                        },
                        child: Card(
                          elevation: 5.0,
                          shape: const RoundedRectangleBorder(
                            borderRadius: BorderRadius.all(
                              Radius.circular(25.0),
                            ),
                          ),
                          child: Row(
                            mainAxisAlignment: MainAxisAlignment.spaceAround,
                            children: [
                              Column(
                                mainAxisAlignment: MainAxisAlignment.center,
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                  Text(
                                    "Dispositivo: $identifier",
                                    style: const TextStyle(fontSize: 16.0),
                                  ),
                                  const SizedBox(height: 5.0),
                                  Text(
                                    "Temperatura: $temperature °C",
                                    style: const TextStyle(fontSize: 16.0),
                                  ),
                                  const SizedBox(height: 5.0),
                                  Text(
                                    "Humedad: $humidity %",
                                    style: const TextStyle(fontSize: 16.0),
                                  ),
                                  const SizedBox(height: 5.0),
                                  Text(
                                    "Últ. registro: $dateFormat",
                                    style: const TextStyle(fontSize: 16.0),
                                    overflow: TextOverflow.clip,
                                  ),
                                ],
                              ),
                              Icon(
                                Icons.wifi_outlined,
                                color: connected ? Colors.green : Colors.grey,
                                size: 40.0,
                              ),
                            ],
                          ),
                        ),
                      ),
                    ),
                  );
                },
              ),
            ),
          ],
        ),
      ),
    );
  }

  void initializeApp() async {
    final FirebaseMessaging firebaseMessaging = FirebaseMessaging.instance;
    String? token = await firebaseMessaging.getToken();
    print("FCM token: $token");

    FirebaseMessaging.onMessage.listen(_onMessageHandler);
    FirebaseMessaging.onMessageOpenedApp.listen(_onMessageOpenedAppHandler);
  }

  void _onMessageHandler(RemoteMessage message) async {
    getAllDevices();
  }

  void _onMessageOpenedAppHandler(RemoteMessage message) async {
    getAllDevices();
  }

  void getAllDevices() async {
    devices = await DeviceService.getAllDevices();
    setState(() {});
  }

  String getDateFormat(DateTime date) {
    return "${date.day < 10 ? '0${date.day}' : date.day}/${date.month < 10 ? '0${date.month}' : date.month}/${date.year}";
  }

  String getTimeFormat(DateTime date) {
    return "${date.hour < 10 ? '0${date.hour}' : date.hour}:${date.minute < 10 ? '0${date.minute}' : date.minute}:${date.second < 10 ? '0${date.second}' : date.second}";
  }
}
