package pl.EskulapSnake.service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.EskulapSnake.dto.EmployeeDto;
import pl.EskulapSnake.dto.RoleDto;
import pl.EskulapSnake.model.Employee;
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

    @Transactional
    public void deleteAll () {
        roleRepository.deleteAll();
    }

    @Transactional
    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }

}
