package gs.springbootcontrollertesting.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

@Builder(toBuilder = true)
@Data
@Jacksonized
public class ThingRequest {

    @NonNull
    String name;

    String description;

    String notes;
}
