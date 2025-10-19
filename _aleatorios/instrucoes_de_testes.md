# Instruções para Teste da Funcionalidade de Carteirinha de Saúde

Este documento descreve os passos para testar a criação e consulta da nova Carteirinha de Saúde (`HealthCard`).

## Pré-requisitos

1.  A aplicação `petconnect-be` deve estar rodando localmente. Se não estiver, execute:
    ```bash
    docker-compose up -d --build
    ```
2.  É necessário que exista pelo menos um **tutor** (usuário) cadastrado no banco de dados para associar o pet.

---

## Passo a Passo dos Testes

### Passo 1: Criar um novo Pet

Para criar uma carteirinha, primeiro precisamos de um pet. Use o comando `curl` abaixo para criar um pet. Lembre-se de substituir `"SEU_TUTOR_ID"` por um `id` de tutor válido que exista no seu banco.

**Comando:**
```bash
curl -X POST -H "Content-Type: multipart/form-data" -F 'pet={"name": "Fofinho", "tutorId": "SEU_TUTOR_ID", "species": "Cachorro", "races": "SRD", "age": 2, "sex": "Macho", "about": "Um cãozinho muito amigável"}' http://localhost:8080/pets
```

**Resposta Esperada:**
Um JSON com os dados do pet criado, incluindo o `id` do pet. Anote o `id` para o próximo passo.

```json
{"id":3,"tutorId":"...","name":"Fofinho",...}
```

---

### Passo 2: Criar a Carteirinha de Saúde

Com o `id` do pet em mãos, use o comando abaixo para criar a sua carteirinha de saúde. Substitua `"SEU_PET_ID"` pelo `id` obtido no passo anterior.

**Comando:**
```bash
curl -X POST -H "Content-Type: application/json" -d '{"petId": SEU_PET_ID, "allergies": "Poeira e alguns alimentos", "bloodType": "DEA 1.1 Positivo"}' http://localhost:8080/health-cards
```

**Resposta Esperada:**
Um JSON com os dados da carteirinha de saúde recém-criada, confirmando o sucesso da operação.

```json
{"id":1,"petId":SEU_PET_ID,"allergies":"Poeira e alguns alimentos","bloodType":"DEA 1.1 Positivo"}
```

---

### Passo 3: Verificar a Carteirinha Criada

Para garantir que os dados foram salvos corretamente, consulte a carteirinha usando o `id` do pet.

**Comando:**
```bash
curl http://localhost:8080/health-cards/pet/SEU_PET_ID
```

**Resposta Esperada:**
O mesmo JSON da carteirinha criada no passo 2.

```json
{"id":1,"petId":SEU_PET_ID,"allergies":"Poeira e alguns alimentos","bloodType":"DEA 1.1 Positivo"}
```

Se todos os passos retornarem os resultados esperados, a funcionalidade está operando corretamente.

---

# Instruções para Teste da Funcionalidade de Usuário

Este documento descreve os passos para testar a criação e atualização de usuários.

## Passo a Passo dos Testes

### Passo 1: Criar um novo Usuário

Use o comando `curl` abaixo para criar um novo usuário. Certifique-se de que os valores de `email` e `cpfOrCnpj` sejam únicos no banco de dados para evitar erros.

**Comando:**
```bash
curl -X POST -H "Content-Type: application/json" -d '{"type": "TUTOR","name": "Maria","lastname": "Oliveira","email": "maria.oliveira@example.com","phone": "11988887777","birthOrFoundationDate": "1992-05-20","cpfOrCnpj": "55566677788","password": "senha456","address": {"cep": "02002000","street": "Rua das Flores","neighborhood": "Jardim","city": "São Paulo","uf": "SP"}}' http://localhost:8080/users
```

**Resposta Esperada:**
Um JSON com os dados do usuário criado, incluindo seu `id`. Anote o `id` para o próximo passo.

```json
{"id":"SEU_USER_ID","type":"TUTOR","name":"Maria","lastname":"Oliveira",...}
```

---

### Passo 2: Atualizar o Sobrenome do Usuário (PATCH)

Com o `id` do usuário em mãos, use o comando abaixo para atualizar apenas o sobrenome (`lastname`). Substitua `"SEU_USER_ID"` pelo `id` obtido no passo anterior.

**Comando:**
```bash
curl -X PATCH -H "Content-Type: application/json" -d '{"lastname": "Silva"}' http://localhost:8080/users/SEU_USER_ID/lastname
```

**Resposta Esperada:**
Um JSON com os dados do usuário, mostrando que o campo `lastname` foi atualizado com sucesso.

```json
{"id":"SEU_USER_ID","type":"TUTOR","name":"Maria","lastname":"Silva",...}
```

Se ambos os passos retornarem os resultados esperados, as funcionalidades de criação e atualização parcial de usuário estão operando corretamente.
