package com.daeliin.components.webservices.fixtures;

import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;

import static com.ninja_squad.dbsetup.operation.CompositeOperation.sequenceOf;

public final class JavaFixtures {

    public static Operation uuidPersistentResources() {
        return sequenceOf(
            Operations.deleteAllFrom("uuid_persistent_resource"),
            Operations.insertInto("uuid_persistent_resource")
                .columns("uuid", "creation_date", "label")
                .values("d5666c5a-df28-49be-b511-cfd58d0867cf", "2017-01-01 12:00:00", "label1")
                .values("d5666c5a-df28-49be-b512-cfd58d0867cf", "2017-01-01 12:00:00", "label2")
                .values("d5666c5a-df28-49be-b513-cfd58d0867cf", "2017-01-01 12:00:00", "label3")
                .values("d5666c5a-df28-49be-b514-cfd58d0867cf", "2017-01-02 12:00:00", "label4")
                .build()
        );
    }
}
