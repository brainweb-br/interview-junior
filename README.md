# Teste Programador Backend Júnior
Teste destinado aos candidatos a vaga de Programador Backend <b>Júnior</b>. Se o seu nível é outro, por favor, dê uma olhada nos demais repositórios e escolha o que se adequa a sua skill. 
- @brainweb-interview-pleno
- @brainweb-interview-senior

## Descrição
Um cliente chamado Bruce Wayne nos contratou para fazer um sistema com o objetivo de catalogar os super-heróis existentes.
</br>
Parece uma missão difícil, mas, não se preocupe, o seu papel não será o de sair por aí procurando por heróis, vamos deixar isso para o Sr. Wayne...
</br>
Seu papel é desenvolver uma API com as operações básicas de cadastro de um herói e algum mago (coff, coff) do front-end fará as telas.
</br>

## Requisitos
Bom, aqui começa a explicação do que você terá que nos entregar. Leia com atenção.
</br>
Ah, não se preocupe com o setup do projeto, o Alfred (acho que ele é tipo um mordono do Sr. Wayne) começou o projeto para nós e inclusive o endpoint de cadastro de heróis já está quase pronto... É, Quase...
</br>
Quando tentamos rodar os testes do projeto identificamos um bug no cadastro, algum NullPointerException. O erro aconteceu no teste de integração.<p> Dito isso vamos deixar uma lista com as tarefas:

- [x] Resolver o bug no endpoint de cadastro de Heróis;
- [x] Criar endpoint de busca de heróis por ID. ***Caso não encontre o herói o sistema deve retornar um erro 404 (Not Found)***;
- [x] Criar endpoint de busca de heróis por filtro, nesse caso o filtro será apenas o nome. ***Caso não encontre nenhum herói o sistema deve retornar um sucesso 200 com o body vazio***;
- [x] Criar endpoint de atualização de heróis, todos os campos poderão ser atualizados. ***Caso não encontre o herói o sistema deve retornar um erro 404 (Not Found)***
- [x] Criar endpoint de exclusão de heróis. A exclusão será física, ok? (Física?! É, deleta o registro da base). ***Caso não encontre o herói o sistema deve retornar um erro 404 (Not Found)***.

Ah, tem algo mais! O Sr. Wayne nos pediu para criar um endpoint onde ele possa selecionar dois heróis e comparar seus atributos força, agilidade, dextreza e inteligência. Como resultado, o sistema deve retornar um objeto contendo os id's e a diferença dos atributos (positivo se maior, negativo se menor) de cada herói. Dá uma pensada em como vai ficar esse objeto e o caminho do endpoint, tudo bem?
<p>
Agora sim, terminamos! Se você nos entregar isso que pedimos garanto que o Sr. Wayne vai pirar!!!

## Considerações
Leia essas instruções para ganhar tempo no desenvolvimento, ok? ;)
</br>
#### Primeiro Passo
Como primeiro passo faça um ***fork*** desse projeto na sua conta do GitHub é assim que iremos avaliar sua prova!</br>
***Não iremos avaliar provas que não estejam nesse padrão, então MUITA ATENÇÃO nessa dica.***
#### Configurações
- OpenJDK 11 instalado;
- Maven na versão 3.6+ instalado;
- IDE pode ser o de preferência, mas gostamos bastante do IntelliJ por aqui;
- Docker e docker-compose instalados.

#### Testes
Para rodar os testes (unitários e de integração) utilize o comando a seguir:
```
mvn clean verify
```

#### Bônus
Será considerado um plus os candidatos que entregarem:
- Testes unitários e de integração das funconalidades desenvolvidas;
- Bom uso dos padrões de REST;
- Um `docker-compose.yml` funcional para execução da aplicação. (Banco de Dados + API)
