package br.pegz.pocigniteboot.repository;

import br.pegz.pocigniteboot.model.Alert;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource
public interface AlertRepository extends CrudRepository<Alert, UUID> {
}
