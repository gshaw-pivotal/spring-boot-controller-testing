package gs.springbootcontrollertesting.model;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Builder(toBuilder = true)
@Data
@Jacksonized
public class ThingResponse {

    int id;

    String name;

    String description;

    String notes;
}
