package com.booleanuk.api.base;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

abstract public class Controller<Value, DTO> {
  protected final Repository<Value, DTO> repository;

  protected Controller(Repository<Value, DTO> repository) {
    this.repository = repository;
  }

  @GetMapping
  public ResponseEntity<List<Value>> get() {
    return new ResponseEntity<>(this.repository.get(), HttpStatus.OK);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<Value> get(@PathVariable int id) {
    return new ResponseEntity<>(this.repository.get(id), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Value> post(@RequestBody DTO value) {
    return new ResponseEntity<>(this.repository.post(value), HttpStatus.CREATED);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<Value> put(@PathVariable int id, @RequestBody DTO value) {
    return new ResponseEntity<>(this.repository.put(id, value), HttpStatus.CREATED);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Value> delete(@PathVariable int id) {
    return new ResponseEntity<>(this.repository.delete(id), HttpStatus.OK);
  }
}
