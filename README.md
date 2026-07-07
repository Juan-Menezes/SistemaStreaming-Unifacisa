# Sistema de Streaming - Java

Projeto desenvolvido para a disciplina de Programação Orientada a Objetos da UNIFACISA.

O sistema simula uma plataforma de streaming capaz de gerenciar usuários, catálogo de conteúdos, playlists, reprodução de mídias e downloads, aplicando os principais conceitos de Programação Orientada a Objetos em Java.
Integrantes do grupo: Juan Menezes, Kauã Brandon, Carlos Arthur, Davi Estevão.

---

## Conceitos aplicados

- Programação Orientada a Objetos (POO)
- Encapsulamento
- Herança
- Polimorfismo
- Classes abstratas
- Enumerações (Enums)
- Tratamento de Exceções
- Organização em pacotes
- Coleções (`ArrayList`)
- Métodos estáticos
- Sobrescrita de métodos (`@Override`)

---

## Estrutura do projeto

```text
src
│
├── br.unifacisa.enums
│   ├── ClassificacaoIndicativa
│   └── TipoPlano
│
├── br.unifacisa.exceptions
│   ├── ConteudoRestritoException
│   ├── DownloadNaoPermitidoException
│   └── LimitePlaylistException
│
├── br.unifacisa.model
│   ├── Catalogo
│   ├── Conteudo (classe abstrata)
│   ├── Filme
│   ├── Musica
│   ├── Plano
│   ├── Playlist
│   └── Usuario
│
├── br.unifacisa.service
│   ├── DownloadService
│   ├── PlataformaStreamingService
│   └── ReproducaoService
│
├── Main.java
└── MainTeste.java
```

---

## Funcionalidades

### Usuários

- Cadastro de usuários
- Identificador automático
- Cálculo da idade
- Controle de contas ativas
- Associação de plano (Gratuito ou Premium)

### Catálogo

- Cadastro de filmes
- Cadastro de músicas
- Busca por título
- Listagem dos conteúdos disponíveis

### Reprodução

- Reprodução de conteúdos
- Verificação da classificação indicativa
- Controle de restrição por idade

### Playlists

- Criação de playlists
- Adição de conteúdos
- Limite de músicas para usuários gratuitos
- Listagem dos conteúdos da playlist

### Downloads

- Download permitido apenas para usuários Premium
- Tratamento de tentativas de download não autorizadas

### Royalties

Cada tipo de conteúdo calcula seus royalties utilizando polimorfismo.

- Filme
- Música

---

## Classes principais

### Conteudo

Classe abstrata responsável por representar qualquer mídia da plataforma.

Implementações:

- Filme
- Musica

Cada implementação possui sua própria lógica para cálculo de royalties.

### Usuario

Representa um usuário da plataforma.

Responsável por armazenar:

- Nome
- Idade
- Plano
- Identificador
- Quantidade de contas ativas

### Playlist

Permite organizar conteúdos em listas pessoais e realiza validações de acordo com o plano do usuário.

### Catalogo

Gerencia todos os conteúdos cadastrados na plataforma.

### PlataformaStreamingService

Classe responsável por centralizar as operações da plataforma.

---

## Exceções personalizadas

O sistema utiliza exceções específicas para representar regras de negócio.

### ConteudoRestritoException

Lançada quando um usuário tenta reproduzir um conteúdo incompatível com sua idade.

### DownloadNaoPermitidoException

Lançada quando um usuário do plano gratuito tenta realizar download.

### LimitePlaylistException

Lançada quando o limite permitido para playlists é excedido.

---

## Organização em camadas

```text
Model
│
├── Usuario
├── Conteudo
├── Filme
├── Musica
├── Playlist
├── Plano
└── Catalogo

↓

Service

├── PlataformaStreamingService
├── ReproducaoService
└── DownloadService

↓

Main
```

---

## Como executar

Compile o projeto:

```bash
javac Main.java
```

Execute:

```bash
java Main
```

Também é possível executar:

```bash
java MainTeste
```

para validar todas as funcionalidades implementadas.

---

## Tecnologias utilizadas

- Java
- IntelliJ IDEA
- Programação Orientada a Objetos

---

## Objetivo

Projeto acadêmico desenvolvido para a disciplina de Programação Orientada a Objetos da UNIFACISA, com foco na aplicação prática dos principais conceitos da orientação a objetos utilizando Java.

---

## Conceitos demonstrados

- Classes e Objetos
- Encapsulamento
- Herança
- Polimorfismo
- Abstração
- Classes Abstratas
- Sobrescrita de métodos
- Exceções personalizadas
- Enumerações (Enums)
- Organização em pacotes
- Coleções (`ArrayList`)
- Regras de negócio implementadas em serviços