package com.dariomorgrane.compledocx.model;

import java.time.Instant;

public interface SupplementedDocument {

    Long getId();

    String getName();

    Instant getCreateTimestamp();

    Byte[] getContent();

}
