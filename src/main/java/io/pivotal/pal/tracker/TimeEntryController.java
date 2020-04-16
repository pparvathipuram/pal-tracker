package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.micrometer.core.instrument.MeterRegistry;

import java.util.Collection;
import java.util.List;

@RestController
public class TimeEntryController {

    private final TimeEntryRepository timeEntryRepository;
    private final DistributionSummary timeEntrySummary;
    private final Counter actionCounter;
    long id=1;

    @Autowired
    public TimeEntryController(TimeEntryRepository timeEntryRepository, MeterRegistry meterRegistry) {
        this.timeEntryRepository = timeEntryRepository;
        timeEntrySummary = meterRegistry.summary("timeEntry.summary");
        actionCounter = meterRegistry.counter("timeEntry.actionCounter");
    }

    @PostMapping(path = "/time-entries", consumes = "application/json", produces = "application/json")
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry createdTimeEntry= this.timeEntryRepository.create(timeEntryToCreate);
        ResponseEntity res = new ResponseEntity(createdTimeEntry, HttpStatus.CREATED);
        return res;
    }

    @GetMapping(path = "/time-entries/{timeEntryId}", produces = "application/json")
    public ResponseEntity<TimeEntry> read(@PathVariable long timeEntryId) {
        TimeEntry readTimeEntry= this.timeEntryRepository.find(timeEntryId);
        ResponseEntity res;
        if (readTimeEntry != null)
            res = new ResponseEntity(readTimeEntry, HttpStatus.OK);
        else
            res = new ResponseEntity(readTimeEntry, HttpStatus.NOT_FOUND);
        return res;
    }

    @GetMapping(path = "/time-entries", produces = "application/json")
    public ResponseEntity<List<TimeEntry>> list() {
        Collection<TimeEntry> list = this.timeEntryRepository.list();
        ResponseEntity res = new ResponseEntity(list, HttpStatus.OK);
        return res;

    }

    @PutMapping(path = "/time-entries/{timeEntryId}", produces = "application/json")
    public ResponseEntity update(@PathVariable long timeEntryId, @RequestBody TimeEntry expected) {
        TimeEntry updatedEntry= this.timeEntryRepository.update(timeEntryId, expected);
        ResponseEntity res;
        if (updatedEntry != null)
            res = new ResponseEntity(updatedEntry, HttpStatus.OK);
        else
            res = new ResponseEntity(updatedEntry, HttpStatus.NOT_FOUND);
        return res;

    }

    @DeleteMapping(path = "/time-entries/{timeEntryId}", produces = "application/json")
    public ResponseEntity delete(@PathVariable long timeEntryId) {
        this.timeEntryRepository.delete(timeEntryId);
        ResponseEntity res = new ResponseEntity(HttpStatus.NO_CONTENT);
        return res;
    }
}
