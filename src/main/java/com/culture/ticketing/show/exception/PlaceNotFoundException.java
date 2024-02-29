package com.culture.ticketing.show.exception;

public class PlaceNotFoundException extends RuntimeException {

    public PlaceNotFoundException(Long placeId) {
        super(String.format("존재하지 않는 장소입니다. (placeId = %d)", placeId));
    }
}
