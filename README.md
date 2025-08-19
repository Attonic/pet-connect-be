# Projeto Backend - Adoção de Pets

Este projeto é um backend desenvolvido em **Spring Boot com Java** para gerenciar o site de adoções de pets.

---

## Fluxo de Trabalho Git com Kanban

Este guia descreve o passo a passo para o desenvolvimento de tarefas utilizando **Git**, integrado a um quadro **Kanban**.

---

### Passos

#### 1. Início / Sincronização
Antes de começar uma nova tarefa, garanta que seu repositório local está atualizado com a branch principal de desenvolvimento:

```bash
git checkout development
git pull origin development
```

#### 2. Criar Branch da Tarefa
Crie uma nova branch a partir da `development`.  
O nome da branch deve seguir o padrão: `KAN- + número da tarefa`.

```bash
git checkout -b KAN-01-implementar-rota-get
```

#### 3. Desenvolvimento
Realize as alterações necessárias no código para completar a tarefa.

*(Edite seus arquivos conforme necessário)*

#### 4. Commits
Salve seu progresso em pequenos commits.  
A mensagem de commit também deve **referenciar o número da tarefa**.

```bash
git add .
git commit -m "KAN-01: adiciona validação de entrada"
```

#### 5. Enviar para o Repositório
Faça o push da sua branch de tarefa para o repositório remoto (GitHub).

```bash
git push origin KAN-01-implementar-rota-get
```

#### 6. Pull Request (PR)
No GitHub, abra um **Pull Request** da sua branch para a branch `development`.

*(Ação realizada na interface do GitHub)*

#### 7. Atualizar o Kanban
Após solicitar o **Pull Request**, mova o card correspondente à sua tarefa para a coluna de **"Análise"** ou **"Code Review"**.

*(Ação realizada no quadro Kanban)*

#### 8. Nova Tarefa
Ao iniciar uma nova tarefa, repita o processo a partir do **Passo 1**, garantindo que você volte para a `development` e a atualize:

```bash
git checkout development
git pull origin development
```

---

### ⚠️ Pontos Importantes
- **Não mude para a branch `development` antes de commitar todas as suas alterações na branch da tarefa.** Você pode perder trabalho não salvo.  
- **🧪 Testes:** Utilize o Postman para testar suas rotas e endpoints antes de finalizar a tarefa e solicitar o Pull Request.  
- **Clone Inicial:** Se for seu primeiro acesso ao projeto, clone o repositório com:  

```bash
git clone [URL_DO_REPOSITÓRIO]
```

---

**Mantenha o fluxo consistente para garantir organização e colaboração eficiente no time!**
