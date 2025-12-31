# Nível 01: gRPC Unary (Síncrono)

Este módulo contém uma implementação básica de gRPC do tipo **Unary**. No modelo Unary, o cliente envia uma única requisição ao servidor e recebe uma única resposta (como uma chamada HTTP tradicional, mas sobre HTTP/2 e com Protocol Buffers).

## O que este código faz?

O serviço é uma **Calculadora de Soma**:

1. O **Cliente** envia dois números inteiros (`first_number` e `second_number`).
2. O **Servidor** recebe os números, realiza a soma e retorna o resultado.
3. O contrato é definido no arquivo `proto/calculator.proto`.

## Estrutura Simplificada

Para este estudo, configuramos o Maven para aceitar arquivos fora do padrão `src/main/java`, permitindo que o código fique na raiz da pasta:

- `proto/`: Contrato da API.
- `CalculatorServer.java`: Inicializa o servidor gRPC na porta 50051.
- `CalculatorServiceImpl.java`: Contém a lógica de negócio (soma).
- `CalculatorClient.java`: Cliente que realiza a chamada de teste.

## Como Rodar

### 1. Compilar e Gerar Classes gRPC

A partir da pasta raiz do projeto (`/gRCP`), execute:

```powershell
mvn clean compile
```

Entre na pasta do módulo:

```powershell
cd 01-unary
```

### 2. Rodar o Servidor

Certifique-se de estar na pasta do módulo e execute:

```powershell
mvn exec:java "-Dexec.mainClass=com.ferro.unary.CalculatorServer"
```

### 3. Rodar o Cliente

Certifique-se de estar na pasta do módulo e execute:

```powershell
mvn exec:java "-Dexec.mainClass=com.ferro.unary.CalculatorClient"
```
