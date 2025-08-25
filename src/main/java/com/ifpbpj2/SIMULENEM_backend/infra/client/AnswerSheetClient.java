package com.ifpbpj2.SIMULENEM_backend.infra.client;

import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.answerSheetClient.GeracaoCartaoRespostaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "answerSheetClient", url = "https://localhost:8080")
public interface AnswerSheetClient {

        @PostMapping("/cartao-resposta/model")
        ResponseEntity<Byte[]> generatedAnswerSheet(@RequestBody GeracaoCartaoRespostaDTO geracaoCartaoRespostaDTO);

}
