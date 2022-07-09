package com.example.demo.parkinglot.models;

import com.example.demo.parkinglot.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ParkingSlot {
    int parkingSlotNumber;
    int floorNumber;
    VehicleType type;
    boolean isFree;

    public ParkingSlot(int parkingSlotNumber, VehicleType type, boolean isFree) {
        this.parkingSlotNumber = parkingSlotNumber;
        this.type = type;
        this.isFree = isFree;
    }
}
