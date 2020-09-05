package pl.EskulapSnake.controller;

import org.hibernate.hql.internal.ast.util.NodeTraverser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.EskulapSnake.model.VisitType;
import pl.EskulapSnake.service.VisitTypeService;

@RestController
@RequestMapping("/visits")
public class VisitTypeController {

    private VisitTypeService visitTypeService;

    @Autowired
    public VisitTypeController(VisitTypeService visitTypeService) {
        this.visitTypeService = visitTypeService;
    }

}
