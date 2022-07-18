package com.dariomorgrane.compledocx.model.document;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "documents")
public class SupplementedDocumentImpl implements SupplementedDocument {

    private static final String SNOWFLAKE_ID_GENERATOR_NAME = "SnowflakeIdentifierGenerator";
    private static final String IGNORABLE_NAME = "";
    private static final byte[] IGNORABLE_CONTENT = {};

    @GenericGenerator(
            name = SNOWFLAKE_ID_GENERATOR_NAME,
            strategy = "com.dariomorgrane.compledocx.persistence.generator.SnowflakeIdentifierGenerator"
    )
    @GeneratedValue(generator = SNOWFLAKE_ID_GENERATOR_NAME)
    @Id
    private final Long id;

    private final String name;

    private final Instant createTimestamp;

    @Lob
    private final byte[] content;

    public SupplementedDocumentImpl(
            Long id,
            String name,
            Instant createTimestamp,
            byte[] content
    ) {
        this.id = id;
        this.name = name;
        this.createTimestamp = createTimestamp;
        this.content = content;
    }

    public SupplementedDocumentImpl(
            String name,
            byte[] content
    ) {
        this(null, name, Instant.now(), content);
    }

    protected SupplementedDocumentImpl() {
        this(IGNORABLE_NAME, IGNORABLE_CONTENT);
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
    public byte[] getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SupplementedDocumentImpl that = (SupplementedDocumentImpl) o;
        return Objects.equals(id, that.id)
                && Objects.equals(name, that.name)
                && Objects.equals(createTimestamp, that.createTimestamp)
                && Arrays.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, createTimestamp);
        result = 31 * result + Arrays.hashCode(content);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(",\n", SupplementedDocumentImpl.class.getSimpleName() + "[\n", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("createTimestamp=" + createTimestamp)
                .add("content=" + Arrays.toString(content))
                .toString();
    }

}
