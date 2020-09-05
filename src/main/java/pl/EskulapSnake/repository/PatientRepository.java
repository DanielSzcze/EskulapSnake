package pl.EskulapSnake.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import pl.EskulapSnake.model.Patient;

public interface PatientRepository extends PagingAndSortingRepository<Patient, Long> {
}
