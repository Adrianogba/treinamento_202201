package com.indracompany.treinamento.controller.rest;

import com.indracompany.treinamento.model.entity.GenericEntity;
import com.indracompany.treinamento.model.service.GenericCrudService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author efmendes
 *
 * @param <T>
 * @param <S>
 * @param <I>
 */
@Slf4j
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public abstract class EmptyGenericCrudRest<T extends GenericEntity<I>, I, S extends GenericCrudService<T, I, ?>> {

  private static final long serialVersionUID = -3853594377194808570L;

  @Autowired
  protected GenericCrudService<T, I, ?> service;

  @Getter
  @Setter
  private transient T entity;

  @Getter
  @Setter
  private transient List<T> list;

  public S getService() 
  {
	  return (S) this.service;
  }
}
