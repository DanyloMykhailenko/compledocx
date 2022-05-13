package com.dariomorgrane.compledocx.dto;

import java.time.Instant;

public interface SupplementedDocumentDto {

    Long id();

    String name();

    Instant createTimestamp();

}
