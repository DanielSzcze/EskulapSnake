package pl.EskulapSnake.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.EskulapSnake.model.Role;
import pl.EskulapSnake.repository.RoleRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class RoleService {
    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> findAll(){
        return roleRepository.findAll();
    }

    public Role findById(Long id){
        return roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Role not found!"));
    }

//    public List<Role> findByEmployee(Long id) {
//        return roleRepository.findByEmployee(id);
//    }

    @Transactional
    public void deleteAll () {
        roleRepository.deleteAll();
    }

    @Transactional
    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }

    @Transactional
    public Role createNew(Role role) {
        return roleRepository.save(role);
    }

    @Transactional
    public Role update (Role role, Long id) {
        role.setId(id);
        return roleRepository.save(role);
    }

}
