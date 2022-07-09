package com.example.demo.parkinglot.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ParkingLot {
    String parkingLotId;
    List<ParkingFloor> parkingFloors;
    int numberOfFloor;
    int numberOfSlotPerFloor;
}
