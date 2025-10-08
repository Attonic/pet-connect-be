# Documentação Técnica: Implementação do Cadastro de Pets

**Autor:** Marcelo
**Status:** Concluído (para a Fase 1)
**Foco:** Detalhamento técnico da implementação da funcionalidade de cadastro de pets, respondendo a questões arquiteturais sobre identificação de usuário e upload de imagens.

---

## 1. Introdução

Este documento descreve o processo de desenvolvimento e a arquitetura da funcionalidade de **cadastro de novos pets**. O objetivo é registrar as decisões técnicas e os desafios encontrados, servindo como um guia para a equipe e respondendo proativamente a duas questões centrais levantadas durante o desenvolvimento:

1.  **Como o backend identificará o usuário que está cadastrando o pet?**
2.  **Qual a estratégia correta para o upload das imagens dos pets?**

---

## 2. Visão Geral e Decisões de Arquitetura

### 2.1. Estratégia para Upload de Imagens: `multipart/form-data`

Uma das primeiras decisões de arquitetura foi definir como as imagens dos pets seriam enviadas e armazenadas. A abordagem de converter a imagem para `base64` foi considerada e **descartada**, pois não é uma boa prática (aumenta o tamanho do payload da requisição e consome mais recursos do servidor).

-   **Solução Adotada:** A implementação segue a melhor prática do mercado, utilizando **`multipart/form-data`**. Isso permite que o frontend envie os dados do pet (JSON) e o arquivo da imagem na mesma requisição, de forma eficiente.
    -   O `PetController` já está configurado para receber este tipo de dado: `@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)`.

-   **Armazenamento (Desacoplado):** Para alinhar com a ideia de "guardar a imagem em um drive e salvar o caminho (path) no banco", foi criada a interface `FileStorageService.java`. Ela abstrai a lógica de armazenamento.
    -   **Implementação Atual (Simulada):** Para não travar o desenvolvimento, a implementação `FileStorageServiceImpl` **simula** o upload e retorna uma URL fixa. Isso permite que a equipe de frontend possa trabalhar no formulário de cadastro enquanto a infraestrutura de armazenamento de arquivos é decidida e implementada em paralelo.

### 2.2. Estratégia para Identificação do Usuário (Tutor)

Para responder como o backend identificará o tutor do pet, a solução foi dividida em duas fases para permitir o desenvolvimento paralelo e evitar conflitos com a branch de autenticação.

-   **Fase 1 (Implementação Atual - Solução Temporária):** Para que a funcionalidade pudesse ser desenvolvida e testada sem depender da feature de login, o `PetServiceImpl` adota uma lógica provisória: ele busca o **primeiro usuário encontrado no banco** e o associa como tutor do pet. 

    ```java
    // Lógica temporária em PetServiceImpl.java
    User tutor = userRepository.findAll().stream().findFirst()
            .orElseThrow(() -> new RuntimeException("Nenhum usuário encontrado..."));
    pet.setTutor(tutor);
    ```

-   **Fase 2 (Implementação Definitiva - Próximo Passo):** O plano final, já documentado na seção de próximos passos, é substituir essa lógica temporária. A versão definitiva obterá o usuário diretamente do **contexto de segurança do Spring**, após o login via token JWT estar integrado ao projeto.

### 2.3. Resolução dos 17 Erros de Compilação: Padronização do Modelo

Durante o desenvolvimento, um grande obstáculo surgiu na forma de 17 erros de compilação que impediam a execução do projeto.

-   **Diagnóstico:** A causa raiz foi uma **inconsistência fundamental no modelo de dados do usuário**. O projeto utilizava duas entidades para representar a mesma coisa: `User.java` (no CRUD de usuários) e `Usuario.java` (na nova funcionalidade de Pet).

-   **A Solução (Refatoração):**
    1.  **Padronização do Modelo:** A entidade `User.java` foi definida como o modelo padrão para usuários em toda a aplicação.
    2.  **Refatoração do Código:** A entidade `Pet.java` e o serviço `PetServiceImpl.java` foram ajustados para usar exclusivamente `User` e `UserRepository`.

    Essa refatoração não apenas corrigiu todos os erros, mas também unificou a base de código, tornando-a mais robusta e fácil de manter.

---

## 3. Estrutura de Código Final

-   **`PetController.java`:** Expondo o endpoint `POST /pets` que consome `multipart/form-data`.
-   **`PetServiceImpl.java`:** Orquestrando a lógica de negócio, incluindo a associação (temporária) do tutor e a chamada ao serviço de armazenamento de imagem.
-   **`Pet.java`:** Entidade com a associação `@ManyToOne` para a entidade `User`, representando o tutor.
-   **`FileStorageService.java`:** Interface que desacopla a lógica de upload de arquivos.

---

## 4. Conclusão e Próximos Passos

A funcionalidade de cadastro de pets foi implementada com sucesso, com uma arquitetura que responde às questões levantadas pela equipe e segue as boas práticas de mercado.

**Próximos Passos Sugeridos:**
1.  **Reintegrar a Segurança:** Substituir a lógica temporária de busca de tutor pela obtenção do usuário autenticado.
2.  **Implementar o Armazenamento de Arquivos:** Substituir a implementação simulada do `FileStorageService` por uma real (ex: AWS S3, Firebase Storage).
3.  **Criar Testes de Integração** para o endpoint de cadastro.
