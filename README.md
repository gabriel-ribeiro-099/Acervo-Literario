# Acervo literário

Esse programa foi desenvolvido em Java, com objetivo de implementar um Acervo Literário no qual é possível: fazer o cadastro de usuários e acessar documentos acadêmicos disponíveis na plataforma.

## Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Instalação](#instalação)
- [Como Usar](#como-usar)
- [Diagrama de Classes](#diagrama-de-classes)
- [Funcionalidades](#funcionalidades)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Desenvolvedores](#Desenvolvedores)

## Sobre o Projeto

O projeto foi desenvolvido com o intuito de disponibilizar um acervo literário para o âmbito acadêmico, que contará com documentos como TCCs, artigos científicos e livros acadêmicos.
Para isso, usuário pode se cadastrar fornecendo os atributos "Nome", "E-mail", "Login" e "Senha", com um ID gerado automaticamente para cada usuário, garantindo a singularidade no sistema. Com a crescente demanda por acesso fácil e organizado a material acadêmico, o Acervo Literário se apresenta como uma solução ideal para estudantes, pesquisadores e educadores que necessitam de documentos específicos para suas atividades acadêmicas, atendendo a um público-alvo interessado em uma coleção organizada de materiais acadêmicos.

## Link para o vídeo de apresentação

https://youtu.be/Ajp_8JBOh28?si=r_laVdEBOpvmNgDn

## Instalação

Passos para configurar o ambiente de desenvolvimento local.

1. Clone o repositório:
    ```bash
    git clone https://github.com/usuario/nome-do-projeto.git
    ```

2. Navegue até o diretório do projeto:
    ```bash
    cd nome-do-projeto
    ```

3. Compile o projeto (assumindo que você está usando Maven):
    ```bash
    mvn clean install
    ```

4. Execute o projeto:
    ```bash
    Clique em "Run"
    ```

## Como Usar

Após clonar o repositório, lembre-se de atualizar o "application.properties" com seus dados do banco, no trecho abaixo.

![image](https://github.com/user-attachments/assets/8b2688fe-cd92-475b-b5bb-7ac1c6480c57)

Acesse o seu Postman, em seguida para testar o funcionamento de cada end-point.
As possibilidades são:

- Cadastro de usuário
  
![image](https://github.com/user-attachments/assets/5e0cc216-9895-4e3e-9b02-e47b8fdd9205)

```bash
http://localhost:8080/api/auth-server/v1/users/register
````
- Login

  ![image](https://github.com/user-attachments/assets/2f60cf99-964f-41ea-b09c-1831ff064521)
```bash
http://localhost:8080/api/auth-server/v1/auth/authenticate
````

- Deletar usuário
  Utilize o token gerado no cadastro para deletar o usuário, colocando no Authorization a opção "Bearer Token".

```bash
  http://localhost:8080/api/auth-server/v1/users/delete
```

- Buscar usuário

  Utilize o GET
  ```bash
  http://localhost:8080/api/auth-server/v1/users/find
  ```
- Buscar todos usuário

  Utilize o GET
  ```bash
  http://localhost:8080/api/auth-server/v1/users/find
  ```

- Cadastrar livro

![image](https://github.com/user-attachments/assets/c4effd94-e42a-4eef-9b4f-db54dc5280df)

```bash
http://localhost:8080/api/auth-server/v1/books/register
```

- Deletar livro

```bash
http://localhost:8080/api/auth-server/v1/books/delete/112
```

- Atualizar livro

![image](https://github.com/user-attachments/assets/f6ae5219-a2bc-42e1-ac0e-e0661d57e05a)

```bash
http://localhost:8080/api/auth-server/v1/books/edit/{ISBN}
```

- Buscar livro por ISBN

```bash
http://localhost:8080/api/auth-server/v1/books/find-by-isbn/{ISBN}
```
- Buscar livro por Nome

```bash
http://localhost:8080/api/auth-server/v1/books/find-by-name/Teste edit
```

- Buscar todos os livros
  
```bash
http://localhost:8080/api/auth-server/v1/books/find-all
```
- Cadastrar artigo

![image](https://github.com/user-attachments/assets/ca8aba99-d167-46f1-a02d-d690cebfa25e)


```bash
http://localhost:8080/api/auth-server/v1/paper/register
```

- Deletar artigo

```bash
http://localhost:8080/api/auth-server/v1/paper/delete/{id}
```

- Atualizar artigo

![image](https://github.com/user-attachments/assets/28be37a7-d750-4702-8a0b-7982ceaa22b5)

```bash
http://localhost:8080/api/auth-server/v1/paper/edit/{id}
```

- Buscar artigo por ID

```bash
http://localhost:8080/api/auth-server/v1/paper/find-by-id/{id}
```
- Buscar artigo por Nome

```bash
http://localhost:8080/api/auth-server/v1/paper/find-by-name/{nome}
```

- Buscar todos os artigos
  
```bash
http://localhost:8080/api/auth-server/v1/paper/find-all
```
- Cadastrar TCC

![image](https://github.com/user-attachments/assets/9602a11f-3c24-4ad2-ab54-5f1049f4087b)


```bash
http://localhost:8080/api/auth-server/v1/finalProject/register
```

- Deletar TCC

```bash
http://localhost:8080/api/auth-server/v1/finalProject/delete/{id}
```

- Atualizar TCC

![image](https://github.com/user-attachments/assets/30dee1d3-02f3-4f21-b243-275f825afae9)

```bash
http://localhost:8080/api/auth-server/v1/finalProject/edit/{id}
```

- Buscar TCC por ID

```bash
http://localhost:8080/api/auth-server/v1/finalProject/find-by-id/{id}
```
- Buscar TCC por Nome

```bash
http://localhost:8080/api/auth-server/v1/finalProject/find-by-name/{nome}
```

- Buscar todos os TCCs
  
```bash
http://localhost:8080/api/auth-server/v1/finalProject/find-all
```
## Diagrama de Classes
![WhatsApp Image 2024-08-22 at 17 52 48](https://github.com/user-attachments/assets/d3893078-2591-4d22-9b10-c0b41409f2cf)


## Funcionalidades

- Cadastro de usuários
- Login de usuários
- Exclusão de usuários
- Busca de usuários por nome
- Atualizar usuário
  

- Cadastro de livros 
- Atualização de livros por ISBN
- Exclusão de livros por ISBN
- Busca de livros por nome e ISBN
  
  
- Cadastro de artigos científicos
- Atualização de artigos científicos por ID
- Exclusão de artigos científicos por ID
- Busca de artigos científicos por nome e ID
  

- Cadastro de TCCs
- Atualização de TCCs por ID
- Exclusão de TCCs por ID
- Busca de TCCs por nome e ID

## Tecnologias Utilizadas

- **Java**: Linguagem de programação utilizada para desenvolver o projeto.
- **Maven**: Ferramenta de automação de compilação usada para gerenciar dependências e construir o projeto.
- **Spring Boot**: Framework usado para criar a aplicação web.
- **Spring Security**: Módulo do ecossistema Spring que oferece autenticação, autorização e outras funcionalidades de segurança para aplicativos Java.
- **JavaDoc**: Responsável por criar a documentação oficial a partir dos comentários.

## Desenvolvedores

- Beatriz Santana de Moura
- Gabriel Ribeiro Barbosa da Silva
- Nicole Carvalho Nogueira
