package com.dariomorgrane.compledocx.model;

import java.time.Instant;

public class SupplementedDocumentImpl implements SupplementedDocument {

    private static final Long ignorableId = -1L;

    private final Long id;

    private final String name;

    private final Instant createTimestamp;

    private final Byte[] content;

    public SupplementedDocumentImpl(
            Long id,
            String name,
            Byte[] content
    ) {
        this.id = id;
        this.name = name;
        this.createTimestamp = Instant.now();
        this.content = content;
    }

    public SupplementedDocumentImpl(
            String name,
            Byte[] content
    ) {
        this(ignorableId, name, content);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Instant getCreateTimestamp() {
        return createTimestamp;
    }

    @Override
    public Byte[] getContent() {
        return content;
    }

}
