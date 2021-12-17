package gs.springbootcontrollertesting.api;

import gs.springbootcontrollertesting.model.ThingRequest;
import gs.springbootcontrollertesting.service.ThingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/thing")
public class ApiEndpoints {

    private final ThingService thingService;

    @Autowired
    public ApiEndpoints(ThingService thingService) { this.thingService = thingService; }

    @PostMapping(
            value = "/create",
            consumes = APPLICATION_JSON_VALUE
    )
    public ResponseEntity create(@RequestBody ThingRequest thing) {
        int thingId = thingService.create(thing);

        return ResponseEntity.status(HttpStatus.CREATED).body(String.format("{ \"id\": %d}", thingId));
    }

    @GetMapping(
            value = "/{thingId}",
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity get(@PathVariable int thingId) {
        return ResponseEntity.status(HttpStatus.OK).body(thingService.get(thingId));
    }

    @DeleteMapping(
            value = "/{thingId}"
    )
    public ResponseEntity remove(@PathVariable int thingId) {
        thingService.remove(thingId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
