package com.ferro.buffer;

import com.google.protobuf.Timestamp;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.time.Instant;

public class PayrollClient {
        public static void main(String[] args) {
                ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                                .usePlaintext()
                                .build();

                PayrollServiceGrpc.PayrollServiceBlockingStub stub = PayrollServiceGrpc.newBlockingStub(channel);

                // Criando um Timestamp do Google a partir do Instant do Java
                Instant now = Instant.now();
                Timestamp timestamp = Timestamp.newBuilder()
                                .setSeconds(now.getEpochSecond())
                                .setNanos(now.getNano())
                                .build();

                // Construindo o funcionário com tipos avançados
                Employee employee = Employee.newBuilder()
                                .setId(101)
                                .setName("Felipe Ferro")
                                .setStatus(EmployeeStatus.ACTIVE)
                                .addSkills("Java") // repeated
                                .addSkills("gRPC")
                                .putMetadata("dept", "Engineering")
                                .setHireDate(timestamp)
                                .build();

                PayrollResponse response = stub.calculateSalary(
                                PayrollRequest.newBuilder().setEmployee(employee).build());

                System.out.println("Server says: " + response.getMessage());
                channel.shutdown();
        }
}