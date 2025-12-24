package org.example;

import com.github.javafaker.Faker;
import org.example.model.Reservation;
import org.example.model.Seat;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class AirlineSeatReservationSystem {

    static Map<Integer, Seat> seats = new ConcurrentHashMap<>(100);
    static List<Reservation> reservations = new CopyOnWriteArrayList<>();
    static ExecutorService executorService = Executors.newFixedThreadPool(5);
    static Faker faker = new Faker();

    public static void main(String[] args) throws InterruptedException {
        flightSeats();
        for (int i=1; i<=100; i++) {
            submitBookingTask();
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        reservations.forEach(System.out::println);
        reservations.stream().map(Reservation::getAssignedSeat).max(Comparator.comparingInt(seat -> seat.getAttempts().get())).ifPresent(seat -> System.out.println("Seat with most attempts " +seat.getSeatNumber()));
    }

    public static void flightSeats() {
        String[] alphabet = {"A", "B", "C", "D", "E"};
        for(int i=0; i<20; i++) {
            for(int j=1; j<=5; j++) {
                seats.put((i*5)+j, new Seat(i+1 + alphabet[j-1]));
            }
        }
    }

    public static void submitBookingTask() {
            executorService.submit(() -> {
                for(Seat seat: seats.values()) {
                    seat.lock.lock();
                    try {
                        seat.getAttempts().getAndIncrement();
                        if (!seat.getAssigned()) {
                            seat.setAssigned(true);
                            reservations.add(new Reservation(faker.name().fullName(), seat));
                            break;
                        }
                    } finally {
                        seat.lock.unlock();
                    }
                }
            });
        }
}
