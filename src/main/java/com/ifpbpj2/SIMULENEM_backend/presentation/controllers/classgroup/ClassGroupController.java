package com.ifpbpj2.SIMULENEM_backend.presentation.controllers.classgroup;

import java.util.UUID;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifpbpj2.SIMULENEM_backend.business.services.classgroup.ClassGroupService;
import com.ifpbpj2.SIMULENEM_backend.model.entities.classgroup.ClassGroup;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.ClassGroupRequestDTO;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/classgroups")
public class ClassGroupController {

    ClassGroupService classGroupService;

    public ClassGroupController(ClassGroupService classGroupService) {
        this.classGroupService = classGroupService;
    }

    @GetMapping("{id}")
    public ResponseEntity<ClassGroup> findById(@PathVariable UUID id) {
        ClassGroup classGroup = classGroupService.findById(id);
        return ResponseEntity.ok().body(classGroup);

    }

    @PostMapping
    public ResponseEntity<ClassGroup> save(@RequestBody @Valid ClassGroupRequestDTO obj) {
        ClassGroup classGroup = classGroupService.save(obj);
        return ResponseEntity.status(HttpStatus.CREATED).body(classGroup);
    }

    @PostMapping("{id}")
    public ResponseEntity<ClassGroup> update(@PathVariable UUID id, @RequestBody @Valid ClassGroupRequestDTO obj) {
        ClassGroup classGroup = classGroupService.update(id, obj);
        return ResponseEntity.ok().body(classGroup);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        classGroupService.deleteById(id);
        return new ResponseEntity<>((HttpStatus.NO_CONTENT));
    }

}
