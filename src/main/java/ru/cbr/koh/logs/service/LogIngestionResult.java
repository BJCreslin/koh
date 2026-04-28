package ru.cbr.koh.logs.service;

public record LogIngestionResult(int insertedEvents, int skippedDuplicates, long newOffset) {
}
