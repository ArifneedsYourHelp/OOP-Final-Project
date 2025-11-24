package model;

import java.time.LocalDate;

public class Showtime {
    private Movie movie;
    private LocalDate date;
    private String time;
    private String room;

    public Showtime(Movie movie, LocalDate date, String time, String room) {
        this.movie = movie;
        this.date = date;
        this.time = time;
        this.room = room;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}

