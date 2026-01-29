

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