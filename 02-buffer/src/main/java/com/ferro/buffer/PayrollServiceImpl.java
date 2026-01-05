package com.ferro.buffer;

import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class PayrollServiceImpl extends PayrollServiceGrpc.PayrollServiceImplBase {

    @Override
    public void calculateSalary(PayrollRequest request, StreamObserver<PayrollResponse> responseObserver) {
        Employee emp = request.getEmployee();

        // 1. Lendo ENUM (Status)
        String statusInfo = "Status: " + emp.getStatus();
        if (emp.getStatus() == EmployeeStatus.TERMINATED) {
            System.out.println("Atenção: Processando funcionário desligado.");
        }

        // 2. Lendo MAP (Metadata)
        // Usamos getMetadataOrDefault para evitar NullPointer se a chave não existir
        String department = emp.getMetadataOrDefault("dept", "Geral");

        // 3. Lendo LISTA (Skills - Repeated)
        String skillsList = String.join(", ", emp.getSkillsList());
        int skillCount = emp.getSkillsCount();

        // 4. Lendo TIMESTAMP (google.protobuf.Timestamp)
        Timestamp hireDateProto = emp.getHireDate();
        Instant instant = Instant.ofEpochSecond(hireDateProto.getSeconds(), hireDateProto.getNanos());
        String formattedDate = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                .withZone(ZoneId.systemDefault())
                .format(instant);

        // 5. Lendo ONEOF (Documento: CPF ou CNPJ)
        String documentDetail = "";
        if (emp.getDocumentCase() == Employee.DocumentCase.CPF) {
            documentDetail = "CPF: " + emp.getCpf();
        } else if (emp.getDocumentCase() == Employee.DocumentCase.CNPJ) {
            documentDetail = "CNPJ: " + emp.getCnpj();
        } else {
            documentDetail = "Documento não informado";
        }

        // Log no console do servidor para você ver o "Buffer" em ação
        System.out.println("=== Processando Folha de Pagamento ===");
        System.out.println("Funcionário: " + emp.getName());
        System.out.println("Documento: " + documentDetail);
        System.out.println("Departamento: " + department);
        System.out.println("Contratado em: " + formattedDate);
        System.out.println("Habilidades (" + skillCount + "): " + skillsList);
        System.out.println("======================================");

        // Criando a resposta
        String msg = String.format("Folha processada para %s (%s). Dept: %s.",
                emp.getName(), documentDetail, department);

        PayrollResponse response = PayrollResponse.newBuilder()
                .setMessage(msg)
                .setNetSalary(5000.00)
                .build();

        // Enviando e finalizando a chamada Unary
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}