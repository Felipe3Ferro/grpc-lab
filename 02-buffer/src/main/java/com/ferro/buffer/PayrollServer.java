package com.ferro.buffer;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;

public class PayrollServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        // Porta padrão do seu lab
        int port = 50051;

        Server server = ServerBuilder.forPort(port)
                .addService(new PayrollServiceImpl())
                .build();

        System.out.println("Nível 2: Servidor gRPC (Buffer Avançado) iniciado na porta " + port);
        server.start();

        // Faz o programa esperar até que o servidor seja parado
        server.awaitTermination();
    }
}