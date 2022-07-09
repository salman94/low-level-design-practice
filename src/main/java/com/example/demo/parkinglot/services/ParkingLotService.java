package com.example.demo.parkinglot.services;

import com.example.demo.parkinglot.enums.Display;
import com.example.demo.parkinglot.enums.VehicleType;
import com.example.demo.parkinglot.models.ParkingFloor;
import com.example.demo.parkinglot.models.ParkingLot;
import com.example.demo.parkinglot.models.ParkingSlot;
import com.example.demo.parkinglot.models.Vehicle;
import com.example.demo.parkinglot.repositories.ParkingLotRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ParkingLotService {

    private ParkingLot parkingLot;
    private Map<String, Vehicle> vehicleMap;
    private ParkingLotRepository parkingLotRepository;

    public void createParkingLot(String parkingLotId, int numberOfFloor, int parkingSlotEachFloor) {
        parkingLotRepository = new ParkingLotRepository();
        List<ParkingFloor> parkingFloors = parkingLotRepository.initilizeData(numberOfFloor, parkingSlotEachFloor);
        parkingLot = new ParkingLot(parkingLotId, parkingFloors, numberOfFloor, parkingSlotEachFloor);
        vehicleMap = new HashMap<>();

        System.out.println("Created parking lot with " + parkingLot.getNumberOfFloor() +
                " floors and " + parkingLot.getNumberOfSlotPerFloor() + " slots per floor");
    }

    public void parkVehicle(VehicleType type, String registrationNumber, String color) {
        ParkingSlot parkingSlot = getParkingSlot(type);
        if (parkingSlot == null) {
            System.out.println("Parking Lot Full");
        } else {
            String ticketNumber = String.format("%s_%s_%s", parkingLot.getParkingLotId(), parkingSlot.getFloorNumber() + 1, parkingSlot.getParkingSlotNumber() + 1);
            parkingSlot.setFree(false);
            vehicleMap.put(ticketNumber, new Vehicle(registrationNumber, color, type));

            System.out.println("Parked vehicle. Ticket ID: " + ticketNumber);
        }
    }

    private ParkingSlot getParkingSlot(VehicleType type) {
        List<ParkingFloor> parkingFloors = parkingLot.getParkingFloors();
        for (ParkingFloor parkingFloor : parkingFloors) {
            for (ParkingSlot parkingSlot : parkingFloor.getParkingSlots()) {
                if (parkingSlot.isFree() && parkingSlot.getType().equals(type)) {
                    return parkingSlot;
                }
            }
        }
        return null;
    }

    public void unparkCar(String ticketId) {
        Vehicle vehicle = vehicleMap.get(ticketId);
        if (vehicle == null) {
            System.out.println("Invalid Ticket");
        } else {
            String[] parkingSlotInfo = ticketId.split("_");
            if (parkingSlotInfo.length < 2) {
                System.out.println("Invalid Ticket");
            } else {
                int floorNumber = Integer.parseInt(parkingSlotInfo[1]);
                int slotNumber = Integer.parseInt(parkingSlotInfo[2]);
                ParkingSlot parkingSlot = getParkingSlot(floorNumber - 1, slotNumber - 1);
                parkingSlot.setFree((true));
                System.out.println(String.format("Unparked vehicle with Registration Number: %s and Color: %s", vehicle.getRegistrationNumber(), vehicle.getColor()));
                vehicleMap.remove(ticketId);
            }
        }
    }

    private ParkingSlot getParkingSlot(int floorNumber, int slotNumber) {
        return parkingLot.getParkingFloors().get(floorNumber).getParkingSlots().get(slotNumber);
    }

    public void displayFreeSlots(Display displayType, VehicleType vehicleType) {
        switch (displayType) {
            case FREE_SLOTS:
                parkingLot.getParkingFloors().forEach(parkingFloor -> {
                    List<Integer> freeSlots = getFreeSlots(vehicleType, parkingFloor);
                    StringBuilder displayFreeSlots = new StringBuilder(String.format("Free slots for %s on Floor %s: ", vehicleType, parkingFloor.getFloorNumber() + 1));
                    freeSlots.forEach(slotNumber -> displayFreeSlots.append(slotNumber + 1).append(","));
                    System.out.println(displayFreeSlots.deleteCharAt(displayFreeSlots.length()-1));
                });
                break;
            case OCCUPIED_SLOTS:
                parkingLot.getParkingFloors().forEach(parkingFloor -> {
                    List<Integer> occupiedSlot = getOccupiedSlot(vehicleType, parkingFloor);
                    StringBuilder displayOccupiedSlots = new StringBuilder(String.format("Occupied slots for %s on Floor %s: ", vehicleType, parkingFloor.getFloorNumber() + 1));
                    occupiedSlot.forEach(slotNumber -> displayOccupiedSlots.append(slotNumber + 1).append(","));
                    System.out.println(displayOccupiedSlots.deleteCharAt(displayOccupiedSlots.length()-1));
                });
                break;
            case FREE_COUNT:
                parkingLot.getParkingFloors().forEach(parkingFloor -> {
                    int numberOfFreeSlots = getCountOfFreeSlotsForAFloor(vehicleType, parkingFloor);
                    System.out.println(String.format("No. of free slots for %s on Floor %s: %s", vehicleType, parkingFloor.getFloorNumber() + 1, numberOfFreeSlots));
                });
            default:

        }
    }

    private List<Integer> getFreeSlots(VehicleType vehicleType, ParkingFloor parkingFloor) {

        return parkingFloor.getParkingSlots().stream()
                .filter(parkingSlot -> parkingSlot.getType().equals(vehicleType))
                .filter(ParkingSlot::isFree)
                .map(ParkingSlot::getParkingSlotNumber)
                .collect(Collectors.toList());
    }

    private int getCountOfFreeSlotsForAFloor(VehicleType vehicleType, ParkingFloor parkingFloor) {

        return (int) parkingFloor.getParkingSlots().stream()
                .filter(parkingSlot -> parkingSlot.getType().equals(vehicleType))
                .filter(ParkingSlot::isFree)
                .count();
    }

    private List<Integer> getOccupiedSlot(VehicleType vehicleType, ParkingFloor parkingFloor) {

        return parkingFloor.getParkingSlots().stream()
                .filter(parkingSlot -> parkingSlot.getType().equals(vehicleType))
                .filter(parkingSlot -> !parkingSlot.isFree())
                .map(ParkingSlot::getParkingSlotNumber)
                .collect(Collectors.toList());
    }
}
