# Registro Técnico do Projeto

---

### **Data: 29/07/2024**

## Tópico: Implementação da Funcionalidade de Carteirinha do Pet (PetCard)

**Objetivo Concluído:** Implementar o CRUD completo para a entidade `PetCard`, que funciona como uma carteirinha de saúde para cada pet, armazenando informações como tipo sanguíneo e alergias.

### 1. Resumo da Implementação

*   **Estrutura de Dados:** Foi criada a entidade `PetCard` com uma relação `@OneToOne` com a entidade `Pet`, garantindo que cada pet tenha um registro de saúde único.
*   **Endpoints da API:** Foram expostos os endpoints REST para as operações de CRUD no `PetCardController`:
    *   `POST /pet-cards`: Cria uma nova carteirinha para um pet específico.
    *   `GET /pet-cards/pet/{petId}`: Busca a carteirinha de um pet pelo ID do pet.
    *   `PUT /pet-cards/{id}`: Atualiza as informações de uma carteirinha existente.
    *   `DELETE /pet-cards/{id}`: Remove uma carteirinha.
*   **Lógica de Negócio:** A camada de serviço (`PetCardServiceImpl`) foi implementada para orquestrar as operações, incluindo uma validação que impede a criação de mais de uma carteirinha para o mesmo pet.

### 2. Decisões de Arquitetura e Pontos de Atenção

*   **Garantia da Relação 1-para-1:**
    *   **Decisão:** A relação `@OneToOne` foi reforçada com uma verificação na camada de serviço (`createPetCard`). Antes de criar uma nova carteirinha, o sistema verifica se já existe uma para o `petId` fornecido. Isso previne inconsistências de dados caso a restrição do banco de dados falhe ou não seja aplicada.

*   **Desacoplamento com DTO:**
    *   **Decisão:** Foi criado o `PetCardDto` para ser a camada de comunicação entre o cliente e a API. Ele utiliza `petId` em vez do objeto `Pet` completo para evitar a exposição de dados aninhados desnecessários e prevenir loops de serialização, tornando a API mais limpa e eficiente.

### 3. Status da Segurança
*   **Observação:** A implementação segue o padrão atual do projeto, que ainda não possui uma camada de segurança. Portanto, os endpoints do `PetCard` estão públicos, e a implementação de autenticação (como JWT) continua sendo uma recomendação crítica para o projeto como um todo.

---

### **Data: 29/07/2024**

## Tópico: Implementação da Funcionalidade de Pet

**Objetivo Concluído:** Implementar o cadastro e gerenciamento de um Pet, incluindo a funcionalidade de upload de imagem de perfil.

### 1. Resumo da Implementação

*   **Cadastro de Pet:** Foi criado o endpoint `POST /pets` que aceita dados do pet em formato JSON e um arquivo de imagem em uma única requisição (`multipart/form-data`).
*   **Atualização de Imagem:** Foi criado um endpoint dedicado, `POST /pets/{id}/image`, para adicionar ou alterar a imagem de um pet já existente.
*   **Armazenamento de Arquivos:** Foi implementada uma solução para salvar as imagens em um diretório local (`/uploads`), com a URL de acesso sendo salva no banco de dados junto às informações do pet.
*   **Acesso às Imagens:** O servidor foi configurado para expor o diretório `/uploads` publicamente através da rota `/images/**`, permitindo que o frontend exiba as fotos.

### 2. Decisões de Arquitetura e Pontos de Atenção

*   **Obrigatoriedade da Imagem no Cadastro:**
    *   **Decisão no Backend:** A API foi desenvolvida para ser flexível. O envio da imagem no momento do cadastro é **opcional**. O backend não exige a imagem para criar um registro de pet.
    *   **Responsabilidade do Frontend:** A equipe de frontend tem a autonomia para decidir se o formulário na interface do usuário irá tratar a imagem como um campo obrigatório para o usuário final.

*   **Armazenamento de Arquivos em Produção:**
    *   A solução atual de salvar arquivos em um diretório local é adequada apenas para o ambiente de desenvolvimento. Para produção, é **necessário** substituir essa implementação por um serviço de armazenamento de objetos em nuvem (como AWS S3, Google Cloud Storage, etc.) para garantir escalabilidade e segurança dos dados.

### 3. Status da Segurança (Tópico Crítico)

*   **Constatação:** Atualmente, **não há uma camada de segurança (como JWT ou Spring Security) implementada no projeto.**
*   **Impacto:** Todos os endpoints criados, incluindo os de criação e deleção de pets, estão **públicos e desprotegidos**.
*   **Recomendação Urgente:** A implementação da autenticação e autorização de rotas é um passo crítico e deve ser priorizada antes de o projeto avançar para um ambiente de produção ou de testes com dados reais.

---

### **Data: 30/07/2024**

## Tópico: Refatoração da Carteirinha de Saúde (HealthCard) - KAN-5

**Objetivo Concluído:** Refatorar a funcionalidade `PetCard` para `HealthCard`, incluindo DTOs, Controllers e endpoints, e testar o fluxo completo.

### 1. Resumo da Implementação
*   **Refatoração:** A entidade `PetCard` foi renomeada para `HealthCard`, assim como todas as classes relacionadas (`PetCardDto` para `HealthCardDto`, `PetCardController` para `HealthCardController`, etc.).
*   **Endpoints Atualizados:** Os endpoints foram ajustados para a nova nomenclatura (`/health-cards`).

### 2. Testes Realizados

A nova funcionalidade foi testada manualmente para garantir a robustez e o comportamento esperado dos endpoints. Os seguintes testes foram executados com sucesso:

1.  **Criação de um novo Pet:**
    *   **Ação:** Foi enviado um `POST` para `/pets` com os dados de um novo pet.
    *   **Resultado:** O pet foi criado com sucesso, retornando um JSON com o `id` do novo pet.

2.  **Criação da Carteirinha de Saúde:**
    *   **Ação:** Foi enviado um `POST` para o novo endpoint `/health-cards`.
    *   **Problema Inicial:** A aplicação retornou `404 Not Found`. A investigação apontou que a imagem Docker não havia sido reconstruída com as novas alterações.
    *   **Solução:** A aplicação foi reiniciada com o comando `docker-compose up -d --build` para forçar a reconstrução da imagem.
    *   **Resultado:** A carteirinha foi criada com sucesso, retornando os dados esperados.

3.  **Consulta da Carteirinha de Saúde:**
    *   **Ação:** Foi enviado um `GET` para `/health-cards/pet/{petId}`.
    *   **Resultado:** A API retornou os dados exatos da carteirinha criada, confirmando que todo o fluxo está funcionando como esperado.

**Conclusão:** Os testes manuais confirmam que a refatoração foi bem-sucedida e a funcionalidade de Carteirinha de Saúde está 100% operacional.
---

### **Data: 31/07/2024**

## Tópico: Diagnóstico de Erro 500 e Implementação de PATCH para Usuário

**Objetivo Concluído:** Diagnosticar um erro 500 na criação de usuários e implementar uma rota `PATCH` para atualização parcial de dados.

### 1. Diagnóstico do Erro `500 Internal Server Error`

*   **Problema:** Ao tentar criar um usuário com `POST /users`, a API retornava um erro `500 Internal Server Error` de forma consistente, mesmo com uma requisição `curl` aparentemente correta.
*   **Investigação:** Após várias tentativas, incluindo a correção do tipo de usuário (`type: "TUTOR"`) e a reversão de código não relacionado, o erro persistia. A hipótese final foi de que estávamos violando uma restrição de unicidade no banco de dados.
*   **Causa Raiz:** A investigação confirmou que os dados de teste (email e CPF/CNPJ) já existiam no banco de dados de execuções anteriores. A tentativa de inserir um registro duplicado causava um `DataIntegrityViolationException` não tratado especificamente, resultando no erro 500.
*   **Solução:** O teste foi refeito com dados completamente novos para o usuário (email e CPF/CNPJ diferentes), o que resultou na criação bem-sucedida do usuário, validando a teoria.

### 2. Implementação da Funcionalidade PATCH

*   **Endpoint:** `PATCH /users/{id}/lastname`
*   **Funcionalidade:** Atualiza apenas o campo `lastname` do usuário especificado, evitando a necessidade de enviar o objeto de usuário completo.
*   **Implementação:**
    1.  **`UserController`**: Foi reativado e ajustado o método `updateUserLastname` para o endpoint `PATCH`.
    2.  **`UserService`**: A interface foi atualizada com a assinatura do método `updateUserLastname(UUID id, String lastname)`.
    3.  **`UserServiceImpl`**: Foi implementada a lógica para buscar o usuário por ID, atualizar o campo `lastname` e salvar a entidade de volta no banco.