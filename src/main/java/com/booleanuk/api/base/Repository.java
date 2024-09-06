package com.booleanuk.api.base;

import java.util.List;

import org.springframework.web.server.ResponseStatusException;

public interface Repository<Value, DTO> {
  public List<Value> get() throws ResponseStatusException;

  public Value get(int id) throws ResponseStatusException;

  public Value post(DTO value) throws ResponseStatusException;

  public Value put(int id, DTO value) throws ResponseStatusException;

  public Value delete(int id) throws ResponseStatusException;
}
