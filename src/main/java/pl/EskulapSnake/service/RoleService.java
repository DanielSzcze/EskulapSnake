package pl.EskulapSnake.service;
import org.springframework.stereotype.Service;
import pl.EskulapSnake.repository.RoleRepository;

@Service
public class RoleService {
    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
}
