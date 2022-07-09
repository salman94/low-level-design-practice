package com.example.demo.parkinglot.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ParkingFloor {
    int floorNumber;
    List<ParkingSlot> parkingSlots;
}
