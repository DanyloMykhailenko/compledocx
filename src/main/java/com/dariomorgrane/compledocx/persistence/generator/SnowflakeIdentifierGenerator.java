package com.dariomorgrane.compledocx.persistence.generator;

import org.apache.marmotta.kiwi.generator.SnowflakeIDGenerator;
import org.hibernate.HibernateException;
import org.hibernate.boot.model.relational.SqlStringGenerationContext;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Optional;
import java.util.Random;

public class SnowflakeIdentifierGenerator implements IdentifierGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(SnowflakeIdentifierGenerator.class);

    private static final String MACHINE_ID_ENVIRONMENT_VARIABLE_NAME = "MACHINE_ID";

    private static final Long MAX_MACHINE_ID = 1023L;

    private SnowflakeIDGenerator generator;

    @Override
    public void initialize(SqlStringGenerationContext context) {
        Long machineId = Optional.ofNullable(System.getenv(MACHINE_ID_ENVIRONMENT_VARIABLE_NAME))
                .map(Long::valueOf)
                .orElseGet(this::getRandomMachineId);
        LOGGER.info("Machine id {} is used for identifiers generation.", machineId);
        generator = new SnowflakeIDGenerator(machineId);
    }

    @Override
    public Serializable generate(
            SharedSessionContractImplementor session,
            Object object
    ) throws HibernateException {
        return generator.getId();
    }

    private Long getRandomMachineId() {
        LOGGER.warn("Environment variable {} not found. Random value is used.", MACHINE_ID_ENVIRONMENT_VARIABLE_NAME);
        return new Random().nextLong(1, MAX_MACHINE_ID);
    }

}
