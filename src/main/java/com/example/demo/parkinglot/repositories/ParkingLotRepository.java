package com.example.demo.parkinglot.repositories;

import com.example.demo.parkinglot.enums.VehicleType;
import com.example.demo.parkinglot.models.ParkingFloor;
import com.example.demo.parkinglot.models.ParkingSlot;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotRepository {
    List<ParkingFloor> parkingFloors;

    public List<ParkingFloor> initilizeData(int numberOfFloor, int numberOfSlotsPerFloor) {
        parkingFloors = new ArrayList<>(numberOfFloor);
        for (int i = 0; i < numberOfFloor; i++) {
            ParkingFloor parkingFloor = new ParkingFloor();
            parkingFloor.setFloorNumber(i);
            List<ParkingSlot> parkingSlots = getParkingSots(numberOfSlotsPerFloor, i);
            parkingFloor.setParkingSlots(parkingSlots);
            parkingFloors.add(parkingFloor);
        }
        return parkingFloors;
    }

    private List<ParkingSlot> getParkingSots(int numberOfSlotsPerFloor, int floorNumber) {
        List<ParkingSlot> parkingSlots = new ArrayList<>(numberOfSlotsPerFloor);
        if (numberOfSlotsPerFloor > 1) {
            parkingSlots.add(getTruckSlot(floorNumber));
        }
        if (numberOfSlotsPerFloor > 3) {
            for (int i = 1; i < 3; i++) {
                parkingSlots.add(getBikeSlot(i, floorNumber));
            }
        }
        if (numberOfSlotsPerFloor > 3) {
            for (int i = 3; i < numberOfSlotsPerFloor; i++) {
                parkingSlots.add(getCarSlot(i, floorNumber));
            }
        }
        return parkingSlots;
    }

    private ParkingSlot getTruckSlot(int floorNumber) {
        return new ParkingSlot(0, floorNumber, VehicleType.TRUCK, true);
    }

    private ParkingSlot getCarSlot(int slotId, int floorNumber) {
        return new ParkingSlot(slotId, floorNumber, VehicleType.CAR, true);
    }

    private ParkingSlot getBikeSlot(int slotId, int floorNumber) {
        return new ParkingSlot(slotId, floorNumber, VehicleType.BIKE, true);
    }
}
