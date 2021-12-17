package gs.springbootcontrollertesting.model;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Builder
@Value
public class Thing {

    int id;

    String name;

    String description;

    String notes;

    UUID internalId;
}
