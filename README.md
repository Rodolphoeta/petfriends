# PetFriends Pedidos - Como rodar e testar

# Se estiver em um novo PC, faça as configurações abaixo:

1. Pré-requisitos — o que você precisa ter instalado
   Antes de clonar o projeto, verifique/instale:
   a) Git
- terminal:
git --version
## Se não tiver: https://git-scm.com/downloads

b) JDK 21
java -version

Se não tiver, baixe o Eclipse Temurin 21 (gratuito):
👉 https://adoptium.net/temurin/releases/?version=21

c) Docker Desktop (necessário para o RabbitMQ)
👉 https://www.docker.com/products/docker-desktop/

2. Clonar o repositório
   Abra o terminal (CMD, PowerShell ou Git Bash) e execute:
   bash
   git clone https://github.com/Rodolphoeta/petfriends.git
   cd petfriends

3. Abrir no IntelliJ (ou IDE da sua preferencia)

  1. Abra o IntelliJ IDEA 
  2. Clique em "Open" (ou File → Open)
  3. Navegue até a pasta petfriends que você clonou 
  4. Selecione o arquivo pom.xml e clique em Open 
  5. Na janela que aparecer, escolha "Open as Project"

4. Configurar o JDK 21 no IntelliJ
   Após abrir:
  1. Vá em File → Project Structure (atalho: Ctrl+Alt+Shift+S)
  2. Em Project SDK, selecione o JDK 21 
  3. Se não aparecer, clique em "Add SDK" → "JDK" → aponte para a pasta onde instalou o Java 21 
  4. Clique OK

# FAÇA ISSO ANTES DE RODAR A APLICAÇÃO

5. Subir o RabbitMQ com Docker
   O projeto depende do RabbitMQ. Antes de rodar a aplicação, 
   execute no terminal (na pasta do projeto):
docker compose up -d

6. Rodar a aplicação no IntelliJ

  1. No painel lateral esquerdo, navegue até:
     src/main/java/com/petfriends/pedidos/
  2. Encontre a classe principal (geralmente termina em Application.java)
  3. Clique com botão direito → Run 'PedidosApplication.java'
## Ou use o atalho: abra a classe main e 
## pressione Shift+F10 ou clique no ícone ▶️ verde ao lado do método main.

7. Verificar que está rodando

- API: http://localhost:8080/api/pedidos
- H2 Console: http://localhost:8080/h2-console
    - JDBC URL: jdbc:h2:mem:pedidosdb
    - User: sa
    - Senha: (vazia)

# O docker-compose.yml já está no projeto. Para verificar se subiu:
docker compose ps

# Insomnia
Importe o arquivo no caminho abaixo para o insomnia
docs/insomnia/pedidos-petfriends-insomnia.yaml 
para configurar as requisições HTTP HTTPs

# OBS: AO TESTAR REQUISIÇÕES HTTP, EDITAR O ID GERADO AO CRIAR PEDIDO NO INSOMNIA
1. Clique em "Criar Pedido"
2. Vá na aba Scripts → After-response
3. Cole isso:
   const response = insomnia.response.json();
   if (response && response.id) {
   insomnia.environment.set("Id", response.id);
   }

- Ctrl + E Edit Environments → click to edit
- use o json abaixo para alterar endpoints url e id 
- (CRIE APENAS url NO JSON SE USAR O MÉTODO ACIMA, POIS id SERÁ IMPLEMENTADO AUTOMATICAMENTE)
  {
  "url" : "http://localhost:8080",
  "id": "[altere-pelo-token-gerado-ao-criar-pedido]"
  }
- id (altere o valor do id no JSON com o que foi gerado ao criar pedido)

# Acesse a UI de gerenciamento em:
- UI de gerenciamento: http://localhost:15672
- usuário: guest
- senha: guest


# CRIEI UM docker-compose.yml PARA CONFIGURAR E INICIAR O RABBIT 
- Subir: docker compose up -d (JÁ FOI FEITO NO PASSO INICIAL)
- Parar: docker compose down
- Ver logs: docker compose logs -f rabbitmq

# SE TIVER APRESENTADO FALHA, FAÇA O PROCEDIMENTO ACIMA E REINICIE NOVAMENTE A APLICAÇÃO DEPOIS

# Como acessar H2: 
- abra http://localhost:8080/h2-console e use: 
- JDBC URL: jdbc:h2:mem:pedidosdb 
- user sa, 
- senha vazia.

# Faça a query
select * from pedidos;
select * from pedido_items;

## Endpoints principais
- **Criar pedido**
    - `POST /api/pedidos`
    - Body (JSON):
      ```json
  {
  "clienteId":"<UUID>",
  "items":[
  {"produtoId":"<UUID>","nome":"Ração","quantidade":2,"precoUnitario":120.00}
  ]
  }
    ```
    - Resposta: `{ "id": "<UUID>" }`

- **Confirmar pagamento**
    - `POST /api/pedidos/{id}/confirmar-pagamento`

- **Enviar pedido**
    - `POST /api/pedidos/{id}/enviar`

- **Cancelar pedido**
    - `POST /api/pedidos/{id}/cancelar`

- **Buscar pedido**
    - `GET /api/pedidos/{id}`

## Testes rápidos (curl)
# Criar pedido:

bash
curl -s -X POST http://localhost:8080/api/pedidos \
  -H "Content-Type: application/json" \
  -d '{"clienteId":"00000000-0000-0000-0000-000000000000","items":[{"produtoId":"11111111-1111-1111-1111-111111111111","nome":"Ração","quantidade":2,"precoUnitario":120.00}]}' | jq .

# Confirmar pagamento:

bash
curl -X POST http://localhost:8080/api/pedidos/<ID>/confirmar-pagamento

# Enviar pedido:

bash
curl -X POST http://localhost:8080/api/pedidos/<ID>/enviar

# Buscar pedido:
bash
curl http://localhost:8080/api/pedidos/<ID> | jq .