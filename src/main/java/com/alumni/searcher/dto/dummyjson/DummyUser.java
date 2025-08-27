package com.alumni.searcher.dto.dummyjson;

public record DummyUser(
        int id,
        String firstName,
        String lastName,
        DummyAddress address,
        DummyCompany company
) {}
