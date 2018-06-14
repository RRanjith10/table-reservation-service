package com.mindtree.table.reservation.repository;

import org.springframework.data.repository.CrudRepository;

import com.mindtree.table.reservation.entity.Booking;

public interface BookingRepository extends CrudRepository<Booking, Integer> {
    public Booking save(Booking book);
}
