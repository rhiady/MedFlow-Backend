# 💊 MedFlow Backend

API REST para gerenciamento de farmácia, incluindo medicamentos, usuários, clientes, fornecedores e vendas.

---

## 📌 Sobre o Projeto

O **MedFlow Backend** é uma aplicação desenvolvida com Spring Boot que permite o gerenciamento de operações de uma farmácia.

O sistema permite:

- Cadastro e gerenciamento de medicamentos  
- Registro de vendas  
- Controle de quantidade de medicamentos  
- Gerenciamento de usuários  
- Cadastro de clientes e fornecedores  

---

## 🚀 Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Spring Data JPA
- Hibernate
- Maven
- MySQL ou PostgreSQL
- Lombok
- MapStruct

---

## 📂 Estrutura do Projeto

```bash
src/main/java/com/

├── domains/         # Entidades (Medicamento, Venda, Usuario, ItemVenda, Cliente, Fornecedor)
├── dtos/            # Objetos de transferência de dados
├── repositories/    # Interfaces JPA
├── services/        # Regras de negócio
├── resources/       # Controllers (REST API)
├── mappers/         # Conversão Entity ↔ DTO
├── config/          # Configurações
└── exceptions/      # Tratamento de erros
⚙️ Funcionalidades
👤 Usuários

Cadastro de usuários

Definição de funções (ADMIN, FUNCIONARIO)

💊 Medicamentos

Cadastro de medicamentos

Controle de quantidade disponível

Classificação por categoria

👥 Clientes

Cadastro e gerenciamento de clientes

🚚 Fornecedores

Cadastro e gerenciamento de fornecedores

🛒 Vendas

Registro de vendas

Associação com itens de venda

Cálculo automático do valor total

Atualização automática da quantidade de medicamentos após venda

🔗 Endpoints Principais
📌 Medicamentos
GET    /medicamentos
GET    /medicamentos/{id}
POST   /medicamentos
PUT    /medicamentos/{id}
DELETE /medicamentos/{id}
📌 Usuários
GET    /usuarios
GET    /usuarios/{id}
POST   /usuarios
PUT    /usuarios/{id}
DELETE /usuarios/{id}
📌 Clientes
GET    /clientes
GET    /clientes/{id}
POST   /clientes
PUT    /clientes/{id}
DELETE /clientes/{id}
📌 Fornecedores
GET    /fornecedores
GET    /fornecedores/{id}
POST   /fornecedores
PUT    /fornecedores/{id}
DELETE /fornecedores/{id}
📌 Vendas
GET    /vendas
GET    /vendas/{id}
POST   /vendas
PUT    /vendas/{id}
DELETE /vendas/{id}
📌 Itens de Venda
GET    /itens-venda
GET    /itens-venda/{id}
PUT    /itens-venda/{id}
DELETE /itens-venda/{id}

✅ Regras de Negócio

Os itens de venda são enviados diretamente no POST /vendas

A venda:

associa cliente e usuário

recebe a lista de itens vendidos

calcula automaticamente o valor total

Alterações (PUT) e remoções (DELETE) de vendas devem atualizar corretamente a quantidade dos medicamentos

O sistema mantém consistência dos dados após cada operação

▶️ Como Executar o Projeto
1️⃣ Clonar o repositório
git clone https://github.com/seu-usuario/medflow-backend.git

❗ Tratamento de Erros

O sistema possui tratamento global de exceções:

400 → Erro de validação

404 → Recurso não encontrado

500 → Erro interno do servidor

📌 Melhorias Futuras

Autenticação com JWT

Controle de permissões avançado

Relatórios de vendas

Dashboard administrativo
