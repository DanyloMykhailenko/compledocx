package com.dariomorgrane.compledocx.model.document;

import java.time.Instant;

public interface SupplementedDocument {

    Long getId();

    String getName();

    Instant getCreateTimestamp();

    Byte[] getContent();

}
