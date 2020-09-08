package pl.EskulapSnake.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.EskulapSnake.model.VisitType;
import pl.EskulapSnake.repository.VisitTypeRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class VisitTypeService {
    private VisitTypeRepository visitTypeRepository;

    public VisitTypeService (VisitTypeRepository visitTypeRepository) {
        this.visitTypeRepository = visitTypeRepository;
    }

    public List<VisitType> findAll(){
        return visitTypeRepository.findAll();
    }

    public VisitType findById(Long id){
        return visitTypeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Visit type not found!"));
    }

//    public List<VisitType> findByEntry(Long id) {
//        return visitTypeRepository.findByEntry(id);
//    }

    @Transactional
    public void deleteAll () {
        visitTypeRepository.deleteAll();
    }

    @Transactional
    public void deleteById(Long id) {
        visitTypeRepository.deleteById(id);
    }

    @Transactional
    public VisitType createNew(VisitType visitType) {
        return visitTypeRepository.save(visitType);
    }

    @Transactional
    public VisitType update (VisitType visitType, Long id) {
        visitType.setId(id);
        return visitTypeRepository.save(visitType);
    }

}
