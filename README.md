# PetFriends Pedidos - Como rodar e testar

# FAÇA ISSO ANTES DE RODAR A APLICAÇÃO

# Para Rodar o RabbitMQ, rode o comando docker no terminal para a aplicação funcionar:

docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management

# Insomnia
Importe o arquivo docs/insomnia/pedidos-petfriends-insomnia para o insomnia para configurar os metodos HTTPs

# OBS: AO TESTAR REQUISIÇÕES HTTP, EDITAR O ID GERADO AO CRIAR PEDIDO NO INSOMNIA
- Ctrl + E Edit Environments → pedidoId (alterar o id no JSON com o que foi gerado ao criar pedido)

# Acesse a UI de gerenciamento em:
- UI de gerenciamento: http://localhost:15672
- usuário: guest
- senha: guest


# CRIEI UM docker-compose.yml PARA CONFIGURAR E INICIAR O RABBIT 
- Subir: docker compose up -d
- Parar: docker compose down
- Ver logs: docker compose logs -f rabbitmq

# SE TIVER APRESENTADO FALHA, FAÇA O PROCEDIMENTO ACIMA E REINICIE NOVAMENTE A APLICAÇÃO DEPOIS

# Como acessar H2: 
- abra http://localhost:8080/h2-console e use: 
- JDBC URL: jdbc:h2:mem:pedidosdb 
- user sa, 
- senha vazia.


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