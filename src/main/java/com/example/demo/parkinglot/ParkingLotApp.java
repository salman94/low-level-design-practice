package com.example.demo.parkinglot;

import com.example.demo.parkinglot.enums.Command;
import com.example.demo.parkinglot.enums.Display;
import com.example.demo.parkinglot.enums.VehicleType;
import com.example.demo.parkinglot.services.ParkingLotService;

import java.util.Scanner;

public class ParkingLotApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ParkingLotService parkingLotService = new ParkingLotService();
        while (true) {
            String[] commands = scanner.nextLine().split(" ");
            if (commands.length == 0) continue;
            Command command = Command.of(commands[0]);
            switch (command) {
                case CREATE_PARKING_LOT:
                    parkingLotService.createParkingLot(commands[1], Integer.parseInt(commands[2]), Integer.parseInt(commands[3]));
                    break;
                case DISPLAY:
                    parkingLotService.displayFreeSlots(Display.of(commands[1]), VehicleType.valueOf(commands[2]));
                    break;
                case PARK_VEHICLE:
                    parkingLotService.parkVehicle(VehicleType.valueOf(commands[1]), commands[2], commands[3]);
                    break;
                case UNPARK_VEHICLE:
                    parkingLotService.unparkCar(commands[1]);
                    break;
                case EXIT:
                    System.exit(0);
                    break;
                default:
                    System.out.println("No command found");
            }
        }
    }
}
