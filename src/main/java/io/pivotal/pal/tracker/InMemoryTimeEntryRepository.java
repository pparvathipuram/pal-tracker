package io.pivotal.pal.tracker;

import java.util.*;


public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    Map<Long, TimeEntry> inMemoryEntries = new HashMap<>();
    //Map<Long, TimeEntry> inMemoryEntries = new List<>();

    long id=1;

    public TimeEntry create(TimeEntry timeEntry) {

        timeEntry.setId(id++);
        inMemoryEntries.put(timeEntry.getId(), timeEntry);
        return inMemoryEntries.get(timeEntry.getId());
    }

    @Override
    public TimeEntry find(long timeEntryId) {
        return inMemoryEntries.get(timeEntryId);
    }

    @Override
    public List<TimeEntry> list() {
        return new ArrayList<>(inMemoryEntries.values());
    }


    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        if (!inMemoryEntries.containsKey(id))
            return null;
        inMemoryEntries.remove(id);
        timeEntry.setId(id);
        inMemoryEntries.put(id, timeEntry);
        return inMemoryEntries.get(id);

    }

    @Override
    public void delete(long id) {
        if (!inMemoryEntries.containsKey(id))
            return;

        inMemoryEntries.remove(id);

    }
}
