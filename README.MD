# Roadmap de Estudos: gRPC - Do Básico ao Avançado

Este documento detalha a progressão de aprendizado sugerida para este repositório.

## 🟢 Nível 1: Básico (Unary RPC)

O foco inicial é entender o ciclo de vida de uma requisição simples e a sintaxe fundamental do Protocol Buffers.

- **Conceitos:** Definição de `service`, `message`, sintaxe proto3, compilação com `protoc`, implementação de Server e Client.
- **Sugestão de código:** Criar um serviço de "Calculadora" ou "Conversor de Moedas".

## 🟡 Nível 2: Protocol Buffers Avançado

Dominar a estruturação de dados para lidar com cenários reais de APIs.

- **Conceitos:** Uso de tipos `enum`, `map`, `oneof`, importação de outros arquivos `.proto` e `google.protobuf.Timestamp`.
- **Sugestão de código:** Evoluir o serviço para lidar com perfis de usuários com múltiplos endereços e status.

## 🟠 Nível 3: Streaming

Explorar a capacidade do gRPC de lidar com fluxos de dados contínuos de forma eficiente.

- **Conceitos:**
  - **Server Streaming:** Servidor enviando dados em pedaços (ex: logs/feeds).
  - **Client Streaming:** Cliente enviando arquivos pesados em partes.
  - **Bi-directional Streaming:** Comunicação em tempo real (ex: chat).

## 🔴 Nível 4: Middlewares e Segurança

Tornar a aplicação robusta e pronta para ser exposta à internet.

- **Conceitos:** Interceptors (Unary e Stream), Autenticação via JWT/OAuth2, Metadados (Context) e criptografia TLS/SSL.
- **Sugestão de código:** Implementar um validador de token que bloqueia chamadas não autorizadas.

## 🟣 Nível 5: Resiliência e Produção

O nível final para dominar arquiteturas de microserviços.

- **Conceitos:** Tratamento de erros rico (`status` e `errdetails`), Deadlines/Timeouts, Retries, Health Checks e Service Reflection.
