package com.ferro.unary;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;

public class CalculatorServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 50051; // Porta padrão comum para gRPC

        // Builder para configurar e iniciar o servidor
        Server server = ServerBuilder.forPort(port)
                .addService(new CalculatorServiceImpl()) // Registra a implementação da lógica
                .build();

        System.out.println("Servidor gRPC iniciado na porta: " + port);

        server.start(); // Inicia o servidor de forma assíncrona

        // Shutdown hook: Garante que o servidor feche as conexões antes de encerrar o
        // processo Java (Graceful Shutdown)
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Recebido comando de shutdown...");
            server.shutdown();
            System.out.println("Servidor finalizado.");
        }));

        server.awaitTermination(); // Mantém a thread principal viva aguardando chamadas
    }

    // A implementação da lógica gerada pelo Proto
    // O nome "CalculatorServiceImpl" você escolheu (Instância livre).
    // O "CalculatorServiceImplBase" é o nome obrigatório gerado pelo gRPC com base
    // no seu .proto.
    static class CalculatorServiceImpl extends CalculatorServiceGrpc.CalculatorServiceImplBase {

        @Override
        // 1. request: É a ENTRADA.
        // Contém os dados que o cliente enviou. Sem ele, você não sabe o que calcular.

        // 2. responseObserver: É a SAÍDA.
        // Como o método é 'void', este objeto é a sua única ferramenta para
        // empurrar a resposta de volta ao cliente pela rede.
        public void sum(SumRequest request, StreamObserver<SumResponse> responseObserver) {

            // 1. EXTRAÇÃO DE DADOS:
            // O gRPC transforma o snake_case do proto (first_number) em CamelCase no Java
            // (getFirstNumber).
            int result = request.getFirstNumber() + request.getSecondNumber();

            // 2. CONSTRUÇÃO DA RESPOSTA:
            // Mensagens gRPC são imutáveis. Você sempre usa um Builder para criar a
            // resposta.
            SumResponse response = SumResponse.newBuilder()
                    .setResult(result)
                    .build();

            // 3. O responseObserver (OBRIGATÓRIO):
            // Como o gRPC é assíncrono e suporta streaming, ele não usa "return".
            // O responseObserver é o seu "tubo" de comunicação com o cliente.

            // onNext: Envia o objeto de resposta através do tubo.
            // Em métodos "Unary" (comum), chamamos apenas uma vez.
            responseObserver.onNext(response);

            // 4. FINALIZAÇÃO (CRUCIAL):
            // Avisa ao cliente que a mensagem acabou e o servidor pode fechar a conexão.
            // Se você esquecer isso, o cliente ficará esperando (timeout) até travar.
            responseObserver.onCompleted();
        }
    }
}