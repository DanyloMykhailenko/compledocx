package com.dariomorgrane.compledocx.dto;

import java.time.Instant;

public record SupplementedDocumentDtoImpl(
        Long id,
        String name,
        Instant createTimestamp
) implements SupplementedDocumentDto {
}
