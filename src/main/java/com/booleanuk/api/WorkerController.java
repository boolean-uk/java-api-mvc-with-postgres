package com.booleanuk.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

public class WorkerController {
    private WorkerRepository workers;

    public WorkerController() throws SQLException {
        this.workers = new WorkerRepository();

    }

    @GetMapping
    public List<Worker> getAll() throws SQLException {
        return this.workers.getAll();
    }

    @GetMapping("/{id}")
    public Worker getOne(@PathVariable (name = "id") long id) throws SQLException {
        Worker worker = this.workers.get(id);
        if (worker == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return worker;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Worker create(@RequestBody Worker worker) throws SQLException {
        Worker theWorker = this.workers.add(worker);
        if (theWorker == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create the specified Customer");
        }
        return theWorker;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Worker update(@PathVariable (name = "id") long id, @RequestBody Worker worker) throws SQLException {
        Worker toBeUpdated = this.workers.get(id);
        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.workers.update(id, worker);
    }

    @DeleteMapping("/{id}")
    public Worker delete(@PathVariable (name = "id") long id) throws SQLException {
        Worker toBeDeleted = this.workers.get(id);
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.workers.delete(id);
    }
}
