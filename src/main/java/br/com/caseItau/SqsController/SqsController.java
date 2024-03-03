package br.com.caseItau.SqsController;

import br.com.caseItau.service.SqsMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class SqsController {
//classe usada para testar
    @Autowired
    private SqsMessageService sqsMessageService;

    @GetMapping("/sendMessage")
    public ResponseEntity<?> sendMessage(@RequestBody String message) {
        String SQS = "https://localhost.localstack.cloud:4566/000000000000/acoesCobranca-fila";
        sqsMessageService.sendMessage(SQS, message);
        return ResponseEntity.ok().build();
    }

}
