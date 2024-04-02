package be.tftic.java.models;

public record Section(
        long id,
        String name,
        long delegateId
) {}
