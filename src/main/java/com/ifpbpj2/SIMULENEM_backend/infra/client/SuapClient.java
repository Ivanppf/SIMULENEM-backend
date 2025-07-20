package com.ifpbpj2.SIMULENEM_backend.infra.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.LoginDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response.SuapAccessDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response.UserPageableSuapDTO;

@FeignClient(name = "suapClient", url = "https://suap.ifpb.edu.br")
public interface SuapClient {

        @PostMapping("/api/jwt/obtain_token/")
        ResponseEntity<SuapAccessDTO> login(@RequestBody LoginDTO loginRequest);

        @GetMapping("/api/recursos-humanos/servidores/v1/")
        ResponseEntity<UserPageableSuapDTO> getAllEmployees(@RequestHeader("Authorization") String token,
                        @RequestParam("search") String username);

        @GetMapping("/api/ensino/alunos/v1/")
        ResponseEntity<String> isStudent(@RequestHeader("Authorization") String token,
                        @RequestParam("search") String username);

}
