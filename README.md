<h3 align="center">
 Projeto - Ação de Cobrança, Case Itau.
</h3>


## :rocket: Tecnologias utilizadas

* Java 21
* Spring Boot
* Spring Cloud AWS
* AWS
* SQS (Simple Queue Service)
* Localstack

Referências utilizadas:
https://docs.aws.amazon.com/AWSSimpleQueueService/latest/SQSDeveloperGuide/sqs-visibility-timeout.html
https://docs.awspring.io/spring-cloud-aws/docs/3.1.0/reference/html/index.html#starter-dependencies


Microserviço responsável por fazer pulling em uma fila SQS, quando houver mensagem, deve dar inicio ao processamento das ações de cobrança ao cliente.
-Realiza as tratativas e dependendo da quantidade de dias em atraso de pagamento do cliente, faz requisição aos microserviços de envio de 
 notificação através de mensagem enviada a fila correspondende ao microserviço a ser solicitado.
-Envia mensagem com dados da notificação realizada para serem persistidos na base de dados.
