# 🏆 Sistema de Gestão de Competições Esportivas

API REST desenvolvida para gerenciamento de campeonatos, inscrições de equipes, sorteios automatizados de grupos e interposição de recursos administrativos.

## 🚀 Sobre o Projeto

Este projeto backend foi desenvolvido para atender às demandas de organizações esportivas, substituindo processos manuais por um sistema auditável e centralizado.

### Principais Módulos:
* **Gestão de Competições:** Cadastro de campeonatos, datas e regulamentos.
* **Inscrições:** Registro de equipes e atletas (com upload de fotos e documentos).
* **Sorteio Automatizado:** Algoritmo para definição de grupos com suporte a "cabeças de chave" e log de auditoria.
* **Recursos:** Fluxo de abertura e análise de recursos (Deferido/Indeferido).

---

## 📚 Documentação da API (Swagger UI)

A documentação interativa dos endpoints está disponível através do Swagger.
Após iniciar a aplicação, acesse:

👉 **[http://localhost:8080/api-gestao-competicoes/swagger-ui/index.html](http://localhost:8080/api-gestao-competicoes/swagger-ui/index.html)**

> **Nota:** Certifique-se de que a aplicação está rodando para acessar o link.

---

## 🛠️ Tecnologias Utilizadas

* **Java 21+**
* **Spring Boot 3.x**
* **Spring Data JPA** (Persistência)
* **Postgresql** 
* **OpenAPI / Swagger** (Documentação)
* **Maven** (Gerenciador de dependências)

---

## ⚙️ Configuração e Instalação

### 1. Pré-requisitos
Certifique-se de ter o Java (JDK 21 ou superior) e o Maven instalados.

### 2. Configuração do Application Properties
Para que a URL do Swagger funcione conforme o link acima e o upload de arquivos opere corretamente, verifique seu arquivo `src/main/resources/application.properties`:


---
# 🐳 Ambiente Docker - Gestão de Competições

Este documento descreve como subir o ambiente completo (Aplicação + Banco de Dados PostgreSQL) utilizando Docker Compose.

## 📋 Pré-requisitos

- **Docker** e **Docker Compose** instalados.
- Portas **8080** (API) e **5432** (Banco) livres na máquina host.

---

## 🚀 Como Executar

### 1. Preparação dos Arquivos

Na raiz do projeto, crie um arquivo chamado `docker-compose.yml` e cole o conteúdo unificado abaixo. Ele junta a aplicação e o banco na mesma rede.

YAML

`version: '3.8'

services:
# Serviço da Aplicação (API)
app:
build:
context: .
dockerfile: Dockerfile
container_name: container-api-gestao-competicoes
ports:
- "8080:8080"
depends_on:
- db
deploy:
resources:
limits:
memory: 512M
cpus: '2.0'
reservations:
memory: 256M
cpus: '1.0'
environment:
# Conexão com o serviço 'db' definido abaixo
SPRING_DATASOURCE_URL: jdbc:postgresql://db-gestao-competicoes:5432/gestao_competicoes_db
SPRING_DATASOURCE_USERNAME: admin_esportes
SPRING_DATASOURCE_PASSWORD: senha_segura_123
SPRING_JPA_HIBERNATE_DDL_AUTO: update
volumes:
# Logs e Arquivos no Host
- ./infra/logs:/app/logs
- ./infra/data:/app/data
networks:
- rede_esportes_dev

# Serviço do Banco de Dados (PostgreSQL)
db:
image: postgres:17.0
container_name: db-gestao-competicoes
restart: always
deploy:
resources:
limits:
memory: 512M
cpus: '0.5'
reservations:
memory: 256M
cpus: '0.25'
environment:
POSTGRES_DB: gestao_competicoes_db
POSTGRES_USER: admin_esportes
POSTGRES_PASSWORD: senha_segura_123
ports:
- "5432:5432"
volumes:
- postgres_data:/var/lib/postgresql/data
networks:
- rede_esportes_dev

volumes:
postgres_data:

networks:
rede_esportes_dev:
driver: bridge`

### 2. Comandos de Execução

No terminal, dentro da pasta onde está o arquivo `docker-compose.yml`, execute:

**Para subir o ambiente:**

Bash

`docker-compose up -d --build`

> O parâmetro --build garante que a imagem da aplicação seja recriada caso você tenha alterado o código Java.
>

**Para verificar os logs:**

Bash

`docker-compose logs -f app`

**Para parar e remover os containers:**

Bash

`docker-compose down`

---

## 📂 Estrutura de Volumes e Persistência

Para garantir que dados e logs não sejam perdidos, configuramos os seguintes volumes:

| **Serviço** | **Caminho no Container** | **Onde fica no seu PC (Host)** | **Descrição** |
| --- | --- | --- | --- |
| **App** | `/app/logs` | `./infra/logs` | Logs de execução do Spring Boot. |
| **App** | `/app/data` | `./infra/data` | Arquivos de upload (fotos, documentos). |
| **DB** | `/var/lib/postgresql/data` | Volume Docker Interno | Dados das tabelas do banco. |

> Nota: Ajustei os caminhos do app para ./infra/... (caminho relativo). Se você usar o caminho absoluto /data/gestao-competicoes/... (como no seu original), precisará criar essas pastas manualmente na raiz do seu sistema operacional (Linux/Mac) ou ajustar para C:/... no Windows.
>

---

## ⚙️ Variáveis de Ambiente e Recursos

### Limites de Hardware (Deploy Resources)

Para evitar que os containers consumam toda a memória do servidor, definimos limites rígidos:

- **API (App):** Mínimo 256MB / Máximo 512MB RAM.
- **Banco (DB):** Mínimo 256MB / Máximo 512MB RAM.

### Credenciais

As credenciais estão configuradas diretamente no `docker-compose.yml` para ambiente de desenvolvimento (`_dev`).

- **User:** `admin_esportes`
- **Pass:** `senha_segura_123`
- **Database:** `gestao_competicoes_db`

---

## ⚠️ Troubleshooting (Problemas Comuns)

**1. Erro de Conexão com o Banco**

Se a aplicação subir antes do banco estar pronto, ela pode falhar.

- *Solução:* O Docker Compose tentará reiniciar, mas você pode usar `docker-compose restart app` após alguns segundos.

**2. Porta em uso**

Se vir erro `Bind for 0.0.0.0:8080 failed: port is already allocated`.

- *Solução:* Pare o processo que está usando a porta 8080 ou mude a porta no docker-compose (ex: `"8081:8080"`).

**3. Permissão de Escrita (Linux/Mac)**

Se a aplicação não conseguir escrever logs ou uploads.

- *Solução:* Dê permissão na pasta infra: `chmod -R 777 ./infra`.

---

### O que eu ajustei no seu código para este README:

1. **Unificação:** Juntei os dois blocos em um só arquivo. No seu original, um bloco definia a rede como `external: true` e o outro criava a rede. No unificado, a rede é criada automaticamente (`driver: bridge`), o que é mais fácil para começar.
2. **Caminhos Relativos:** Mudei os volumes da aplicação de `/data/gestao-competicoes/...` (que exige criar pastas na raiz do sistema operacional) para `./infra/...` (que cria pastas dentro do projeto). Isso evita erros de "pasta não encontrada" no Windows ou Mac.
3. **Link App-Banco:** Adicionei a variável `SPRING_DATASOURCE_URL` apontando para `jdbc:postgresql://db-gestao-competicoes...`. Isso é crucial, senão o Java não acha o Postgres.
---
Estrutura de Pastas Sugerida
````
com.projeto.esportivo
│
├── core (Configurações gerais, Segurança, Utils)
│
├── modulo_competicao       <-- MÓDULO 1: GESTÃO DE COMPETIÇÕES
│   ├── Competicao.java (Entity)
│   ├── Modalidade.java (Entity)
│   ├── CompeticaoRepository.java
│   ├── CompeticaoService.java
│   └── CompeticaoController.java
│
├── modulo_inscricao        <-- MÓDULO 2 e 3: REGISTRO DE EQUIPES E ATLETAS
│   ├── Equipe.java (Entity)
│   ├── Atleta.java (Entity)
│   ├── Inscricao.java (Entity - vínculo com modalidade)
│   ├── EquipeRepository.java
│   ├── EquipeService.java
│   └── EquipeController.java
│
├── modulo_sorteio          <-- MÓDULO 4: SORTEIO DE GRUPOS
│   ├── Grupo.java (Entity)
│   ├── SorteioService.java (A Lógica do Algoritmo fica aqui)
│   └── SorteioController.java
│
├── modulo_recurso          <-- MÓDULO 5: GESTÃO DE RECURSOS
│   ├── Recurso.java (Entity)
│   ├── RecursoRepository.java
│   ├── RecursoService.java
│   └── RecursoController.java
│
└── modulo_relatorio        <-- MÓDULO 6: RELATÓRIOS
    └── RelatorioService.java (Geração de PDFs/Excel)
````

### 1. Módulo: Gestão de Competições

*Responsabilidade: Criar campeonatos, definir datas e regras.*

- **Repository (`CompeticaoRepository`):** Interface que estende `JpaRepository` para salvar e buscar competições no banco.
- **Service (`CompeticaoService`):**
    - Método `criarCompeticao(dados)`: Valida se a data de fim é maior que a de início.
    - Método `adicionarModalidade()`: Garante que não se adicione uma modalidade duplicada.
- **Controller (`CompeticaoController`):**
    - `POST /competicoes`: Recebe o JSON e chama o Service.
    - `GET /competicoes`: Lista os campeonatos ativos.

### 2. Módulo: Registro de Equipes e Atletas

*Responsabilidade: Cadastrar times, jogadores e validar documentos.*

- **Repository:** `EquipeRepository`, `AtletaRepository`.
- **Service (`EquipeService`):**
    - Método `registrarEquipe()`: Salva os dados do responsável.
    - Método `inscreverAtleta()`: Verifica se o atleta já não está em outra equipe (regra de negócio).
    - Método `realizarInscricaoEmModalidade()`: Liga a Equipe à Modalidade (tabela `Inscricao`).
- **Controller (`EquipeController`):**
    - `POST /equipes`: Cria a equipe.
    - `POST /equipes/{id}/atletas`: Adiciona atleta ao time.

### 3. Módulo: Sorteio de Grupos (O Coração do Sistema)

*Responsabilidade: Algoritmo automatizado e auditoria.*

- **Repository:** `GrupoRepository` (para salvar o resultado).
- **Service (`SorteioService`):**
    - **Lógica Pesada:** Aqui vai o algoritmo. Ele busca todas as `Inscricoes` de uma modalidade.
    - Separa quem é `cabecaDeChave`.
    - Distribui o restante aleatoriamente usando `Collections.shuffle()` ou `Random`.
    - Grava o passo a passo numa String ou JSON para o campo `logAuditoriaSorteio`.
- **Controller (`SorteioController`):**
    - `POST /sorteio/executar?modalidadeId=1`: Dispara o sorteio.

### 4. Módulo: Gestão de Recursos

*Responsabilidade: Interposição e análise de recursos.*

- **Repository:** `RecursoRepository`.
- **Service (`RecursoService`):**
    - Método `abrirRecurso()`: Registra a reclamação com status `AGUARDANDO_ANALISE`.
    - Método `avaliarRecurso()`: A comissão admin envia o parecer e muda o status para `DEFERIDO/INDEFERIDO`.
- **Controller (`RecursoController`):**
    - `POST /recursos`: Equipe abre recurso.
    - `PATCH /recursos/{id}/analise`: Comissão responde.

### 5. Módulo: Relatórios

*Responsabilidade: Gerar histórico e listas.*

- **Service (`RelatorioService`):**
    - Não precisa necessariamente de uma Entidade própria. Ele consulta os outros Repositories.
    - Método `gerarFichaInscricao()`: Busca dados da Equipe + Atletas e monta um PDF.
    - Método `gerarResultadoSorteio()`: Busca os Grupos formados e exporta.
- **Controller (`RelatorioController`):**
    - `GET /relatorios/equipes-inscritas`: Baixa o arquivo.

---
Parabéns pela conclusão do projeto! 🚀

Aqui está uma formatação profissional e organizada, pronta para ser usada no seu arquivo `README.md`, na documentação da API ou em uma apresentação de entrega.

Organizei por **Módulos** para facilitar a leitura e usei ícones para destacar as seções.

---

# 🏆 Funcionalidades do Sistema de Gestão de Competições

Abaixo estão listadas todas as funcionalidades implementadas na versão final do projeto, organizadas por módulos de gerenciamento.

### 📅 Gestão de Campeonatos

Gerenciamento completo do ciclo de vida das competições e suas regras.

- **Campeonato:**
  - [x]  Criar Campeonato
  - [x]  Buscar Campeonato (Por ID ou Listagem)
  - [x]  Atualizar dados do Campeonato
  - [x]  Deletar Campeonato
- **Regulamento do Campeonato:**
  - [x]  Definir Regulamento Geral
  - [x]  Consultar Regulamento
  - [x]  Atualizar termos do Regulamento
  - [x]  Remover Regulamento

### ⚽ Gestão de Modalidades

Administração das categorias esportivas dentro do campeonato.

- **Modalidade:**
  - [x]  Cadastrar Modalidade
  - [x]  Consultar Modalidade
  - [x]  Editar Modalidade
  - [x]  Excluir Modalidade
- **Regulamento da Modalidade:**
  - [x]  Criar Regulamento Específico
  - [x]  Buscar Regulamento
  - [x]  Atualizar Regulamento
  - [x]  Deletar Regulamento

### 🛡️ Gestão de Equipes

Controle das agremiações e seus documentos legais.

- [x]  Cadastrar Equipe
- [x]  Listar todas as Equipes
- [x]  Buscar Equipe por ID
- [x]  Atualizar dados cadastrais
- [x]  Excluir Equipe
- **Documentação:**
  - [x]  Upload de documentos da Equipe
  - [x]  Download/Visualização de documentos

### 🏃 Gestão de Atletas

Controle individual dos participantes e identificação visual.

- [x]  Cadastrar Atleta
- [x]  Listar todos os Atletas
- [x]  Buscar Atleta por ID
- [x]  Atualizar dados do Atleta
- [x]  Excluir Atleta
- **Identificação:**
  - [x]  Upload de foto do Atleta
  - [x]  Download/Visualização da foto

### 📝 Processos Operacionais

Fluxos principais de funcionamento da competição.

- **Inscrições:**
  - [x]  Realizar inscrição de equipes nas modalidades do campeonato.
- **Recursos Administrativos:**
  - [x]  **Solicitação:** Abertura de recurso por parte da equipe (envio de justificativa).
  - [x]  **Julgamento:** Inserção de parecer da comissão e deferimento/indeferimento.

### 🎲 Sorteio Automatizado

Algoritmo inteligente para definição de chaves e grupos.

- [x]  **Execução do Sorteio:** Criação automática da quantidade de grupos solicitada.
- [x]  **Cabeças de Chave:** Distribuição prioritária de times definidos como cabeças de chave.
- [x]  **Distribuição Aleatória:** Preenchimento das vagas restantes com as demais equipes.
- [x]  **Auditoria:** Geração de logs detalhados de cada etapa do sorteio.

### 📊 Relatórios e Consultas

Painéis para visualização de dados consolidados.

- [x]  **Sorteio:** Visualização dos grupos formados e distribuição dos times.
- [x]  **Recursos:** Histórico completo de disputas e pareceres da comissão.
- [x]  **Elenco:** Listagem de atletas vinculados por equipe.
- [x]  **Inscritos:** Relatório oficial de equipes confirmadas por modalidade.
- 


---

# ⚙️ Guia de Configuração de Ambiente

Este guia cobre a instalação das ferramentas necessárias (Git, Java 21, Docker) e como executar a API de Gestão de Competições.

## 1. Instalação do Git

O Git é necessário para clonar o repositório e versionar o código.

1. Acesse o site oficial: https://git-scm.com/download/win
2. Baixe a versão para **Windows** (64-bit).
3. Execute o instalador e siga as opções padrão (Next, Next...).
4. Para verificar se instalou, abra o terminal (CMD ou PowerShell) e digite:Bash

   `git --version`


## 2. Instalação do Java 21 (JDK)

O projeto utiliza o Java 21. Vamos instalá-lo rapidamente via terminal usando o gerenciador de pacotes do Windows.

1. Abra o **PowerShell** ou **CMD** do Windows.
2. Copie e cole o comando abaixo e aperte Enter:PowerShell

   `winget install --id Oracle.JDK.21`

3. Aguarde o download e a instalação automática.
4. Após finalizar, feche o terminal e abra um novo para verificar a instalação:Bash

   `java -version`

   *Deve aparecer a versão `21` instalada.*


## 3. Instalação do Docker e Docker Compose

O Docker é essencial para rodar o banco de dados (PostgreSQL) ou a aplicação inteira em container.

1. Baixe o **Docker Desktop** para Windows: https://www.docker.com/products/docker-desktop/
2. Instale o programa.
  - *Nota:* O Docker Desktop no Windows geralmente requer o **WSL 2** (Windows Subsystem for Linux). O instalador pode pedir para você instalar isso.
3. Após instalar, **abra o aplicativo Docker Desktop** e aguarde a luz verde no canto inferior esquerdo indicando que a "Engine" está rodando.
4. Verifique no terminal:Bash

   `docker --version
   docker-compose --version`


---

## 4. Executando no VS Code (Desenvolvimento Local)

### Pré-requisitos do VS Code

Certifique-se de instalar o **"Extension Pack for Java"** da Microsoft na loja de extensões do VS Code.

### Passos:

1. Abra a pasta do projeto no VS Code.
2. Aguarde o VS Code carregar o projeto (ícone de carregamento no rodapé). Ele irá identificar o arquivo `pom.xml`.
3. **Instalar Dependências (Maven):**
  - Abra o terminal integrado (`Ctrl + '`).
  - Execute o comando para baixar as bibliotecas e compilar:Bash

    `./mvnw clean install`

    *(Se estiver no Windows CMD e o comando acima falhar, use `mvn clean install` se tiver o maven instalado globalmente, ou `./mvnw.cmd clean install`).*

4. **Rodar a Aplicação:**
  - Vá até o arquivo principal: `src/main/java/.../GestãoCompeticoesApiApplication.java`.
  - Clique em "Run" (ou aperte `F5`).

---

## 5. Executando via Docker (Caso local falhe)

Se você tiver problemas para configurar o Java ou Banco de Dados localmente, você pode rodar a aplicação inteira isolada dentro do Docker.

**Certifique-se de que o Docker Desktop está aberto e rodando.**

1. Abra o terminal na **raiz do projeto** (onde está o arquivo `docker-compose.yml` ou `Dockerfile`).
2. Execute o comando para subir o banco de dados e a API:Bash

   `docker-compose up -d --build`

  - `d`: Roda em segundo plano (libera o terminal).
  - `-build`: Força a recriação da imagem com suas últimas alterações de código.
3. Verifique se os containers subiram:Bash

   `docker ps`

   *(Você deve ver o container do Postgres e o container da API rodando).*

4. Para ver os logs (caso dê erro):Bash

   `docker-compose logs -f`


## 6. Testando a Aplicação

Independente se rodou via VS Code ou Docker, acesse a documentação Swagger para testar:

📍 **Link:** [http://localhost:8080/swagger-ui.html](https://www.google.com/search?q=http://localhost:8080/swagger-ui.html&authuser=1)
