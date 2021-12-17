package gs.springbootcontrollertesting.service;

import gs.springbootcontrollertesting.model.Thing;
import gs.springbootcontrollertesting.model.ThingRequest;
import gs.springbootcontrollertesting.model.ThingResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ThingService {

    private Integer nextId = 1;

    private Map<Integer, Thing> store = new HashMap();

    public int create(ThingRequest thingRequest) {
        Thing thing = Thing.builder()
                .id(nextId)
                .name(thingRequest.getName())
                .description(thingRequest.getDescription())
                .notes(thingRequest.getNotes())
                .internalId(UUID.randomUUID())
                .build();

        store.put(nextId, thing);

        nextId++;

        return thing.getId();
    }

    public ThingResponse get(int thingId) {
        Thing thing = store.get(thingId);

        if (thing!= null) {
            return ThingResponse.builder()
                    .id(thing.getId())
                    .name(thing.getName())
                    .description(thing.getDescription())
                    .notes(thing.getNotes())
                    .build();
        }

        return null;
    }

    public void remove(int thingId) {
        store.remove(thingId);
    }
}
