package com.ferro.unary;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class CalculatorClient {
    public static void main(String[] args) {
        // 1. O Channel (Canal) define o endereço e como conectar (é a conexão física)
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext() // Usa HTTP/2 sem criptografia (apenas para desenvolvimento)
                .build();

        // 2. O Stub é o "procurador". Você chama métodos nele como se fossem locais,
        // mas ele faz a chamada de rede para o servidor.
        // O 'BlockingStub' espera a resposta (síncrono).
        CalculatorServiceGrpc.CalculatorServiceBlockingStub stub = CalculatorServiceGrpc.newBlockingStub(channel);

        // 3. Cria a requisição usando o Builder gerado pelo Proto
        SumRequest request = SumRequest.newBuilder()
                .setFirstNumber(15)
                .setSecondNumber(25)
                .build();

        System.out.println("Enviando requisição: " + request.getFirstNumber() + " + " + request.getSecondNumber());

        // 4. Faz a chamada remota de fato
        SumResponse response = stub.sum(request);

        // 5. Exibe o resultado recebido do servidor
        System.out.println("Resposta do Servidor: " + response.getResult());

        // 6. Encerra a conexão do canal para liberar recursos
        channel.shutdown();
    }
}